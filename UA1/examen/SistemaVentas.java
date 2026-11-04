import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Simula la venta de entradas de un teatro con cinco taquillas concurrentes y
 * lanza el proceso independiente Auditor para verificar el total.
 */
public class SistemaVentas {

    private static final int AFORO = 200;
    private static final int[] CUPOS = {20, 30, 40, 50, 60};
    private static final int MS_POR_VENTA = 5;
    private static final long TIMEOUT_AUDITOR_S = 10;

    /** Taquilla: hilo con sus propios datos, nadie mas modifica 'vendidas'. */
    private static final class Taquilla extends Thread {
        private final int cupo;
        private int vendidas;

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
                    return; // termina de forma ordenada con lo vendido hasta ahora
                }
                vendidas++;
            }
        }

        int getVendidas() {
            return vendidas;
        }
    }

    public static void main(String[] args) throws Exception {
        List<Taquilla> taquillas = new ArrayList<>();
        for (int i = 0; i < CUPOS.length; i++) {
            taquillas.add(new Taquilla(i + 1, CUPOS[i]));
        }

        // Primero se lanzan todas (a la vez)...
        long inicio = System.nanoTime();
        for (Taquilla t : taquillas) {
            t.start();
        }
        // ...y despues se espera a todas.
        for (Taquilla t : taquillas) {
            t.join();
        }
        long tiempoMs = (System.nanoTime() - inicio) / 1_000_000;

        // Lectura de resultados solo cuando todas han terminado.
        int total = 0;
        for (int i = 0; i < taquillas.size(); i++) {
            int vendidas = taquillas.get(i).getVendidas();
            total += vendidas;
            System.out.println("Taquilla " + (i + 1) + ": " + vendidas + " entradas");
        }
        System.out.println("Total vendido: " + total + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + tiempoMs + " ms");

        int codigo = ejecutarAuditor(AFORO, total);
        System.out.println("Codigo del auditor: " + codigo);
    }

    /** Lanza Auditor como proceso independiente, le envia los datos y devuelve su codigo de salida. */
    private static int ejecutarAuditor(int aforo, int total) throws Exception {
        String ejecutableJava = Paths.get(System.getProperty("java.home"), "bin", "java").toString();
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(ejecutableJava, "-cp", classpath, "Auditor");
        // stderr del hijo va a la consola del padre: no se mezcla con su veredicto.
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process proceso = pb.start();

        try (Writer salida = new OutputStreamWriter(proceso.getOutputStream(), StandardCharsets.UTF_8)) {
            salida.write(aforo + "\n");
            salida.write(total + "\n");
        } // al cerrar, el Auditor recibe fin de entrada

        // El veredicto es de una sola linea: cabe en el buffer de la tuberia.
        try (BufferedReader entrada = new BufferedReader(
                new InputStreamReader(proceso.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                System.out.println(linea);
            }
        }

        if (!proceso.waitFor(TIMEOUT_AUDITOR_S, TimeUnit.SECONDS)) {
            proceso.destroyForcibly();
            throw new IllegalStateException("El Auditor no termino a tiempo");
        }
        return proceso.exitValue();
    }
}
