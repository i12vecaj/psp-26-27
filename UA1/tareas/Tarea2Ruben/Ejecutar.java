
import java.io.IOException;

public class Ejecutar {

    public static void main(String[] args) {

        // FR3: Preparar la ejecución de Lector
        ProcessBuilder pb = new ProcessBuilder(
                "java", "-cp",
                System.getProperty("java.class.path"),
                "Leer"
        );

        // Compartir la entrada y salida con la consola
        pb.inheritIO();

        try {
            // Iniciar el proceso hijo
            Process p = pb.start();

            // Mostrar su PID
            System.out.println("PID del proceso hijo: " + p.pid());

            // Esperar a que termine
            int codigoSalida = p.waitFor();

            if (codigoSalida == 0) {
                System.out.println("Proceso finalizado correctamente");
            } else {
                System.err.println("El proceso ha fallado. Codigo: " + codigoSalida);
            }

        } catch (IOException e) {
            System.err.println("Error al iniciar el proceso: " + e.getMessage());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El proceso ha sido interrumpido");
        }
    }
}
