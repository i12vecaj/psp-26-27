/**
 * @file      LanzadorLector.java
 * @brief     Ejecuta LectorAsterisco como proceso hijo y muestra su salida y su codigo de salida
 * @date      14/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.tarea_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

// Uso, desde la raiz del repo y compilando fuera de el:
//   javac -d <carpeta> UA1/tareas/dev_14/tarea_2/*.java
//   java -cp <carpeta> UA1.tareas.dev_14.tarea_2.LanzadorLector
public class LanzadorLector {

    // clase que se ejecuta como proceso hijo
    static final String CLASE_HIJO = "UA1.tareas.dev_14.tarea_2.LectorAsterisco";

    public static void main(String[] args) {
        // uso el mismo java y el mismo classpath que este programa, asi el hijo encuentra su clase
        String java = Paths.get(System.getProperty("java.home"), "bin", "java").toString();
        String classpath = System.getProperty("java.class.path");

        // control de errores: sin classpath el hijo no puede encontrar LectorAsterisco
        if (classpath == null || classpath.isEmpty()) {
            System.err.println("Ejecuta este programa compilado, con java -cp <carpeta> " + LanzadorLector.class.getName());
            System.exit(1);
        }

        // FR3: preparo el proceso que ejecuta el programa anterior
        ProcessBuilder pb = new ProcessBuilder(java, "-cp", classpath, CLASE_HIJO);
        // el hijo lee del mismo teclado que este programa
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        // los errores del hijo salen junto a su salida normal
        pb.redirectErrorStream(true);

        try {
            Process hijo = pb.start();
            System.out.println("Proceso hijo lanzado con PID " + hijo.pid());

            // leo la salida del hijo mientras se ejecuta y la muestro
            try (BufferedReader salida = new BufferedReader(new InputStreamReader(hijo.getInputStream()))) {
                String linea;
                while ((linea = salida.readLine()) != null) {
                    System.out.println("[hijo] " + linea);
                }
            }

            // espero a que termine y miro como acabo
            int codigo = hijo.waitFor();
            if (codigo == 0) {
                System.out.println("El programa lector termino bien (codigo 0)");
            } else {
                System.out.println("El programa lector termino con error (codigo " + codigo + ")");
            }
            System.exit(codigo);
        } catch (IOException e) {
            // control de errores: no se pudo crear el proceso
            System.err.println("No se pudo lanzar el programa lector: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            // control de errores: se corto la espera
            System.err.println("Se interrumpio la espera del proceso hijo");
            Thread.currentThread().interrupt();
            System.exit(1);
        }
    }
}
