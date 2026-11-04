import java.util.Scanner;

public class Auditor {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int codigo;
        String mensaje;

        try {

            int aforo = Integer.parseInt(sc.nextLine().trim());
            int vendido = Integer.parseInt(sc.nextLine().trim());

            if (aforo < 0 || vendido < 0) {
                mensaje = "Datos no validos";
                codigo = 3;
            } else if (vendido == aforo) {
                mensaje = "Auditoria correcta: total coincide con aforo";
                codigo = 0;
            } else if (vendido > aforo) {
                mensaje = "Error: total vendido mayor que aforo";
                codigo = 1;
            } else {
                mensaje = "Error: total vendido menor que aforo";
                codigo = 2;
            }

        } catch (Exception e) {
            mensaje = "Datos no validos";
            codigo = 3;
        }

        System.out.println(mensaje);
        System.exit(codigo);
    }
}