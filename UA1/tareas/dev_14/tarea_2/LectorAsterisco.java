/**
 * @file      LectorAsterisco.java
 * @brief     Lee texto de la entrada estandar hasta un asterisco y despues lo muestra entero
 * @date      14/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.tarea_2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class LectorAsterisco {

    // caracter que marca el final de la lectura
    static final char FIN = '*';

    public static void main(String[] args) {
        System.out.println("Escribe el texto y termina con " + FIN + " (puede ocupar varias lineas, pulsa Intro al final):");

        StringBuilder leido = new StringBuilder();
        boolean hayFin = false;

        // FR1: leo caracter a caracter de la entrada estandar hasta llegar al asterisco
        try {
            Reader entrada = new InputStreamReader(System.in);
            int c;
            while ((c = entrada.read()) != -1) {
                if (c == FIN) {
                    hayFin = true;
                    break;
                }
                leido.append((char) c);
            }
        } catch (IOException e) {
            // control de errores: fallo al leer la entrada
            System.err.println("Error al leer la entrada estandar: " + e.getMessage());
            System.exit(1);
        }

        // FR2: con el asterisco ya recibido muestro todo lo leido
        System.out.println("Texto leido (" + leido.length() + " caracteres):");
        System.out.println(leido);

        // control de errores: la entrada se acabo y no llego el asterisco
        if (!hayFin) {
            System.err.println("Aviso: la entrada termino sin el caracter de fin " + FIN);
            System.exit(2);
        }
    }
}
