import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Proceso independiente. Lee por la entrada estandar dos lineas
 * (aforo y total vendido), escribe una linea con el veredicto y termina con:
 *   0 = total igual al aforo
 *   1 = total mayor que el aforo
 *   2 = total menor que el aforo
 *   3 = datos no validos
 */
public class Auditor {

    public static void main(String[] args) {
        int codigo = auditar();
        System.exit(codigo);
    }

    private static int auditar() {
        int aforo;
        int total;
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in, StandardCharsets.UTF_8));
            String l1 = br.readLine();
            String l2 = br.readLine();
            if (l1 == null || l2 == null) {
                return invalido();
            }
            aforo = Integer.parseInt(l1.trim());
            total = Integer.parseInt(l2.trim());
        } catch (NumberFormatException | IOException e) {
            return invalido();
        }

        if (aforo <= 0 || total < 0) {
            return invalido();
        }

        if (total == aforo) {
            System.out.println("Auditoria correcta: el total coincide con el aforo");
            return 0;
        } else if (total > aforo) {
            System.out.println("Auditoria fallida: se han vendido mas entradas que el aforo");
            return 1;
        } else {
            System.out.println("Auditoria fallida: se han vendido menos entradas que el aforo");
            return 2;
        }
    }

    private static int invalido() {
        System.out.println("Auditoria fallida: datos no validos");
        return 3;
    }
}
