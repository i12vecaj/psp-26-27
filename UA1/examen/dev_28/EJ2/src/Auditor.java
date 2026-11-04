import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Proceso independiente. Lee por la entrada estándar el aforo y el total
 * vendido (una línea cada uno), escribe un veredicto y termina con:
 * 0 = coincide, 1 = total mayor, 2 = total menor, 3 = datos no válidos.
 */
public class Auditor {

    public static void main(String[] args) {
        int aforo;
        int total;
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in))) {
            aforo = leerNumero(entrada.readLine());
            total = leerNumero(entrada.readLine());
        } catch (IOException e) {
            aforo = -1;
            total = -1;
        }

        if (aforo < 0 || total < 0) {
            System.out.println("Auditor: datos no validos");
            System.exit(3);
        }

        if (total == aforo) {
            System.out.println("Auditor: correcto, se han vendido " + total + " de " + aforo);
            System.exit(0);
        } else if (total > aforo) {
            System.out.println("Auditor: error, se han vendido " + total
                    + " y el aforo es " + aforo + " (exceso)");
            System.exit(1);
        } else {
            System.out.println("Auditor: error, se han vendido " + total
                    + " y el aforo es " + aforo + " (faltan entradas)");
            System.exit(2);
        }
    }

    /**
     * Convierte una línea en un número entero no negativo.
     * Devuelve -1 si la línea no existe (null), está vacía, no es un número
     * o es negativa.
     */
    private static int leerNumero(String linea) {
        if (linea == null) {
            return -1;
        }
        try {
            int numero = Integer.parseInt(linea.trim());
            return numero >= 0 ? numero : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
