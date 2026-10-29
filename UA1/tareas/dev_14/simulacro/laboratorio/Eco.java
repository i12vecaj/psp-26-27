/**
 * @file      Eco.java
 * @brief     Lee lineas de su entrada hasta recibir fin y las devuelve en mayusculas
 * @date      29/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.simulacro.laboratorio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Eco {

    public static void main(String[] args) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String linea;
        // lee hasta fin o hasta que el padre cierre el flujo
        while ((linea = entrada.readLine()) != null && !linea.equals("fin")) {
            System.out.println(linea.toUpperCase());
        }
    }
}
