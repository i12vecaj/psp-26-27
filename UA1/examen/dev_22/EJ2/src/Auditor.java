import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Auditor {

    public static void main(String[] args) {

        // Leemos los datos que envía el proceso padre
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        try {
            String aforoTexto = br.readLine();
            String totalTexto = br.readLine();

            int aforo;
            int total;

            // Comprobamos que los datos sean números válidos
            try {
                if (aforoTexto == null || totalTexto == null) {
                    throw new NumberFormatException();
                }

                aforo = Integer.parseInt(aforoTexto.trim());
                total = Integer.parseInt(totalTexto.trim());

                if (aforo < 0 || total < 0) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException e) {
                System.out.println("Datos no validos");
                System.exit(3);
                return;
            }

            // Comprobamos si el total coincide con el aforo
            if (total == aforo) {
                System.out.println("Auditoria correcta: el total vendido coincide con el aforo.");
                System.exit(0);

            } else if (total > aforo) {
                System.out.println("Auditoria incorrecta: el total vendido supera el aforo.");
                System.exit(1);

            } else {
                System.out.println("Auditoria incorrecta: el total vendido es menor que el aforo.");
                System.exit(2);
            }

        } catch (IOException e) {
            System.out.println("Datos no validos");
            System.exit(3);
        }
    }
}