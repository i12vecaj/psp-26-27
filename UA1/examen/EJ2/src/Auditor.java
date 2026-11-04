import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Auditor {

    public static void main(String[] args) throws IOException {
        int codigo = 3;
        String veredicto = "Datos no validos.";

        try (BufferedReader entrada = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String lineaAforo = entrada.readLine();
            String lineaTotal = entrada.readLine();

            if (lineaAforo != null && lineaTotal != null
                    && entrada.readLine() == null) {
                int aforo = Integer.parseInt(lineaAforo.trim());
                int total = Integer.parseInt(lineaTotal.trim());

                if (aforo > 0 && total >= 0) {
                    if (total == aforo) {
                        codigo = 0;
                        veredicto = "Auditoria correcta: el total coincide con el aforo.";
                    } else if (total > aforo) {
                        codigo = 1;
                        veredicto = "Auditoria: se han vendido mas entradas que el aforo.";
                    } else {
                        codigo = 2;
                        veredicto = "Auditoria: se han vendido menos entradas que el aforo.";
                    }
                }
            }
        } catch (NumberFormatException e) {
            codigo = 3;
            veredicto = "Datos no validos.";
        }

        System.out.println(veredicto);
        System.exit(codigo);
    }
}
