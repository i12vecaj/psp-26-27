import java.io.IOException;

public class Tarea2Lanzadora {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Tarea2");
            pb.inheritIO(); // el hijo comparte teclado y consola con este programa

            Process proceso = pb.start();
            int codigo = proceso.waitFor();
            System.out.println("El programa Tarea2 terminó con código: " + codigo);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al ejecutar Tarea2: " + e.getMessage());
        }
    }
}