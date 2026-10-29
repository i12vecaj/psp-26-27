/**
 * @file      Reto2.java
 * @brief     Envia hola, mundo y fin al programa Eco y muestra lo que responde
 * @date      29/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.simulacro.laboratorio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Reto2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), Eco.class.getName());
        Process hijo = pb.start();

        // lo que escribe el padre es la entrada del hijo
        PrintWriter alHijo = new PrintWriter(hijo.getOutputStream(), true);
        alHijo.println("hola");
        alHijo.println("mundo");
        alHijo.println("fin");
        alHijo.close();

        // lo que imprime el hijo lo lee el padre
        BufferedReader delHijo = new BufferedReader(new InputStreamReader(hijo.getInputStream()));
        String linea;
        while ((linea = delHijo.readLine()) != null) {
            System.out.println("El hijo dice: " + linea);
        }

        // ampliacion: se espera 3 segundos como mucho
        if (hijo.waitFor(3, TimeUnit.SECONDS)) {
            System.out.println("Eco ha terminado con codigo " + hijo.exitValue());
        } else {
            System.out.println("Eco no termina, lo corto");
            hijo.destroy();
        }
    }
}
