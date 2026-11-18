import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class reto2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Process hijo = new ProcessBuilder(
                "java", "-cp", System.getProperty("java.class.path"), "Eco"
        ).start();

        try (PrintWriter escritor = new PrintWriter(hijo.getOutputStream(), true)) {
            escritor.println("hola");
            escritor.println("mundo");
            escritor.println("fin");
        }

        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(hijo.getInputStream()))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }
        }

        hijo.waitFor();
    }
}