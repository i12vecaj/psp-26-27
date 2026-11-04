import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Proceso independiente de auditoria contable y validacion de aforo.
 * Se comunica exclusivamente mediante los flujos estandar (IPC con entrada en System.in y salida en System.out).
 * 
 * Contrato de codigos de salida (Exit codes):
 *  0: Coincide (el total vendido coincide con el aforo asignado).
 *  1: Mayor (sobreventa: las ventas superan el aforo).
 *  2: Menor (ventas incompletas: las ventas son inferiores al aforo).
 *  3: Datos no validos (formato no numerico, lineas incompletas o valores negativos).
 */
public class Auditor {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String lineaAforo = reader.readLine();
            String lineaTotal = reader.readLine();

            if (lineaAforo == null || lineaTotal == null) {
                System.out.println("Error de auditoria: Datos incompletos en la entrada estandar.");
                System.exit(3);
                return;
            }

            int aforo;
            int totalVendido;

            try {
                aforo = Integer.parseInt(lineaAforo.trim());
                totalVendido = Integer.parseInt(lineaTotal.trim());
            } catch (NumberFormatException nfe) {
                System.out.println("Error de auditoria: Formato numerico no valido en los datos.");
                System.exit(3);
                return;
            }

            if (aforo < 0 || totalVendido < 0) {
                System.out.println("Error de auditoria: Los valores de aforo y venta no pueden ser negativos.");
                System.exit(3);
                return;
            }

            if (totalVendido == aforo) {
                System.out.println("Auditoria correcta: el total coincide con el aforo.");
                System.exit(0);
            } else if (totalVendido > aforo) {
                System.out.println("Auditoria erronea: sobreventa detectada (" + totalVendido + " > " + aforo + ").");
                System.exit(1);
            } else {
                System.out.println("Auditoria desfavorable: ventas incompletas (" + totalVendido + " < " + aforo + ").");
                System.exit(2);
            }

        } catch (Exception e) {
            System.out.println("Error critico en el proceso Auditor: " + e.getMessage());
            System.exit(3);
        }
    }
}

