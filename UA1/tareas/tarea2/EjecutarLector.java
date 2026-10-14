import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * Programa padre (FR3): lanza el programa LectorAsterisco como proceso hijo.
 *
 * Pide texto por teclado, se lo envía al hijo por su entrada estándar
 * (hasta que se escribe un '*') y muestra lo que el hijo responde,
 * junto con su código de salida.
 *
 * Uso: java EjecutarLector
 */
public class EjecutarLector {

    public static void main(String[] args) {
        // Ruta al ejecutable 'java' de la misma JDK/JRE que está ejecutando este programa
        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        // Mismo classpath que el padre, así el hijo encuentra LectorAsterisco.class
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(java, "-cp", classpath, "LectorAsterisco");
        pb.redirectErrorStream(true); // los errores del hijo salen junto a su salida normal

        Process hijo = null;
        try {
            hijo = pb.start();
            System.out.println("Proceso hijo lanzado (PID " + hijo.pid() + ").");
            System.out.println("Escribe texto; termina con un asterisco (*):");

            // Enviar al hijo lo que el usuario escribe por teclado
            BufferedWriter haciaHijo = new BufferedWriter(
                    new OutputStreamWriter(hijo.getOutputStream()));
            Scanner teclado = new Scanner(System.in);
            boolean fin = false;
            while (!fin && teclado.hasNextLine()) {
                String linea = teclado.nextLine();
                haciaHijo.write(linea);
                haciaHijo.newLine();
                haciaHijo.flush();
                fin = linea.contains("*");
            }
            haciaHijo.close(); // cierra la entrada del hijo (señala EOF)

            // Mostrar la salida del hijo
            try (BufferedReader delHijo = new BufferedReader(
                    new InputStreamReader(hijo.getInputStream()))) {
                String linea;
                while ((linea = delHijo.readLine()) != null) {
                    System.out.println("[hijo] " + linea);
                }
            }

            int codigo = hijo.waitFor();
            System.out.println("El hijo terminó con código " + codigo
                    + (codigo == 0 ? " (correcto)" : " (error)"));

        } catch (IOException e) {
            // No se pudo lanzar el hijo o falló la comunicación con él
            System.err.println("Error de E/S con el proceso hijo: " + e.getMessage());
        } catch (InterruptedException e) {
            // Nos interrumpieron mientras esperábamos al hijo
            System.err.println("Espera interrumpida: " + e.getMessage());
            if (hijo != null) hijo.destroy();
            Thread.currentThread().interrupt();
        }
    }
}