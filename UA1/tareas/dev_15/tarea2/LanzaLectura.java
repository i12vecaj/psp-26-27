/*
 * Tarea 2 - Programacion de procesos en Java (I)
 * Andres Martinez Cano (dev_15)
 *
 * FR3: programa que ejecuta LecturaAsterisco como un proceso hijo con
 * ProcessBuilder. Con inheritIO() el hijo usa la misma consola que este
 * programa, asi que lo que se escribe le llega al hijo y lo que el hijo
 * muestra sale por pantalla. Al terminar se mira el codigo de salida.
 *
 * Compilar y ejecutar desde la carpeta tarea2:
 *   javac LecturaAsterisco.java LanzaLectura.java
 *   java LanzaLectura
 */
import java.io.*;

public class LanzaLectura {

    public static void main(String[] args) {
        // control de errores: si no esta compilado el otro programa no se puede lanzar
        File clase = new File("LecturaAsterisco.class");
        if (!clase.exists()) {
            System.out.println("Error: no se encuentra LecturaAsterisco.class");
            System.out.println("Compila primero con: javac LecturaAsterisco.java");
            System.exit(1);
        }

        ProcessBuilder pb = new ProcessBuilder("java", "LecturaAsterisco");
        // el hijo usa la entrada, la salida y la salida de error de este programa
        pb.inheritIO();

        try {
            Process p = pb.start();
            System.out.println("Proceso hijo lanzado, PID: " + p.pid());

            // esperamos a que el hijo termine y cogemos su codigo de salida
            int codigo = p.waitFor();
            System.out.println("El proceso hijo ha terminado con codigo " + codigo);

            if (codigo == 0) {
                System.out.println("Correcto, se ha leido el texto hasta el *");
            } else if (codigo == 2) {
                System.out.println("Fallo: se acabo la entrada sin escribir el *");
            } else if (codigo == 3) {
                System.out.println("Fallo: el hijo no ha podido leer la entrada");
            } else {
                System.out.println("Fallo: codigo de salida desconocido");
            }
        } catch (IOException e) {
            // control de errores: no se ha podido arrancar el proceso (por ejemplo, no esta java)
            System.out.println("Error al lanzar el proceso: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            // control de errores: nos han interrumpido mientras esperabamos al hijo
            System.out.println("Se ha interrumpido la espera del proceso hijo");
            System.exit(1);
        }
    }
}
