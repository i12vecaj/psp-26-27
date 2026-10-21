import java.io.IOException;

public class Ejecutador {
    public static void main(String[] args) {
        // FR3: Crea después otro programa que ejecute el anterior - 2 puntos

        // Obtenemos la ruta de las clases compiladas actuales
        String classPath = System.getProperty("java.class.path");
        // Añadimos '-cp' seguido del classpath actual
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", classPath, "Procesos");

        // Inherit para poder escribir
        pb.inheritIO();

        // Try iniciando el programa.
        try {
            System.out.println("Iniciando el programa Procesos...");
            Process proceso = pb.start();
            int exitCode = proceso.waitFor();
            // Switch para documentar que codigo de salida uso
            switch (exitCode) {
                case 1: {
                    System.out.println("No has indicado nada");
                    break;
                }
                case 2: {
                    System.out.println("Has indicado una cadena de caracteres");
                    break;
                }
                case 3: {
                    System.out.println("Un argumento menor que 0");
                    break;
                }
                case 0: {
                    System.out.println("Cualquier otro caso");
                    break;
                }
            }
            System.out.println("Programa Main finalizado con código de salida: " + exitCode);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al ejecutar el programa: " + e.getMessage());
        }
    }
}
