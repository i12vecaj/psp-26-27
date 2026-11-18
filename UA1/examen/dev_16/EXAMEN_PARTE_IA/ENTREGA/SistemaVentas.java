import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Sistema de ventas de un teatro.
 * Cinco taquillas venden a la vez (un hilo por taquilla) y, al terminar todas,
 * se lanza el proceso independiente Auditor para comprobar el total vendido.
 */
public class SistemaVentas {

    private static final int AFORO = 200;
    private static final int[] CUPOS_TAQUILLAS = {20, 30, 40, 50, 60};

    public static void main(String[] args) throws InterruptedException {
        // Un objeto Taquilla distinto por hilo: cada uno tiene su cupo y su contador (H1)
        Taquilla[] taquillas = new Taquilla[CUPOS_TAQUILLAS.length];
        Thread[] hilos = new Thread[CUPOS_TAQUILLAS.length];
        for (int i = 0; i < taquillas.length; i++) {
            taquillas[i] = new Taquilla(i + 1, CUPOS_TAQUILLAS[i]);
            hilos[i] = new Thread(taquillas[i], "Taquilla-" + (i + 1));
        }

        // Se arrancan todos los hilos y solo después se espera a todos (H2, H6)
        long inicio = System.nanoTime();
        for (Thread hilo : hilos) {
            hilo.start();
        }
        for (Thread hilo : hilos) {
            hilo.join();
        }
        long fin = System.nanoTime();

        // Los resultados se consultan cuando todos los hilos han terminado (H3)
        int totalVendido = 0;
        for (Taquilla taquilla : taquillas) {
            System.out.println("Taquilla " + taquilla.getNumero() + ": "
                    + taquilla.getVendidas() + " entradas");
            totalVendido += taquilla.getVendidas();
        }
        System.out.println("Total vendido: " + totalVendido + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + (fin - inicio) / 1_000_000 + " ms");

        lanzarAuditor(AFORO, totalVendido);
    }

    /**
     * Ejecuta Auditor como proceso Java independiente, le envía los datos por su
     * entrada estándar, muestra su veredicto y recoge su código de salida.
     */
    private static void lanzarAuditor(int aforo, int totalVendido) throws InterruptedException {
        String ejecutableJava = Path.of(System.getProperty("java.home"), "bin", "java").toString();
        ProcessBuilder constructor = new ProcessBuilder(
                ejecutableJava, "-cp", System.getProperty("java.class.path"), "Auditor");
        constructor.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process auditor = constructor.start();

            // stdin del hijo: exactamente dos líneas y cierre para enviar EOF (P2).
            // BufferedWriter, a diferencia de PrintWriter, no oculta los errores de escritura.
            try (BufferedWriter entrada = new BufferedWriter(
                    new OutputStreamWriter(auditor.getOutputStream(), StandardCharsets.UTF_8))) {
                entrada.write(String.valueOf(aforo));
                entrada.newLine();
                entrada.write(String.valueOf(totalVendido));
                entrada.newLine();
            }

            // stdout del hijo: se lee entera antes de esperar su final (P3)
            try (BufferedReader salida = new BufferedReader(
                    new InputStreamReader(auditor.getInputStream(), StandardCharsets.UTF_8))) {
                String linea;
                while ((linea = salida.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            int codigoSalida = auditor.waitFor(); // (P4)
            System.out.println("Codigo del auditor: " + codigoSalida);
        } catch (IOException e) {
            System.err.println("No se pudo ejecutar el proceso Auditor: " + e.getMessage());
            System.exit(1);
        }
    }

    /** Taquilla que vende únicamente su propio cupo de entradas. */
    private static class Taquilla implements Runnable {

        private final int numero;
        private final int cupo;
        private int vendidas;

        Taquilla(int numero, int cupo) {
            this.numero = numero;
            this.cupo = cupo;
        }

        @Override
        public void run() {
            try {
                while (vendidas < cupo) {
                    Thread.sleep(5); // simula el tiempo de una venta (H5)
                    vendidas++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int getNumero() {
            return numero;
        }

        int getVendidas() {
            return vendidas;
        }
    }
}
