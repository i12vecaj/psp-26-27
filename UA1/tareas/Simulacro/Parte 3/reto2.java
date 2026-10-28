import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Reto2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        String cp = System.getProperty("java.class.path");
        Process process = new ProcessBuilder("java", "-cp", cp, "Eco").start();

        // Escritor con autoflush activado
        PrintWriter writer = new PrintWriter(process.getOutputStream(), true);

        writer.println("hola");
        writer.println("mundo");
        writer.println("fin");
        writer.close();

        Scanner reader = new Scanner(process.getInputStream());
        while (reader.hasNextLine()) {
            System.out.println("Respuesta: " + reader.nextLine());
        }

        if (!process.waitFor(3, TimeUnit.SECONDS)) {
            System.out.println("Tiempo agotado, destruyendo...");
            process.destroy();
        }
    }
}
