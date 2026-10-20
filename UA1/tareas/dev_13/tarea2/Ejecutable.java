import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Ejecutable {

    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("java", "LectorTexto");

        try {
            Process proceso = pb.start();

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(proceso.getOutputStream()))) {
                String mensaje = "Hola, este es un texto de prueba que termina con asterisco* este texto ya no se leera";
                writer.write(mensaje);
                writer.flush();
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                System.out.println("Salida");
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            int exitCode = proceso.waitFor();
            if (exitCode != 0) {
                System.err.println("Error: " + exitCode);
            }

        } catch (IOException e) {
            System.err.println("Error. " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
            Thread.currentThread().interrupt(); 
        }
    }
}
