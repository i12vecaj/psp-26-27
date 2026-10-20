import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class LectorTexto {

    public static void main(String[] args) {
        StringBuilder textoAcumulado = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int caracterLeido;

            while ((caracterLeido = reader.read()) != -1) {
                char c = (char) caracterLeido;
                if (c == '*') {
                    break;
                }
                textoAcumulado.append(c);
            }

            System.out.println("Leido");
            System.out.println(textoAcumulado.toString());

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}