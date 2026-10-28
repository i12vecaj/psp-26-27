import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Reto1 {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("java", "-version");
        Process p = pb.start();

        BufferedReader out = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        String linea;
        while ((linea = out.readLine()) != null) System.out.println("OUT: " + linea);
        while ((linea = err.readLine()) != null) System.out.println("ERR: " + linea);

        int codigo = p.waitFor();
        System.out.println("Código de salida: " + codigo);
    }
}