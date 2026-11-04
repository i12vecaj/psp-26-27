import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/* Hilo de una taquilla: vende su cupo y guarda cuantas entradas ha vendido */
class Taquilla implements Runnable {
    private int cupo;
    private int vendidas = 0;

    public Taquilla(int cupo) {
        this.cupo = cupo;
    }

    public void run() {
        try {
            for (int i = 0; i < cupo; i++) {
                Thread.sleep(5);
                vendidas++;
            }
        } catch (InterruptedException e) {
            System.err.println("Taquilla interrumpida: " + e.getMessage());
        }
    }

    public int getVendidas() {
        return vendidas;
    }
}

/**
 * Lanza las cinco taquillas a la vez, muestra lo vendido y el tiempo,
 * y despues pide al proceso Auditor que compruebe el total.
 */
public class SistemaVentas {
    public static void main(String[] args) {
        int aforo = 200;
        int[] cupos = {20, 30, 40, 50, 60};
        Taquilla[] taquillas = new Taquilla[cupos.length];
        Thread[] hilos = new Thread[cupos.length];

        for (int i = 0; i < cupos.length; i++) {
            taquillas[i] = new Taquilla(cupos[i]);
            hilos[i] = new Thread(taquillas[i]);
        }

        try {
            long inicio = System.currentTimeMillis();

            // Primero se lanzan todas, para que vendan a la vez
            for (int i = 0; i < hilos.length; i++) {
                hilos[i].start();
            }

            // Despues se espera a que terminen todas
            for (int i = 0; i < hilos.length; i++) {
                hilos[i].join();
            }

            long fin = System.currentTimeMillis();

            // Los resultados se leen solo cuando ya han terminado los hilos
            int total = 0;
            for (int i = 0; i < taquillas.length; i++) {
                System.out.println("Taquilla " + (i + 1) + ": " + taquillas[i].getVendidas() + " entradas");
                total += taquillas[i].getVendidas();
            }
            System.out.println("Total vendido: " + total + " de " + aforo);
            System.out.println("Tiempo de ventas: " + (fin - inicio) + " ms");

            lanzarAuditor(aforo, total);
        } catch (IOException e) {
            System.err.println("Error al comunicarse con el Auditor: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("Se interrumpio una espera: " + e.getMessage());
            System.exit(1);
        }
    }

    /* Lanza el Auditor, le envia los datos y muestra su respuesta y su codigo */
    static void lanzarAuditor(int aforo, int total) throws IOException, InterruptedException {
        String path = System.getProperty("java.class.path");
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", path, "Auditor");
        // Si el Auditor no puede arrancar, el error se ve en la consola
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process auditor = pb.start();

        // Se cierra la entrada para que el Auditor no se quede esperando
        PrintWriter escritor = new PrintWriter(auditor.getOutputStream(), true);
        escritor.println(aforo);
        escritor.println(total);
        escritor.close();

        BufferedReader lector = new BufferedReader(new InputStreamReader(auditor.getInputStream()));
        String linea;
        while ((linea = lector.readLine()) != null) {
            System.out.println(linea);
        }

        System.out.println("Codigo del auditor: " + auditor.waitFor());
    }
}
