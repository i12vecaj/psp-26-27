import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
 
public class SistemaVentas {
 
    private static final int AFORO = 200;
    private static final int[] CUPOS = {20, 30, 40, 50, 60};
    private static final long MS_POR_VENTA = 5;
 
    /** Taquilla: hilo con sus propios datos (cupo y vendidas). */
    private static class Taquilla extends Thread {
        private final int cupo;
        private int vendidas = 0;
 
        Taquilla(int cupo) {
            this.cupo = cupo;
        }
 
        @Override
        public void run() {
            for (int i = 0; i < cupo; i++) {
                try {
                    Thread.sleep(MS_POR_VENTA);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                vendidas++;
            }
        }
 
        int getVendidas() {
            return vendidas;
        }
    }
 
    public static void main(String[] args) throws IOException, InterruptedException {
        Taquilla[] taquillas = new Taquilla[CUPOS.length];
        for (int i = 0; i < CUPOS.length; i++) {
            taquillas[i] = new Taquilla(CUPOS[i]);
        }
 
        long inicio = System.nanoTime();
        for (Taquilla t : taquillas) {
            t.start();
        }
        for (Taquilla t : taquillas) {
            t.join();
        }
        long ms = (System.nanoTime() - inicio) / 1_000_000;
 
        int total = 0;
        for (int i = 0; i < taquillas.length; i++) {
            int v = taquillas[i].getVendidas();
            total += v;
            System.out.println("Taquilla " + (i + 1) + ": " + v + " entradas");
        }
        System.out.println("Total vendido: " + total + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + ms + " ms");
 
        int codigo = lanzarAuditor(AFORO, total);
        System.out.println("Codigo del auditor: " + codigo);
    }
 
    /** Lanza Auditor como proceso independiente, le envia los datos y muestra su respuesta. */
    private static int lanzarAuditor(int aforo, int total) throws IOException, InterruptedException {
        String java = System.getProperty("java.home") + "/bin/java";
        String classpath = System.getProperty("java.class.path");
        ProcessBuilder pb = new ProcessBuilder(java, "-cp", classpath, "Auditor");
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process proceso = pb.start();
 
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(proceso.getOutputStream(), StandardCharsets.UTF_8))) {
            out.write(aforo + "\n");
            out.write(total + "\n");
            out.flush();
        }
 
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(proceso.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = in.readLine()) != null) {
                System.out.println(linea);
            }
        }
        return proceso.waitFor();
    }
}