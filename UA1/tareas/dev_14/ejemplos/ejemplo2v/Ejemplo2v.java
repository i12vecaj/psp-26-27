/**
 * @file      Ejemplo2v.java
 * @brief     Variación del Ejemplo2: lanza CMD /C DIR, lee su salida por líneas y las muestra numeradas
 * @date      07/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.ejemplos.ejemplo2v;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

// Basado en: Ejemplo2 del repositorio de la asignatura (UA1/ejemplos/java)
public class Ejemplo2v {

    public static void main(String[] args) {
        Process p;
        try {
            p = new ProcessBuilder("CMD", "/C", "DIR").start();
        } catch (IOException e) {
            System.err.println("No se ha podido lanzar CMD: " + e.getMessage());
            System.exit(1);
            return;
        }

        // CMD escribe con la codificación de la consola de Windows (cp850), así salen bien las tildes
        Charset consola = Charset.forName("IBM850");

        // en vez de leer carácter a carácter como el Ejemplo2, leo línea a línea y las numero
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(p.getInputStream(), consola))) {
            String linea;
            int numero = 1;
            while ((linea = lector.readLine()) != null) {
                System.out.printf("%3d: %s%n", numero, linea);
                numero++;
            }
        } catch (IOException e) {
            System.err.println("Error al leer la salida de CMD: " + e.getMessage());
        }

        // espero a que termine el hijo: 0 es que fue bien, otro valor es que hubo un error
        try {
            int valorSalida = p.waitFor();
            System.out.println("Valor de salida: " + valorSalida);
        } catch (InterruptedException e) {
            System.err.println("Se ha interrumpido la espera del proceso hijo");
            Thread.currentThread().interrupt();
        }
    }
}
