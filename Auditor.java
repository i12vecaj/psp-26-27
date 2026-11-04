import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Proceso independiente. Lee por entrada estandar dos lineas (aforo y total
 * vendido), escribe una linea con el veredicto y termina con un codigo:
 * 0 = coincide, 1 = total mayor que el aforo, 2 = total menor, 3 = datos no validos.
 */
public class Auditor {

    private static final int OK = 0;
    private static final int MAYOR = 1;
    private static final int MENOR = 2;
    private static final int INVALIDO = 3;

    public static void main(String[] args) {
        System.exit(auditar());
    }

    private static int auditar() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
            String lineaAforo = entrada.readLine();
            String lineaTotal = entrada.readLine();

            if (lineaAforo == null || lineaTotal == null) {
                System.out.println("Auditor: datos no validos (faltan lineas)");
                return INVALIDO;
            }

            int aforo = Integer.parseInt(lineaAforo.trim());
            int total = Integer.parseInt(lineaTotal.trim());

            if (aforo <= 0 || total < 0) {
                System.out.println("Auditor: datos no validos (valores fuera de rango)");
                return INVALIDO;
            }

            if (total == aforo) {
                System.out.println("Auditor: correcto, el total " + total + " coincide con el aforo " + aforo);
                return OK;
            } else if (total > aforo) {
                System.out.println("Auditor: error, el total " + total + " es mayor que el aforo " + aforo);
                return MAYOR;
            } else {
                System.out.println("Auditor: error, el total " + total + " es menor que el aforo " + aforo);
                return MENOR;
            }
        } catch (NumberFormatException e) {
            System.out.println("Auditor: datos no validos (no son numeros enteros)");
            return INVALIDO;
        } catch (IOException e) {
            System.out.println("Auditor: datos no validos (error de lectura)");
            return INVALIDO;
        }
    }
}