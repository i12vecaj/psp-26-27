import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

class Taquilla extends Thread {
    private final int numero;
    private final int cupo;
    private int vendidas;

    public Taquilla(int numero, int cupo) {
        this.numero = numero;
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
                break;
            }
        }
    }

    public int getNumero() {
        return numero;
    }

    public int getCupo() {
        return cupo;
    }

    public int getVendidas() {
        return vendidas;
    }
}

public class SistemaVentas {
    public static void main(String[] args) {
        final int aforo = 200;
        int[] cupos = {20, 30, 40, 50, 60};
        Taquilla[] taquillas = new Taquilla[cupos.length];

        for (int i = 0; i < cupos.length; i++) {
            taquillas[i] = new Taquilla(i + 1, cupos[i]);
        }

        long inicio = System.currentTimeMillis();

        // Lanzar todos los hilos de las taquillas concurrentemente
        for (Taquilla t : taquillas) {
            t.start();
        }

        // Esperar a que todas las taquillas finalicen sus ventas
        for (Taquilla t : taquillas) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long fin = System.currentTimeMillis();
        long tiempoVentas = fin - inicio;

        // Mostrar ventas por taquilla y calcular el total vendido
        int totalVendido = 0;
        for (Taquilla t : taquillas) {
            System.out.println("Taquilla " + t.getNumero() + ": " + t.getVendidas() + " entradas");
            totalVendido += t.getVendidas();
        }

        System.out.println("Total vendido: " + totalVendido + " de " + aforo);
        System.out.println("Tiempo de ventas: " + tiempoVentas + " ms");

        // Lanzar proceso independiente Auditor usando ProcessBuilder
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "Auditor");
            try {
                File classDir = new File(SistemaVentas.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                if (classDir.exists() && classDir.isDirectory()) {
                    pb.directory(classDir);
                }
            } catch (Exception ignored) {
            }

            Process proceso;
            try {
                proceso = pb.start();
            } catch (IOException ioe) {
                String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
                pb.command(javaBin, "Auditor");
                proceso = pb.start();
            }

            // Enviar aforo y total vendido por la entrada estándar del proceso Auditor
            try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(proceso.getOutputStream()))) {
                pw.println(aforo);
                pw.println(totalVendido);
                pw.flush();
            }

            // Leer y mostrar la respuesta textual del Auditor
            try (BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            // Esperar al proceso y obtener su código de salida
            proceso.waitFor();
            int codigoSalida = proceso.exitValue();
            System.out.println("Codigo del auditor: " + codigoSalida);

        } catch (Exception e) {
            System.err.println("Error al ejecutar Auditor: " + e.getMessage());
        }
    }
}

