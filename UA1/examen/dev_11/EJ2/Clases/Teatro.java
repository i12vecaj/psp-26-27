import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Teatro {
    private static final int AFORO = 200;
    private static final int[] CUPOS = {20, 30, 40, 50, 60};

    public static void main(String[] args) {
        Taquilla[] taquillas = new Taquilla[CUPOS.length];
        Thread[] hilos = new Thread[CUPOS.length];
        for (int i = 0; i < CUPOS.length; i++) {
            taquillas[i] = new Taquilla(CUPOS[i]);
            hilos[i] = new Thread(taquillas[i], "Taquilla-" + (i + 1));
        }

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
            System.err.println("Espera de las taquillas interrumpida");
            return;
        }
        long fin = System.nanoTime();

        int total = 0;
        for (int i = 0; i < taquillas.length; i++) {
            System.out.println("Taquilla " + (i + 1) + ": " + taquillas[i].getVendidas() + " entradas");
            total += taquillas[i].getVendidas();
        }
        System.out.println("Total vendido: " + total);
        System.out.println("Tiempo: " + (fin - inicio) / 1_000_000 + " ms");

        ejecutarAuditor(total);
    }

    private static void ejecutarAuditor(int totalVendido) {
        try {
            String javaBin = System.getProperty("java.home") + File.separator
                    + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            ProcessBuilder constructor = new ProcessBuilder(
                    javaBin, "-cp", classpath, "Auditor");
            constructor.redirectErrorStream(true);
            Process auditor = constructor.start();

            try (BufferedWriter escritor = new BufferedWriter(
                    new OutputStreamWriter(auditor.getOutputStream()))) {
                escritor.write(String.valueOf(AFORO));
                escritor.newLine();
                escritor.write(String.valueOf(totalVendido));
                escritor.newLine();
                escritor.flush();
            }

            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(auditor.getInputStream()))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    System.out.println("Auditor: " + linea);
                }
            }

            int codigo = auditor.waitFor();
            System.out.println("Codigo de salida del Auditor: " + codigo);
        } catch (IOException e) {
            System.err.println("Error al ejecutar el Auditor: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Espera del Auditor interrumpida");
        }
    }
}
