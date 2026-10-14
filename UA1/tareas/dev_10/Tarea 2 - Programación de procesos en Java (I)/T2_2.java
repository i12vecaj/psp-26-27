import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Ejecuta el programa LectorAsterisco como proceso hijo.
 * Le envía un texto terminado en '*' por su entrada estándar y
 * muestra lo que el hijo imprime, junto con su código de salida.
 */
public class Lanzador {

    public static void main(String[] args) {
        // Texto de prueba: lo que va después del '*' el hijo ya no lo lee
        String texto = args.length > 0 ? args[0] : "Hola desde el proceso padre*";

        try {
            // Preparar el comando: "java LectorAsterisco"
            // (requiere que LectorAsterisco.class esté en el directorio actual)
            ProcessBuilder pb = new ProcessBuilder("java", "LectorAsterisco");

            // Redirige el flujo de errores del hijo al de salida,
            // así se leen ambos en un único bucle y no hay bloqueos
            pb.redirectErrorStream(true);

            Process proceso = pb.start();
            System.out.println("Proceso hijo lanzado. PID: " + proceso.pid());

            // Enviar el texto al hijo por su entrada estándar
            try (OutputStream salidaHaciaHijo = proceso.getOutputStream()) {
                salidaHaciaHijo.write(texto.getBytes(StandardCharsets.UTF_8));
                salidaHaciaHijo.flush();
            } // al cerrarse, el hijo recibe fin de datos

            // Leer y mostrar lo que imprime el hijo
            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    System.out.println("[hijo] " + linea);
                }
            }

            // Esperar a que termine y comprobar el resultado
            int codigo = proceso.waitFor();
            if (codigo == 0) {
                System.out.println("El proceso hijo terminó correctamente (código 0).");
            } else {
                System.err.println("El proceso hijo terminó con error (código " + codigo + ").");
            }

        } catch (IOException e) {
            // No se pudo lanzar el proceso o falló la comunicación
            System.err.println("Error de E/S al ejecutar el proceso hijo: " + e.getMessage());
        } catch (InterruptedException e) {
            // Se interrumpió la espera: se restaura la marca de interrupción
            Thread.currentThread().interrupt();
            System.err.println("La espera del proceso hijo fue interrumpida.");
        }
    }
}