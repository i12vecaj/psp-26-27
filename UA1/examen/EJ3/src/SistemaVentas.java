import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SistemaVentas {

    private static final int AFORO = 200;

    public static void main(String[] args) {
        int[] cupos = {20, 30, 40, 50, 60};
        Taquilla[] taquillas = new Taquilla[cupos.length];
        for (int i = 0; i < cupos.length; i++) {
            taquillas[i] = new Taquilla(cupos[i]);
        }

        long inicio = System.nanoTime();
        for (Taquilla taquilla : taquillas) {
            taquilla.start();
        }
        try {
            for (Taquilla taquilla : taquillas) {
                taquilla.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        long fin = System.nanoTime();
        long milisegundos = (fin - inicio) / 1_000_000;

        int total = 0;
        for (int i = 0; i < taquillas.length; i++) {
            int vendidas = taquillas[i].getVendidas();
            System.out.println("Taquilla " + (i + 1) + ": " + vendidas + " entradas");
            total += vendidas;
        }
        System.out.println("Total vendido: " + total + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + milisegundos + " ms");

        try {
            lanzarAuditor(total);
        } catch (IOException | InterruptedException e) {
            System.out.println("No se pudo ejecutar el Auditor: " + e.getMessage());
        }
    }

    private static void lanzarAuditor(int total) throws IOException, InterruptedException {
        Process auditor = new ProcessBuilder("java", "-cp",
                System.getProperty("java.class.path"), "Auditor").start();

        PrintWriter alAuditor = new PrintWriter(auditor.getOutputStream());
        alAuditor.println(AFORO);
        alAuditor.println(total);
        alAuditor.flush();
        alAuditor.close();

        BufferedReader delAuditor = new BufferedReader(new InputStreamReader(auditor.getInputStream()));
        String linea;
        while ((linea = delAuditor.readLine()) != null) {
            System.out.println(linea);
        }
        delAuditor.close();

        int codigo = auditor.waitFor();
        System.out.println("Codigo del auditor: " + codigo);
    }
}
