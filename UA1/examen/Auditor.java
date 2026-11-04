import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Proceso independiente: lee por entrada estandar el aforo y el total vendido
 * (una linea cada uno), escribe un veredicto y termina con un codigo de salida.
 * 0 = coincide, 1 = total mayor, 2 = total menor, 3 = datos no validos.
 */
public class Auditor {

    private static final int COINCIDE = 0;
    private static final int MAYOR = 1;
    private static final int MENOR = 2;
    private static final int NO_VALIDO = 3;

    public static void main(String[] args) {
        System.exit(auditar());
    }

    private static int auditar() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String lineaAforo = in.readLine();
            String lineaTotal = in.readLine();
            if (lineaAforo == null || lineaTotal == null) {
                return invalido();
            }
            int aforo = Integer.parseInt(lineaAforo.trim());
            int total = Integer.parseInt(lineaTotal.trim());
            if (aforo < 0 || total < 0) {
                return invalido();
            }

            if (total == aforo) {
                System.out.println("Auditor: el total vendido coincide con el aforo");
                return COINCIDE;
            } else if (total > aforo) {
                System.out.println("Auditor: se han vendido mas entradas que el aforo");
                return MAYOR;
            } else {
                System.out.println("Auditor: se han vendido menos entradas que el aforo");
                return MENOR;
            }
        } catch (NumberFormatException | IOException e) {
            return invalido();
        }
    }

    private static int invalido() {
        System.out.println("Auditor: datos no validos");
        return NO_VALIDO;
    }
}
