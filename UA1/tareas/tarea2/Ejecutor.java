import java.io.*;

public class Ejecutor  {

    public static void main(String[] args) {

        try {
            // Ruta del ejecutable de Java
            String java = System.getProperty("java.home")
                    + File.separator + "bin"
                    + File.separator + "java";

            // Ruta donde se encuentran las clases compiladas
            String classpath = System.getProperty("java.class.path");

            // Ejecutamos la clase correcta
            ProcessBuilder pb = new ProcessBuilder(
                    java,
                    "-cp",
                    classpath,
                    "LectorHastaAsterisco"
            );

            // Compartimos la entrada y la salida de la consola
            pb.inheritIO();

            // Iniciamos el proceso
            Process proceso = pb.start();

            // Esperamos a que termine
            int resultado = proceso.waitFor();

            if (resultado == 0) {
                System.out.println("El proceso terminó correctamente.");
            } else {
                System.err.println(
                        "El proceso terminó con código: " + resultado
                );
            }

        } catch (IOException e) {
            System.err.println(
                    "Error al ejecutar el programa: " + e.getMessage()
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("La ejecución fue interrumpida.");
        }
    }
}