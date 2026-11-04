import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * Cinco taquillas (hilos) venden a la vez, cada una con su propio cupo.
 * Despues se lanza el proceso independiente Auditor para comprobar el total.
 */
public class SistemaVentas {

    private static final int AFORO = 200;
    private static final int[] CUPOS = {20, 30, 40, 50, 60};
    private static final int MS_POR_VENTA = 5;

    /** Hilo con sus propios datos: nadie mas escribe en 'vendidas'. */
    private static class Taquilla extends Thread {
        private final int cupo;
        private int vendidas = 0; // solo la toca este hilo

        Taquilla(int numero, int cupo) {
            super("Taquilla-" + numero);
            this.cupo = cupo;
        }

        @Override
        public void run() {
            for (int i = 0; i < cupo; i++) {
                try {
                    Thread.sleep(MS_POR_VENTA);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; // se queda con lo vendido hasta ahora
                }
                vendidas++;
            }
        }

        /** Solo debe llamarse cuando el hilo ha terminado (tras join). */
        int getVendidas() {
            return vendidas;
        }
    }

    public static void main(String[] args) {
        Taquilla[] taquillas = new Taquilla[CUPOS.length];
        for (int i = 0; i < CUPOS.length; i++) {
            taquillas[i] = new Taquilla(i + 1, CUPOS[i]);
        }

        // 1) Lanzar TODAS antes de esperar a ninguna
        long inicio = System.nanoTime();
        for (Taquilla t : taquillas) {
            t.start();
        }

        // 2) Esperar a todas
        try {
            for (Taquilla t : taquillas) {
                t.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Ventas interrumpidas");
            System.exit(1);
        }
        long fin = System.nanoTime();
        long tiempoMs = (fin - inicio) / 1_000_000;

        // 3) Leer resultados (ya han terminado)
        int total = 0;
        for (int i = 0; i < taquillas.length; i++) {
            int v = taquillas[i].getVendidas();
            System.out.println("Taquilla " + (i + 1) + ": " + v + " entradas");
            total += v;
        }
        System.out.println("Total vendido: " + total + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + tiempoMs + " ms");

        // 4) Proceso independiente
        lanzarAuditor(AFORO, total);
    }

    private static void lanzarAuditor(int aforo, int total) {
        String ejecutableJava = System.getProperty("java.home") + File.separator + "bin"
                + File.separator + "java";
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(ejecutableJava, "-cp", classpath, "Auditor");
        pb.redirectErrorStream(true);

        try {
            Process proceso = pb.start();

            // Enviar los dos datos por la entrada estandar del proceso y cerrarla
            try (BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(proceso.getOutputStream(), StandardCharsets.UTF_8))) {
                out.write(aforo + "\n");
                out.write(total + "\n");
                out.flush();
            }

            // Leer su respuesta hasta que termine
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream(), StandardCharsets.UTF_8))) {
                String linea;
                while ((linea = in.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            int codigo = proceso.waitFor();
            System.out.println("Codigo del auditor: " + codigo);
        } catch (IOException e) {
            System.out.println("No se pudo ejecutar el Auditor: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Espera del Auditor interrumpida");
        }
    }
}