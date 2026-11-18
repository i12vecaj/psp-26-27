import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Eco {
    public static void main(String[] args) throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        String linea;

        while ((linea = lector.readLine()) != null && !linea.equals("fin")) {
            System.out.println(linea.toUpperCase());
        }
    }
}