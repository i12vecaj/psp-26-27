import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Auditor {
    private static final int CODIGO_CORRECTO = 0;
    private static final int CODIGO_SUPERIOR = 1;
    private static final int CODIGO_INFERIOR = 2;
    private static final int CODIGO_NO_VALIDO = 3;

    public static void main(String[] args) {
        int codigo;
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(System.in))) {
            String lineaAforo = lector.readLine();
            String lineaTotal = lector.readLine();
            if (lineaAforo == null || lineaTotal == null) {
                codigo = CODIGO_NO_VALIDO;
            } else {
                int aforo = Integer.parseInt(lineaAforo.trim());
                int total = Integer.parseInt(lineaTotal.trim());
                if (aforo < 0 || total < 0) {
                    codigo = CODIGO_NO_VALIDO;
                } else if (total == aforo) {
                    codigo = CODIGO_CORRECTO;
                } else if (total > aforo) {
                    codigo = CODIGO_SUPERIOR;
                } else {
                    codigo = CODIGO_INFERIOR;
                }
            }
        } catch (NumberFormatException | IOException e) {
            codigo = CODIGO_NO_VALIDO;
        }
        System.out.println("Resultado de la auditoria: codigo " + codigo);
        System.exit(codigo);
    }
}
