import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RetoUno {
    static void main() throws IOException, InterruptedException {
        Process p = new ProcessBuilder("java", "-version").start();
        BufferedReader lectorError = new BufferedReader(
                new InputStreamReader(p.getErrorStream())
        );

        String linea;
        while ((linea = lectorError.readLine()) != null) {
            System.out.println(linea);
            int exitCode = p.waitFor();

            System.out.println("Código de salida: " + exitCode);
        }
    }
}


