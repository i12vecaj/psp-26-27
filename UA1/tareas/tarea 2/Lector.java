import java.io.*;

public class Lanzador {
    public static void main(String[] args) {
        try {
            // Preparamos el comando: "java -cp <classpath actual> Lector"
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp", System.getProperty("java.class.path"), "Lector");
            pb.redirectErrorStream(true);   // los errores salen junto a la salida normal

            // FR3: lanzamos el programa anterior
            Process proceso = pb.start();

            // Le enviamos texto al hijo (termina en '*')
            BufferedWriter escritor = new BufferedWriter(
                    new OutputStreamWriter(proceso.getOutputStream()));
            escritor.write("Hola, esto es una prueba de procesos*");
            escritor.flush();
            escritor.close();   // cerramos para que el hijo sepa que no hay más datos

            // Leemos lo que muestra el hijo
            BufferedReader lector = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }
            lector.close();

            // Esperamos a que termine y comprobamos el resultado
            int codigo = proceso.waitFor();
            if (codigo == 0) {
                System.out.println("El proceso terminó correctamente.");
            } else {
                System.out.println("El proceso falló. Código de salida: " + codigo);
            }

        } catch (IOException e) {
            System.err.println("Error al lanzar o comunicar con el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
        }
    }
}