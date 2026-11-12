import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
 
public class Auditor {
 
    private static final int OK = 0;
    private static final int MAYOR = 1;
    private static final int MENOR = 2;
    private static final int INVALIDO = 3;
 
    public static void main(String[] args) {
        int codigo = auditar();
        System.exit(codigo);
    }
 
    private static int auditar() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(System.in, StandardCharsets.UTF_8));
            String l1 = in.readLine();
            String l2 = in.readLine();
            if (l1 == null || l2 == null) {
                return invalido();
            }
            int aforo = Integer.parseInt(l1.trim());
            int total = Integer.parseInt(l2.trim());
            if (aforo < 0 || total < 0) {
                return invalido();
            }
            if (total == aforo) {
                System.out.println("Auditor: el total coincide con el aforo");
                return OK;
            } else if (total > aforo) {
                System.out.println("Auditor: el total es mayor que el aforo");
                return MAYOR;
            } else {
                System.out.println("Auditor: el total es menor que el aforo");
                return MENOR;
            }
        } catch (NumberFormatException | IOException e) {
            return invalido();
        }
    }
 
    private static int invalido() {
        System.out.println("Auditor: datos no validos");
        return INVALIDO;
    }
}
 