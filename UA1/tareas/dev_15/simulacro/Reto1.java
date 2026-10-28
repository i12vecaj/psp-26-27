import java.io.*;

public class Reto1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "-version");
        Process p = pb.start();

        // lo que el hijo escribe por la salida normal
        System.out.println("--- getInputStream ---");
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }

        // lo que el hijo escribe por la salida de errores
        System.out.println("--- getErrorStream ---");
        BufferedReader brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((linea = brError.readLine()) != null) {
            System.out.println(linea);
        }

        int codigo = p.waitFor();
        System.out.println("Codigo de salida: " + codigo);

        // ampliacion: un comando que no existe
        try {
            new ProcessBuilder("comandoquenoexiste").start();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
