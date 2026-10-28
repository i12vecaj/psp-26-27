import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Reto1 {
    public static void main(String[] args) {
        try {
            Process p = new ProcessBuilder("java", "-version").start();

            BufferedReader error = new BufferedReader(
                new InputStreamReader(p.getErrorStream()));

            BufferedReader salida = new BufferedReader(
                new InputStreamReader(p.getInputStream()));

            String linea;

            while ((linea = error.readLine()) != null) {
                System.out.println(linea);
            }

            while ((linea = salida.readLine()) != null) {
                System.out.println(linea);
            }

            int codigo = p.waitFor();
            System.out.println("Código de salida: " + codigo);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}