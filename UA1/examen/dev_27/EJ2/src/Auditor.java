import java.util.Scanner;

public class Auditor {

    public static void main(String[] args) {
        int aforo;
        int total;

        try (Scanner sc = new Scanner(System.in)) {
            if (!sc.hasNextLine()) {
                System.out.println("Datos no validos");
                System.exit(3);
                return;
            }
            String lineaAforo = sc.nextLine();

            if (!sc.hasNextLine()) {
                System.out.println("Datos no validos");
                System.exit(3);
                return;
            }
            String lineaTotal = sc.nextLine();

            aforo = Integer.parseInt(lineaAforo.trim());
            total = Integer.parseInt(lineaTotal.trim());

            if (aforo < 0 || total < 0) {
                System.out.println("Datos no validos");
                System.exit(3);
                return;
            }
        } catch (Exception e) {
            System.out.println("Datos no validos");
            System.exit(3);
            return;
        }

        if (total == aforo) {
            System.out.println("Aforo completo");
            System.exit(0);
        } else if (total > aforo) {
            System.out.println("Se ha vendido mas del aforo");
            System.exit(1);
        } else {
            System.out.println("Quedan entradas sin vender");
            System.exit(2);
        }
    }
}
