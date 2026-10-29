/**
 * @file      Hijo.java
 * @brief     Escribe los numeros de 1 a n con una espera de 200 ms y termina con un codigo segun el argumento
 * @date      29/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.simulacro;

public class Hijo {

    // codigos de salida que entiende el padre
    static final int OK = 0;
    static final int SIN_ARGUMENTO = 1;
    static final int NO_ES_NUMERO = 2;
    static final int NEGATIVO = 3;

    public static void main(String[] args) {
        // FR1: sin argumento
        if (args.length == 0) {
            System.exit(SIN_ARGUMENTO);
        }

        // FR1: el argumento tiene que ser un numero
        int n;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.exit(NO_ES_NUMERO);
            return;
        }

        // FR1: y no puede ser negativo
        if (n < 0) {
            System.exit(NEGATIVO);
        }

        // FR1: los numeros de 1 a n, uno por linea y con 200 ms entre cada uno
        for (int i = 1; i <= n; i++) {
            System.out.println(i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.exit(OK);
    }
}
