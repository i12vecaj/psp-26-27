import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Programa principal: cinco taquillas venden a la vez y, al terminar,
 * se lanza el proceso Auditor para comprobar el total vendido.
 */
public class SistemaVentas {

    private static final int AFORO = 200;
    private static final int[] CUPOS = {20, 30, 40, 50, 60};

    public static void main(String[] args) {
        // 1. Crear las taquillas (cada una con sus propios datos) y sus hilos.
        Taquilla[] taquillas = new Taquilla[CUPOS.length];
        Thread[] hilos = new Thread[CUPOS.length];
        for (int i = 0; i < CUPOS.length; i++) {
            taquillas[i] = new Taquilla(i + 1, CUPOS[i]);
            hilos[i] = new Thread(taquillas[i]);
        }

        // 2. Arrancar todas y después esperar a todas (bucles separados).
        long inicio = System.nanoTime();
        for (Thread hilo : hilos) {
            hilo.start();
        }
        try {
            for (Thread hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrumpido mientras se esperaba a las taquillas");
            System.exit(1);
        }
        long fin = System.nanoTime();

        // 3. Leer los resultados: todas las taquillas ya han terminado.
        int total = 0;
        for (Taquilla taquilla : taquillas) {
            System.out.println("Taquilla " + taquilla.getNumero() + ": "
                    + taquilla.getVendidas() + " entradas");
            total += taquilla.getVendidas();
        }
        System.out.println("Total vendido: " + total + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + (fin - inicio) / 1_000_000 + " ms");

        // 4. Lanzar el Auditor como proceso independiente.
        int codigo = ejecutarAuditor(AFORO, total);
        System.out.println("Codigo del auditor: " + codigo);
    }

    private static int ejecutarAuditor(int aforo, int total) {
        String java = System.getProperty("java.home") + File.separator + "bin"
                + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        ProcessBuilder pb = new ProcessBuilder(java, "-cp", classpath, "Auditor");
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process proceso = null;
        try {
            proceso = pb.start();

            // Enviar los dos datos por la entrada estándar y cerrarla.
            // BufferedWriter lanza IOException si falla la escritura
            // (PrintWriter la ocultaría).
            try (BufferedWriter entrada = new BufferedWriter(
                    new OutputStreamWriter(proceso.getOutputStream()))) {
                entrada.write(aforo + System.lineSeparator());
                entrada.write(total + System.lineSeparator());
            }

            // Leer toda la salida del Auditor y mostrarla tal cual.
            try (BufferedReader salida = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = salida.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            return proceso.waitFor();
        } catch (IOException e) {
            System.err.println("No se pudo comunicar con el Auditor: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrumpido mientras se esperaba al Auditor");
        }

        // Solo se llega aquí si ha habido un error: no dejar el hijo vivo.
        if (proceso != null) {
            proceso.destroy();
        }
        System.exit(1);
        return -1; // no se llega aquí
    }
}
