
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Programa único con dos modos de funcionamiento:
 *
 *  - Modo HIJO (argumento "--hijo"): lee de la entrada estándar hasta
 *    el carácter '*' y muestra por pantalla todo lo leído (FR1 y FR2).
 *  - Modo PADRE (sin argumentos): se lanza a sí mismo como proceso
 *    hijo con ProcessBuilder y espera a que termine (FR3).
 *
 * Uso:  java Procesos
 */
public class Procesos {

    /** Carácter que indica el final de la lectura. */
    private static final char TERMINADOR = '*';

    /** Argumento que activa el modo hijo. */
    private static final String ARG_HIJO = "--hijo";

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals(ARG_HIJO)) {
            modoHijo();
        } else {
            modoPadre();
        }
    }

    /**
     * FR1 y FR2: lee caracteres hasta '*' y muestra lo leído.
     * Códigos de salida: 0 = correcto, 1 = error de lectura,
     * 2 = la entrada terminó sin recibir '*'.
     */
    private static void modoHijo() {
        StringBuilder leido = new StringBuilder();
        boolean terminadorRecibido = false;

        System.out.println("Escribe texto y termina con '" + TERMINADOR + "':");

        // try-with-resources: el lector se cierra solo al terminar
        try (Reader entrada = new InputStreamReader(System.in)) {
            int c; // int para poder representar -1 (fin de datos)

            // FR1: leer carácter a carácter hasta '*' o fin de entrada
            while ((c = entrada.read()) != -1) {
                if (c == TERMINADOR) {
                    terminadorRecibido = true;
                    break;
                }
                leido.append((char) c);
            }
        } catch (IOException e) {
            System.err.println("Error al leer la entrada estándar: " + e.getMessage());
            System.exit(1);
        }

        // Control de error: se cerró la entrada sin recibir '*'
        if (!terminadorRecibido) {
            System.err.println("Aviso: la entrada terminó sin recibir '" + TERMINADOR + "'.");
            System.exit(2);
        }

        // FR2: mostrar toda la información leída
        System.out.println("--- Información leída ---");
        System.out.println(leido);
    }

    /**
     * FR3: lanza este mismo programa en modo hijo y espera su resultado.
     */
    private static void modoPadre() {
        try {
            // Ruta del ejecutable java que está usando este programa
            String java = System.getProperty("java.home") + "/bin/java";
            // Classpath actual, para que el hijo encuentre la clase
            String classpath = System.getProperty("java.class.path");

            ProcessBuilder pb = new ProcessBuilder(
                    java, "-cp", classpath, "Procesos", ARG_HIJO);

            // El hijo comparte teclado y pantalla con el padre
            pb.inheritIO();

            Process proceso = pb.start();
            System.out.println("[padre] Hijo lanzado. PID: " + proceso.pid());

            int codigo = proceso.waitFor();

            if (codigo == 0) {
                System.out.println("[padre] El hijo terminó correctamente (código 0).");
            } else {
                System.err.println("[padre] El hijo terminó con error (código " + codigo + ").");
            }

        } catch (IOException e) {
            System.err.println("[padre] No se pudo lanzar el proceso hijo: " + e.getMessage());
        } catch (InterruptedException e) {
            // Se restaura la marca de interrupción del hilo
            Thread.currentThread().interrupt();
            System.err.println("[padre] La espera del hijo fue interrumpida.");
        }
    }
}


