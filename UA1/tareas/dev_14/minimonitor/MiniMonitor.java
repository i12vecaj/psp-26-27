/**
 * @file      MiniMonitor.java
 * @brief     Ejecuta un comando del sistema y muestra un informe de lo que ha pasado
 * @date      14/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.minimonitor;

import java.io.*;

public class MiniMonitor {
    static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] comando = WIN ? new String[]{"cmd", "/c", "dir"}
                               : new String[]{"ls", "-l"};

        // 1. Preparo el comando con ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder(comando);
        // los errores del comando salen por la misma salida para verlos tambien
        pb.redirectErrorStream(true);

        // 2. Lanzo el proceso
        Process proceso = pb.start();

        // 3. Muestro el PID del proceso hijo
        System.out.println("PID del proceso hijo: " + proceso.pid());

        // 4. Leo la salida linea a linea y numero cada linea
        int lineas = 0;
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                lineas++;
                System.out.println(lineas + ": " + linea);
            }
        }

        // 5. Espero a que termine
        int codigo = proceso.waitFor();

        // 6. Informe final con las lineas leidas y el codigo de salida
        System.out.println();
        System.out.println("Informe final");
        System.out.println("Lineas leidas: " + lineas);
        if (codigo == 0) {
            System.out.println("Codigo de salida: " + codigo + " (correcto)");
        } else {
            System.out.println("Codigo de salida: " + codigo + " (ha fallado)");
        }
    }
}
