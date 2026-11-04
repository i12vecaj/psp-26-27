
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Auditor {

    public static void main(String[] args) {

        try {

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            // Recibir el aforo.
            String lineaAforo = entrada.readLine();

            // Recibir el total vendido.
            String lineaTotal = entrada.readLine();

            // Comprobar que se han recibido los dos datos.
            if (lineaAforo == null || lineaTotal == null) {
                System.out.println("Datos no validos.");
                System.exit(3);
            }

            int aforo = Integer.parseInt(lineaAforo);
            int totalVendido = Integer.parseInt(lineaTotal);

            // Validar los datos.
            if (aforo <= 0 || totalVendido < 0) {

                System.out.println("Datos no validos.");
                System.exit(3);

            } else if (totalVendido == aforo) {

                System.out.println(
                        "Auditor: El total vendido coincide con el aforo."
                );
                System.exit(0);

            } else if (totalVendido > aforo) {

                System.out.println(
                        "Auditor: Se ha superado el aforo."
                );
                System.exit(1);

            } else {

                System.out.println(
                        "Auditor: No se ha completado el aforo."
                );
                System.exit(2);
            }

        } catch (Exception e) {

            System.out.println("Datos no validos.");
            System.exit(3);
        }
    }
}
