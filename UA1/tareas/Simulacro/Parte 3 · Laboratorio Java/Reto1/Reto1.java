import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reto1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Process proceso = new ProcessBuilder("java", "-version").start();

        System.out.println("--- getInputStream() ---");
        BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        String linea;
        while ((linea = lector.readLine()) != null) {
            System.out.println(linea);
        }

        System.out.println("--- getErrorStream() ---");
        BufferedReader lectorError = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
        while ((linea = lectorError.readLine()) != null) {
            System.out.println(linea);
        }

        int resultado = proceso.waitFor();
        System.out.println("Resultado: " + resultado);

        // Ampliacion: comando que no existe
        try {
            Process otro = new ProcessBuilder("comando_que_no_existe").start();
            System.out.println("start() ha funcionado");
            otro.waitFor();
            System.out.println("waitFor() ha funcionado");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

