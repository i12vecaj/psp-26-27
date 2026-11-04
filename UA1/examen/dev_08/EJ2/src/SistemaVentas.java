import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SistemaVentas {

    private static final int AFORO = 200;
    private static final int[] CUPOS = {20, 30, 40, 50, 60};

    public static void main(String[] args) throws IOException {
        List<Taquilla> taquillas = new ArrayList<>();
        for (int i = 0; i < CUPOS.length; i++) {
            taquillas.add(new Taquilla(i + 1, CUPOS[i]));
        }

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
            System.err.println("Ventas interrumpidas");
            System.exit(1);
        }
        long milis = (System.nanoTime() - inicio) / 1_000_000;

        int total = 0;
        for (int i = 0; i < taquillas.size(); i++) {
            int vendidas = taquillas.get(i).getVendidas();
            total += vendidas;
            System.out.println("Taquilla " + (i + 1) + ": " + vendidas + " entradas");
        }
        System.out.println("Total vendido: " + total + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + milis + " ms");

        int codigo = lanzarAuditor(AFORO, total);
        System.out.println("Codigo del auditor: " + codigo);
    }

    private static int lanzarAuditor(int aforo, int total) throws IOException {
        String java = System.getProperty("java.home") + "/bin/java";
        String classpath = System.getProperty("java.class.path");
        ProcessBuilder pb = new ProcessBuilder(java, "-cp", classpath, "Auditor");
        pb.redirectErrorStream(true);
        Process proceso = pb.start();

        try (PrintWriter salida = new PrintWriter(
                new OutputStreamWriter(proceso.getOutputStream(), StandardCharsets.UTF_8))) {
            salida.println(aforo);
            salida.println(total);
        }

        try (BufferedReader entrada = new BufferedReader(
                new InputStreamReader(proceso.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                System.out.println(linea);
            }
        }

        try {
            return proceso.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            proceso.destroy();
            return -1;
        }
    }
}
