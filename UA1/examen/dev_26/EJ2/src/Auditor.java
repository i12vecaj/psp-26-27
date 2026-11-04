import java.io.BufferedReader;
import java.io.InputStreamReader;


//corregido algunos errores con la ia, ayudando a poner el relleno de catch y demás
public class Auditor {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String lineaAforo = reader.readLine();
            String lineaTotal = reader.readLine();

            if (lineaAforo == null || lineaTotal == null) {
                System.out.println("Error: Faltan datos.");
                System.exit(3);
            }

            int aforo = Integer.parseInt(lineaAforo.trim());
            int totalVendido = Integer.parseInt(lineaTotal.trim());

            if (aforo < 0 || totalVendido < 0) {
                System.out.println("Error: Datos no válidos.");
                System.exit(3);
            }

            if (totalVendido == aforo) {
                System.out.println("El total coincide exactamente con el aforo de " + aforo + " entradas.");
                System.exit(0);
            } else if (totalVendido > aforo) {
                System.out.println("El total vendido (" + totalVendido + ") es mayor que el aforo (" + aforo + ").");
                System.exit(1);
            } else {
                System.out.println("El total vendido (" + totalVendido + ") es menor que el aforo (" + aforo + ").");
                System.exit(2);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Formato de número inválido.");
            System.exit(3);
        } catch (Exception e) {
            System.out.println("Error en el auditor: " + e.getMessage());
            System.exit(3);
        }
    }
}