import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Reto1 {
    public static void main(String[] args) throws IOException {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-version");
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            StringBuilder cadena = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    cadena.append(linea).append(System.lineSeparator());
                    System.out.println(linea);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}