import java.io.IOException;

public class Tarea2 {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "Lector");
            pb.inheritIO();
            Process proceso = pb.start();
            proceso.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
class Lector {
    public static void main(String[] args) {
        StringBuilder texto = new StringBuilder();
        System.out.println("Introduce texto (* para terminar):");
        try {
            int c;
            while ((c = System.in.read()) != -1) {
                if ((char) c == '*') break;
                texto.append((char) c);
            }
            System.out.println("Texto leído:");
            System.out.println(texto.toString());
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}