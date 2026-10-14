
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Programa que ejecuta LectorTexto como un proceso externo.
 *
 * @author Alumno
 * @version 1.0
 */
public class Ejecutor {

    /**
     * Método principal que inicia el proceso hijo.
     *
     * @param args argumentos recibidos desde la línea de comandos
     */
    public static void main(String[] args) {

        // Obtener la ruta del ejecutable Java y del directorio actual.
        String java = System.getProperty("java.home")
                + System.getProperty("file.separator")
                + "bin"
                + System.getProperty("file.separator")
                + "java";

        String directorio = System.getProperty("user.dir");

        // Construir el comando para ejecutar LectorTexto.
        ProcessBuilder pb = new ProcessBuilder(
                java,
                "-cp",
                directorio,
                "LectorTexto"
        );

        // Unificar la salida de error con la salida estándar.
        pb.redirectErrorStream(true);

        try {
            // Iniciar el proceso hijo.
            Process proceso = pb.start();

            // Leer la salida del proceso hijo.
            try (BufferedReader salida = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()))) {

                String linea;

                while ((linea = salida.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            // Esperar a que termine el proceso y consultar su resultado.
            int resultado = proceso.waitFor();

            if (resultado == 0) {
                System.out.println(
                        "El proceso ha finalizado correctamente."
                );
            } else {
                System.err.println(
                        "El proceso ha terminado con el código: " + resultado
                );
            }

        } catch (IOException e) {
            // Controlar errores al iniciar o leer el proceso.
            System.err.println(
                    "Error al ejecutar el programa: " + e.getMessage()
            );

        } catch (InterruptedException e) {
            // Restaurar el estado de interrupción del hilo.
            Thread.currentThread().interrupt();

            System.err.println(
                    "La espera del proceso ha sido interrumpida."
            );
        }
    }
}
