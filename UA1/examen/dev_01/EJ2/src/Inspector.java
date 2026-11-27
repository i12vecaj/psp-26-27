
import java.util.Scanner;
//sistem exit recoge termina el proceso y recoge SistemaAlmacen con el waitfor
public class Inspector {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Recibimos los datos del almacén
            int esperado = Integer.parseInt(sc.nextLine());
            int preparado = Integer.parseInt(sc.nextLine());

            // Comprobamos que los datos sean válidos
            if (esperado < 0 || preparado < 0) {
                System.out.println("Datos no validos");
                System.exit(3);
            }

            // Comparamos los pedidos esperados y preparados
            if (preparado == esperado) {
                System.out.println("Total correcto");
                System.exit(0);

            } else if (preparado > esperado) {
                System.out.println("Se han preparado mas pedidos");
                System.exit(1);

            } else {
                System.out.println("Se han preparado menos pedidos");
                System.exit(2);
            }

        } catch (Exception e) {
            System.out.println("Datos no validos");
            System.exit(3);
        }
    }
}
