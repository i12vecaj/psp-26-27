import java.io.*;
import java.util.Scanner;

public class Reto1 {
    public static void main(String[] args) {
        try {
            Process process = new ProcessBuilder("java", "-version").start();

            // Leemos el ErrorStream porque java -version imprime ahí
            Scanner s = new Scanner(process.getErrorStream());
            while (s.hasNextLine()) {
                System.out.println(s.nextLine());
            }

            int exitCode = process.waitFor();
            System.out.println("Código de salida: " + exitCode);

            // Ampliación: Comando inexistente
            System.out.println("\n--- Ampliación ---");
            new ProcessBuilder("comando_que_no_existe").start();

        } catch (IOException | InterruptedException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }
    }
}
