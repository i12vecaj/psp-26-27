import java.util.Scanner;

public class Auditor {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            if (!scanner.hasNextInt()) {
                System.out.println("Auditor: Datos no validos recibidos.");
                System.exit(3);
            }
            int aforo = scanner.nextInt();

            if (!scanner.hasNextInt()) {
                System.out.println("Auditor: Datos no validos recibidos.");
                System.exit(3);
            }
            int totalVendido = scanner.nextInt();

            if (totalVendido == aforo) {
                System.out.println("Auditor: El total coincide con el aforo.");
                System.exit(0);
            } else if (totalVendido < aforo) {
                System.out.println("Auditor: El total no coincide con el aforo (quedan entradas por vender).");
                System.exit(1);
            } else {
                System.out.println("Auditor: El total no coincide con el aforo (sobreventa de entradas).");
                System.exit(2);
            }
        } catch (Exception e) {
            System.out.println("Auditor: Error durante la ejecucion de la auditoria.");
            System.exit(3);
        }
    }
}

