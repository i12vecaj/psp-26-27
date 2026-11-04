import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Auditor {

    private static final int COINCIDE = 0;
    private static final int SOBRAN = 1;
    private static final int FALTAN = 2;
    private static final int INVALIDO = 3;

    public static void main(String[] args) {
        System.exit(auditar());
    }

    private static int auditar() {
        int aforo;
        int total;
        try (BufferedReader entrada = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String lineaAforo = entrada.readLine();
            String lineaTotal = entrada.readLine();
            if (lineaAforo == null || lineaTotal == null) {
                return invalido();
            }
            aforo = Integer.parseInt(lineaAforo.trim());
            total = Integer.parseInt(lineaTotal.trim());
        } catch (IOException | NumberFormatException e) {
            return invalido();
        }

        if (aforo <= 0 || total < 0) {
            return invalido();
        }
        if (total == aforo) {
            System.out.println("Correcto: el total coincide con el aforo");
            return COINCIDE;
        }
        if (total > aforo) {
            System.out.println("Error: se han vendido mas entradas que el aforo");
            return SOBRAN;
        }
        System.out.println("Error: se han vendido menos entradas que el aforo");
        return FALTAN;
    }

    private static int invalido() {
        System.out.println("Datos no validos");
        return INVALIDO;
    }
}
