import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * Programa padre (FR3): lanza el programa LectorAsterisco como proceso hijo.
 *
 * Pide texto por teclado, se lo envía al hijo por su entrada estándar
 * (hasta que se escribe un '*') y muestra lo que el hijo responde,
 * junto con su código de salida.
 *
 * Uso: java EjecutarLector
 */
public class EjecutarLector {

    public static void main(String[] args) {
        // Ruta al ejecutable 'java' de la misma JDK/JRE que está ejecutando este programa
        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        // Mismo classpath que el padre, así el hijo encuentra LectorAsterisco.class
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(java, "-cp", classpath, "LectorAsterisco");
       import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Programa hijo: lee caracteres de la entrada estándar hasta recibir
 * el carácter de terminación '*' y después muestra todo lo leído.
 *
 * Códigos de salida:
 *   0 -> correcto (se recibió el asterisco)
 *   1 -> error de E/S al leer la entrada
 *   2 -> la entrada se cerró sin recibir el asterisco
 *
 * Uso: java LectorAsterisco
 */
public class LectorAsterisco {

    /** Carácter que indica el fin de la lectura. */
    private static final char TERMINADOR = '*';

    public static void main(String[] args) {
        StringBuilder leido = new StringBuilder();
        boolean terminadorRecibido = false;

        // FR1: lectura carácter a carácter desde la entrada estándar
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in))) {
            int c;
            while ((c = entrada.read()) != -1) {   // -1 = fin de la entrada (EOF)
                if (c == TERMINADOR) {
                    terminadorRecibido = true;
                    break;                          // el '*' no se guarda
                }
                leido.append((char) c);
            }
        } catch (IOException e) {
            // Control de errores: fallo al leer
            System.err.println("Error de lectura: " + e.getMessage());
            System.exit(1);
        }

        // Control de errores: la entrada terminó sin '*'
        if (!terminadorRecibido) {
            System.err.println("Aviso: la entrada terminó sin recibir el carácter '" + TERMINADOR + "'.");
            System.exit(2);
        }

        // FR2: mostrar toda la información leída
        System.out.println("=== Información leída ===");
        System.out.println(leido);
        System.out.println("=== Fin (" + leido.length() + " caracteres) ===");
    }
}