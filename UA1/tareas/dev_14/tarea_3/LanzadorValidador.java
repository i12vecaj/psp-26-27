/**
 * @file      LanzadorValidador.java
 * @brief     Ejecuta ValidadorArgumentos como proceso hijo y explica lo que pasa segun el codigo que devuelve
 * @date      14/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.tarea_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Uso, desde la raiz del repo y compilando fuera de el:
//   javac -d <carpeta> UA1/tareas/dev_14/tarea_3/*.java
//   java -cp <carpeta> UA1.tareas.dev_14.tarea_3.LanzadorValidador 25
// Sin argumentos prueba todos los casos de la tarea uno detras de otro.
public class LanzadorValidador {

    // clase que se ejecuta como proceso hijo
    static final String CLASE_HIJO = "UA1.tareas.dev_14.tarea_3.ValidadorArgumentos";

    public static void main(String[] args) {
        // uso el mismo java y el mismo classpath que este programa, asi el hijo encuentra su clase
        String java = Paths.get(System.getProperty("java.home"), "bin", "java").toString();
        String classpath = System.getProperty("java.class.path");

        // control de errores: sin classpath el hijo no puede encontrar ValidadorArgumentos
        if (classpath == null || classpath.isEmpty()) {
            System.err.println("Ejecuta este programa compilado, con java -cp <carpeta> " + LanzadorValidador.class.getName());
            System.exit(1);
        }

        if (args.length > 0) {
            // con argumentos se los paso tal cual al hijo
            ejecutar(java, classpath, args);
        } else {
            // sin argumentos pruebo un caso de cada codigo
            String[][] casos = { {}, {"hola"}, {"-7"}, {"25"}, {"0"}, {"3.5"} };
            for (String[] caso : casos) {
                ejecutar(java, classpath, caso);
                System.out.println();
            }
        }
    }

    // FR3: ejecuta el programa anterior con los argumentos dados y explica el resultado
    static void ejecutar(String java, String classpath, String[] argumentos) {
        List<String> comando = new ArrayList<>(Arrays.asList(java, "-cp", classpath, CLASE_HIJO));
        comando.addAll(Arrays.asList(argumentos));
        System.out.println("Argumentos enviados: " + (argumentos.length == 0 ? "(ninguno)" : String.join(" ", argumentos)));

        ProcessBuilder pb = new ProcessBuilder(comando);
        // los errores del hijo salen junto a su salida normal
        pb.redirectErrorStream(true);

        try {
            Process hijo = pb.start();

            // leo lo que escriba el hijo para que no se quede bloqueado y por si avisa de algun error
            try (BufferedReader salida = new BufferedReader(new InputStreamReader(hijo.getInputStream()))) {
                String linea;
                while ((linea = salida.readLine()) != null) {
                    System.out.println("[hijo] " + linea);
                }
            }

            int codigo = hijo.waitFor();
            System.out.println("Valor devuelto " + codigo + ", " + explicar(codigo));
        } catch (IOException e) {
            // control de errores: no se pudo crear el proceso
            System.err.println("No se pudo lanzar el programa: " + e.getMessage());
        } catch (InterruptedException e) {
            // control de errores: se corto la espera
            System.err.println("Se interrumpio la espera del proceso hijo");
            Thread.currentThread().interrupt();
        }
    }

    // traduce el codigo de salida del hijo a un mensaje
    static String explicar(int codigo) {
        switch (codigo) {
            case 0: return "el argumento es un numero entero mayor o igual que 0";
            case 1: return "no se paso ningun argumento";
            case 2: return "el argumento es una cadena, no un numero entero";
            case 3: return "el argumento es un numero entero menor que 0";
            default: return "codigo inesperado, el programa hijo fallo";
        }
    }
}
