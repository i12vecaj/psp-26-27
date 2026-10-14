import java.io.IOException;

public class Ejecutador {
    public static void main(String[] args) {
        // FR3: Crea después otro programa que ejecute el anterior - 2 puntos

        // Obtenemos la ruta de las clases compiladas actuales
        String classPath = System.getProperty("java.class.path");
        // Añadimos '-cp' seguido del classpath actual
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", classPath, "Main");

        // Inherit para poder escribir
        pb.inheritIO();

        // Try iniciando el programa.
        try {
            System.out.println("Iniciando el programa Main...");
            Process proceso = pb.start();
            int exitCode = proceso.waitFor();
            System.out.println("Programa Main finalizado con código de salida: " + exitCode);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al ejecutar el programa: " + e.getMessage());
        }
    }
}