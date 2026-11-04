import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Programa principal del sistema de ventas del teatro.
 * Lanza cinco taquillas (hilos) que venden a la vez, espera a que terminen,
 * muestra el resultado y lanza el proceso independiente Auditor.
 */
public class SistemaVentas {

    private static final int AFORO = 200;
    private static final int[] CUPOS = {20, 30, 40, 50, 60};

    public static void main(String[] args) {
        // Cada taquilla es un hilo con sus propios datos (regla H1).
        List<Taquilla> taquillas = new ArrayList<>();
        for (int i = 0; i < CUPOS.length; i++) {
            taquillas.add(new Taquilla(i + 1, CUPOS[i]));
        }

        // Primero se lanzan todas (H2), luego se espera a todas (H4).
        long inicio = System.nanoTime();
        for (Taquilla t : taquillas) {
            t.start();
        }
        for (Taquilla t : taquillas) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrumpido mientras se esperaba a las taquillas");
                return;
            }
        }
        long fin = System.nanoTime();
        long tiempoMs = (fin - inicio) / 1_000_000;

        // Solo ahora se leen los resultados, ya terminadas todas las taquillas.
        int total = 0;
        for (Taquilla t : taquillas) {
            System.out.println("Taquilla " + t.getNumero() + ": " + t.getVendidas() + " entradas");
            total += t.getVendidas();
        }
        System.out.println("Total vendido: " + total + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + tiempoMs + " ms");

        int codigo = lanzarAuditor(AFORO, total);
        System.out.println("Codigo del auditor: " + codigo);
    }

    /**
     * Lanza el Auditor como proceso independiente, le envía el aforo y el total
     * por su entrada estándar, muestra lo que escribe y devuelve su código de salida.
     * Devuelve -1 si no se ha podido lanzar o se ha interrumpido la espera.
     */
    private static int lanzarAuditor(int aforo, int total) {
        String rutaJava = System.getProperty("java.home") + File.separator
                + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(rutaJava, "-cp", classpath, "Auditor");
        pb.redirectErrorStream(true);

        try {
            Process proceso = pb.start();

            // Se escriben los dos datos y se cierra el flujo para que el hijo vea el fin de la entrada.
            try (PrintWriter haciaHijo = new PrintWriter(
                    new OutputStreamWriter(proceso.getOutputStream()))) {
                haciaHijo.println(aforo);
                haciaHijo.println(total);
            }

            // Se lee la respuesta completa antes de waitFor() (regla P6).
            try (BufferedReader respuesta = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = respuesta.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            return proceso.waitFor();
        } catch (IOException e) {
            System.err.println("No se pudo ejecutar el Auditor: " + e.getMessage());
            return -1;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrumpido mientras se esperaba al Auditor");
            return -1;
        }
    }
}
