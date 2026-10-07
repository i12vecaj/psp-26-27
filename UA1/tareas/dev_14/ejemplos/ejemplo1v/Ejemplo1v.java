/**
 * @file      Ejemplo1v.java
 * @brief     Variación del Ejemplo1: lanza el Explorador y muestra el PID del proceso padre y del hijo
 * @date      07/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.ejemplos.ejemplo1v;

import java.io.IOException;

// Basado en: Ejemplo1 del repositorio de la asignatura (UA1/ejemplos/java)
public class Ejemplo1v {

    public static void main(String[] args) {
        // el proceso padre es este programa
        long pidPadre = ProcessHandle.current().pid();
        System.out.println("PID del proceso padre (este programa): " + pidPadre);

        ProcessBuilder pb = new ProcessBuilder("EXPLORER.exe");

        try {
            // start() crea el proceso hijo
            Process hijo = pb.start();
            System.out.println("PID del proceso hijo (Explorador):   " + hijo.pid());
        } catch (IOException e) {
            System.err.println("No se ha podido lanzar el proceso hijo: " + e.getMessage());
            System.exit(1);
        }
    }
}
