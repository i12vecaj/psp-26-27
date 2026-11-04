import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class SistemaVentas {

    private static final int AFORO = 200;
    private static final int[] CUPOS = {20, 30, 40, 50, 60};

    private static class Taquilla extends Thread {
        private final int cupo;
        private int vendidas;

        Taquilla(int numero, int cupo) {
            super("Taquilla-" + numero);
            this.cupo = cupo;
            this.vendidas = 0;
        }

        @Override
        public void run() {
            for (int i = 0; i < cupo; i++) {
                try {
                    Thread.sleep(5);
                    vendidas++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        int getVendidas() {
            return vendidas;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Taquilla[] taquillas = new Taquilla[CUPOS.length];

        for (int i = 0; i < CUPOS.length; i++) {
            taquillas[i] = new Taquilla(i + 1, CUPOS[i]);
        }

        long inicio = System.nanoTime();

        for (Taquilla taquilla : taquillas) {
            taquilla.start();
        }

        int totalVendido = 0;
        for (Taquilla taquilla : taquillas) {
            taquilla.join();
            totalVendido += taquilla.getVendidas();
        }

        long fin = System.nanoTime();
        long tiempoMs = (fin - inicio) / 1_000_000;

        for (int i = 0; i < taquillas.length; i++) {
            System.out.println("Taquilla " + (i + 1) + ": "
                    + taquillas[i].getVendidas() + " entradas");
        }
        System.out.println("Total vendido: " + totalVendido + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + tiempoMs + " ms");

        ejecutarAuditor(AFORO, totalVendido);
    }

    private static void ejecutarAuditor(int aforo, int totalVendido)
            throws IOException, InterruptedException {
        String java = Path.of(System.getProperty("java.home"), "bin",
                System.getProperty("os.name").toLowerCase().contains("win")
                        ? "java.exe" : "java").toString();
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder builder = new ProcessBuilder(java, "-cp", classpath, "Auditor");
        builder.redirectErrorStream(true);
        Process proceso = builder.start();

        try (OutputStream entrada = proceso.getOutputStream()) {
            String datos = aforo + System.lineSeparator()
                    + totalVendido + System.lineSeparator();
            entrada.write(datos.getBytes(StandardCharsets.UTF_8));
            entrada.flush();
        }

        try (BufferedReader salida = new BufferedReader(
                new InputStreamReader(proceso.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = salida.readLine()) != null) {
                System.out.println(linea);
            }
        }

        int codigo = proceso.waitFor();
        System.out.println("Codigo del auditor: " + codigo);
    }
}
