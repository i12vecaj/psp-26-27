import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Proceso independiente que comprueba si el total vendido coincide con el aforo.
 * Entrada estándar: dos líneas (aforo y total vendido).
 * Códigos de salida:
 *   0 = el total coincide con el aforo
 *   1 = el total es mayor que el aforo
 *   2 = el total es menor que el aforo
 *   3 = los datos no son válidos
 */
public class Auditor {

    private static final int COINCIDE = 0;
    private static final int MAYOR = 1;
    private static final int MENOR = 2;
    private static final int DATOS_NO_VALIDOS = 3;

    public static void main(String[] args) {
        int aforo;
        int total;

        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
            String lineaAforo = entrada.readLine();
            String lineaTotal = entrada.readLine();
            if (lineaAforo == null || lineaTotal == null) {
                datosNoValidos("faltan datos de entrada");
                return;
            }
            aforo = Integer.parseInt(lineaAforo.trim());
            total = Integer.parseInt(lineaTotal.trim());
        } catch (NumberFormatException e) {
            datosNoValidos("los datos no son numeros enteros");
            return;
        } catch (IOException e) {
            datosNoValidos("error al leer la entrada");
            return;
        }

        if (aforo < 0 || total < 0) {
            datosNoValidos("los valores no pueden ser negativos");
            return;
        }

        if (total == aforo) {
            System.out.println("Auditor: correcto, el total (" + total + ") coincide con el aforo (" + aforo + ")");
            System.exit(COINCIDE);
        } else if (total > aforo) {
            System.out.println("Auditor: error, el total (" + total + ") es mayor que el aforo (" + aforo + ")");
            System.exit(MAYOR);
        } else {
            System.out.println("Auditor: aviso, el total (" + total + ") es menor que el aforo (" + aforo + ")");
            System.exit(MENOR);
        }
    }

    private static void datosNoValidos(String motivo) {
        System.out.println("Auditor: datos no validos (" + motivo + ")");
        System.exit(DATOS_NO_VALIDOS);
    }
}
