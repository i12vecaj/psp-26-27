/*
 * Auditor - PSP UA1, ejercicio 2
 * Juan Raigón Mínguez
 *
 * Proceso hijo. Lee "aforo total" por la entrada estandar y
 * termina con un codigo segun cuadre o no la venta.
 */

import java.util.Scanner;

public class Auditor {

    // codigos de salida
    static final int CORRECTO = 0;
    static final int SOBREVENTA = 1;
    static final int FALTAN = 2;
    static final int DATOS_MAL = 3;

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        // primer dato: el aforo
        if (!entrada.hasNextInt()) {
            terminar(DATOS_MAL, "ERROR - no ha llegado el aforo o no es un numero");
        }
        int aforo = entrada.nextInt();

        // segundo dato: lo vendido
        if (!entrada.hasNextInt()) {
            terminar(DATOS_MAL, "ERROR - no ha llegado el total vendido o no es un numero");
        }
        int vendidas = entrada.nextInt();

        // aforo cero o negativo, o ventas negativas, no tienen sentido
        if (aforo <= 0 || vendidas < 0) {
            terminar(DATOS_MAL, "ERROR - valores fuera de rango (aforo " + aforo + ", vendidas " + vendidas + ")");
        }

        // veredicto
        if (vendidas > aforo) {
            terminar(SOBREVENTA, "SOBREVENTA - " + vendidas + " vendidas para " + aforo + " plazas, " + (vendidas - aforo) + " de mas");
        }
        if (vendidas < aforo) {
            terminar(FALTAN, "INCOMPLETO - quedan " + (aforo - vendidas) + " de " + aforo + " entradas sin vender");
        }
        terminar(CORRECTO, "OK - " + vendidas + " entradas vendidas, aforo de " + aforo + " completo");
    }

    // los avisos de error salen por System.err y el resto por System.out
    static void terminar(int codigo, String mensaje) {
        if (codigo == CORRECTO) {
            System.out.println(mensaje);
        } else {
            System.err.println(mensaje);
        }
        System.exit(codigo);
    }
}
