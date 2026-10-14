import java.io.IOException;

/**
 * Programa que ejecuta LeerCadena.java
 * como un proceso independiente.
 */
public class EjecutarPrograma {

    public static void main(String[] args) {

        try {
            // Obtenemos la ruta del ejecutable de Java
            String java = System.getProperty("java.home")
                    + System.getProperty("file.separator")
                    + "bin"
                    + System.getProperty("file.separator")
                    + "java";

            // Creamos el proceso que ejecutará LeerCadena
            ProcessBuilder pb = new ProcessBuilder(
                    java,
                    "-cp",
                    System.getProperty("java.class.path"),
                    "LeerCadena"
            );

            // Mostramos la salida y los errores en la consola actual
            pb.inheritIO();

            // Iniciamos el proceso
            Process proceso = pb.start();

            // Esperamos a que termine
            int resultado = proceso.waitFor();

            // Comprobamos si ha finalizado correctamente
            if (resultado == 0) {
                System.out.println("El proceso ha terminado correctamente.");
            } else {
                System.err.println("El proceso terminó con errores.");
            }

        } catch (IOException e) {
            // Error al iniciar el proceso
            System.err.println("No se pudo ejecutar el programa: "
                    + e.getMessage());

        } catch (InterruptedException e) {
            // Error si se interrumpe la espera
            Thread.currentThread().interrupt();
            System.err.println("Se interrumpió la ejecución del proceso.");
        }
    }
}