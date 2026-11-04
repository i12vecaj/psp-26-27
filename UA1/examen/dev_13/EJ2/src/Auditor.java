import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Auditor {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String lineAforo = reader.readLine();
            String lineTotal = reader.readLine();

            if (lineAforo == null || lineTotal == null) {
                System.out.println("Veredicto: Error - Datos de entrada incompletos.");
                System.exit(3);
            }

            int aforo = 0;
            int totalVendido = 0;
            try {
                aforo = Integer.parseInt(lineAforo.trim());
                totalVendido = Integer.parseInt(lineTotal.trim());
            } catch (NumberFormatException e) {
                System.out.println("Veredicto: Error - Los datos no son números válidos.");
                System.exit(3);
            }

            if (aforo < 0 || totalVendido < 0) {
                System.out.println("Veredicto: Error - Los valores no pueden ser negativos.");
                System.exit(3);
            }

            if (totalVendido == aforo) {
                System.out.println("Veredicto: El total vendido coincide exactamente con el aforo.");
                System.exit(0);
            } else if (totalVendido > aforo) {
                System.out.println("Veredicto: El total vendido supera el aforo permitido.");
                System.exit(1);
            } else {
                System.out.println("Veredicto: El total vendido es inferior al aforo disponible.");
                System.exit(2);
            }

        } catch (Exception e) {
            System.out.println("Veredicto: Error inesperado en el proceso auditor.");
            System.exit(3);
        }
    }
}