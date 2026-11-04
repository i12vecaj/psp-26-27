import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Programa principal: lanza 5 taquillas (hilos), espera a que terminen,
 * escribe el resumen y lanza el proceso independiente Auditor.
 *
 * ESTRATEGIA DE CONCURRENCIA
 * - Cada Taquilla es un hilo con sus propios datos (cuota y vendidas).
 *   No hay ninguna variable modificable compartida entre hilos, por lo que
 *   no hacen falta cerrojos ni hay condiciones de carrera ni interbloqueos.
 * - El hilo principal solo lee el resultado de una taquilla despues de
 *   join(), que garantiza la visibilidad (happens-before) de su valor final.
 * - Todo hilo lanzado se espera con join(); ningun hilo queda colgado.
 *
 * ESTRATEGIA DE PROCESOS
 * - El Auditor se lanza con ProcessBuilder, se le envian dos lineas por la
 *   entrada estandar (que se cierra al terminar), se lee toda su salida y
 *   se recoge con waitFor() para no dejar procesos zombies.
 */
public class SistemaVentas {

    static final int AFORO = 200;
    static final int NUM_TAQUILLAS = 5;

    /** Taquilla: hilo que vende su cuota de entradas con datos propios. */
    static class Taquilla extends Thread {
        private final int cuota;     // solo la usa este hilo
        private int vendidas;        // solo la escribe este hilo; el main lee tras join()

        Taquilla(int numero, int cuota) {
            super("Taquilla-" + numero);
            this.cuota = cuota;
        }

        @Override
        public void run() {
            for (int i = 0; i < cuota; i++) {
                try {
                    Thread.sleep(2); // simula el tiempo de atender a un cliente
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // se conserva la interrupcion
                    return;                             // y se termina con lo vendido hasta ahora
                }
                vendidas++;
            }
        }

        int getVendidas() {
            return vendidas; // solo se invoca despues de join()
        }
    }

    public static void main(String[] args) {
        Taquilla[] taquillas = new Taquilla[NUM_TAQUILLAS];
        int cuota = AFORO / NUM_TAQUILLAS;
        for (int i = 0; i < NUM_TAQUILLAS; i++) {
            taquillas[i] = new Taquilla(i + 1, cuota);
        }

        // Medida de tiempo: desde el lanzamiento hasta que terminan todas.
        long inicio = System.nanoTime();
        for (Taquilla t : taquillas) {
            t.start();
        }
        try {
            for (Taquilla t : taquillas) {
                t.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrumpido mientras se esperaba a las taquillas");
            System.exit(1);
        }
        long ms = (System.nanoTime() - inicio) / 1_000_000;

        int total = 0;
        for (int i = 0; i < NUM_TAQUILLAS; i++) {
            int v = taquillas[i].getVendidas();
            total += v;
            System.out.println("Taquilla " + (i + 1) + ": " + v + " entradas");
        }
        System.out.println("Total vendido: " + total + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + ms + " ms");

        int codigo = lanzarAuditor(AFORO, total);
        System.out.println("Codigo del auditor: " + codigo);
    }

    /** Lanza el Auditor, reenvia su salida tal cual y devuelve su codigo de salida. */
    private static int lanzarAuditor(int aforo, int total) {
        String ejecutableJava = System.getProperty("java.home") + File.separator
                + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(ejecutableJava, "-cp", classpath, "Auditor");
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process proceso = null;
        try {
            proceso = pb.start();

            // Entrada estandar del Auditor: aforo y total, y se cierra.
            try (OutputStream os = proceso.getOutputStream()) {
                os.write((aforo + "\n" + total + "\n").getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            // Salida del Auditor, tal como la escribe.
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream(), StandardCharsets.UTF_8))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            return proceso.waitFor(); // recoge el proceso hijo
        } catch (IOException e) {
            System.err.println("Error de E/S con el Auditor: " + e.getMessage());
            return -1;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            if (proceso != null) {
                proceso.destroy();
            }
            return -1;
        }
    }
}
