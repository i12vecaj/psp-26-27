/**
 * @file      Reto3.java
 * @brief     Lanza hijos Espera de 2 s uno tras otro y a la vez y mide lo que tarda cada forma
 * @date      29/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.simulacro.laboratorio;

import java.io.IOException;

public class Reto3 {

    static Process lanzar(int segundos) throws IOException {
        return new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), Espera.class.getName(), String.valueOf(segundos))
                .start();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // uno tras otro: el segundo no empieza hasta que acaba el primero
        long inicio = System.currentTimeMillis();
        lanzar(2).waitFor();
        lanzar(2).waitFor();
        System.out.println("Secuencial: " + (System.currentTimeMillis() - inicio) + " ms");

        // a la vez: los dos start antes del primer waitFor
        inicio = System.currentTimeMillis();
        Process a = lanzar(2);
        Process b = lanzar(2);
        a.waitFor();
        b.waitFor();
        System.out.println("Paralelo con 2 hijos: " + (System.currentTimeMillis() - inicio) + " ms");

        // ampliacion: tres hijos a la vez
        inicio = System.currentTimeMillis();
        Process[] hijos = {lanzar(2), lanzar(2), lanzar(2)};
        for (Process h : hijos) {
            h.waitFor();
        }
        System.out.println("Paralelo con 3 hijos: " + (System.currentTimeMillis() - inicio) + " ms");
    }
}
