import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Auditor {

    public static void main(String[] args) {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        int aforo;
        int total;

        try {
            String lineaAforo = entrada.readLine();
            String lineaTotal = entrada.readLine();
            if (lineaAforo == null || lineaTotal == null) {
                datosNoValidos();
                return;
            }
            aforo = Integer.parseInt(lineaAforo.trim());
            total = Integer.parseInt(lineaTotal.trim());
        } catch (NumberFormatException | IOException e) {
            datosNoValidos();
            return;
        }

        if (aforo <= 0 || total < 0) {
            datosNoValidos();
            return;
        }

        if (total == aforo) {
            System.out.println("Auditoria correcta: el total coincide con el aforo");
            System.exit(0);
        } else if (total > aforo) {
            System.out.println("Error: se han vendido mas entradas que el aforo");
            System.exit(1);
        } else {
            System.out.println("Aviso: se han vendido menos entradas que el aforo");
            System.exit(2);
        }
    }

    private static void datosNoValidos() {
        System.out.println("Datos no validos");
        System.exit(3);
    }
}
