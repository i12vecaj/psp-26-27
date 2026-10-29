/**
 * @file      Reto1.java
 * @brief     Lanza java -version, muestra lo que imprime y su codigo de salida
 * @date      29/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.simulacro.laboratorio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Reto1 {

    // muestra todas las lineas de un flujo con una etiqueta delante
    static void mostrar(String etiqueta, InputStream flujo) throws IOException {
        BufferedReader lector = new BufferedReader(new InputStreamReader(flujo));
        String linea;
        while ((linea = lector.readLine()) != null) {
            System.out.println(etiqueta + linea);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Process p = new ProcessBuilder("java", "-version").start();
        // java -version escribe en la salida de error, no en la normal
        mostrar("[salida] ", p.getInputStream());
        mostrar("[error]  ", p.getErrorStream());
        System.out.println("Codigo de salida: " + p.waitFor());

        // ampliacion: un comando que no existe
        try {
            new ProcessBuilder("comandoquenoexiste").start();
        } catch (IOException e) {
            System.out.println("No se pudo lanzar: " + e.getMessage());
        }
    }
}
