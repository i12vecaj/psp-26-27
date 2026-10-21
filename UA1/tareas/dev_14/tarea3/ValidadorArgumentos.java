/**
 * @file      ValidadorArgumentos.java
 * @brief     Mira el argumento recibido en main() y devuelve un codigo con System.exit() segun lo que sea
 * @date      14/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.tarea3;

import java.math.BigInteger;

// Codigos de salida:
//   1 si no hay argumentos
//   2 si el argumento es una cadena (no es un numero entero)
//   3 si el argumento es un numero entero menor que 0
//   0 en cualquier otro caso
public class ValidadorArgumentos {

    static final int SIN_ARGUMENTOS = 1;
    static final int ES_CADENA = 2;
    static final int ENTERO_NEGATIVO = 3;
    static final int CORRECTO = 0;

    public static void main(String[] args) {
        // FR1: los argumentos llegan por main()
        // FR2: menos de un argumento devuelve 1
        if (args.length < 1) {
            System.exit(SIN_ARGUMENTOS);
        }

        // uso BigInteger para que un entero muy largo no se tome por cadena
        BigInteger numero;
        try {
            numero = new BigInteger(args[0].trim());
        } catch (NumberFormatException e) {
            // FR2: no es un numero entero, es una cadena
            System.exit(ES_CADENA);
            return;
        }

        // FR2: entero menor que 0 devuelve 3, cualquier otro caso devuelve 0
        if (numero.signum() < 0) {
            System.exit(ENTERO_NEGATIVO);
        }
        System.exit(CORRECTO);
    }
}
