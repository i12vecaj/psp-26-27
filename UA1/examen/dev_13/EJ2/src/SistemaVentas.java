import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class TaquillaTask extends Thread {
    private final int id;
    private final int entradas;
    private final long tiempoSimuladoMs;

    public TaquillaTask(int id, int entradas, long tiempoSimuladoMs) {
        this.id = id;
        this.entradas = entradas;
        this.tiempoSimuladoMs = tiempoSimuladoMs;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(tiempoSimuladoMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getIdTaquilla() {
        return id;
    }

    public int getEntradas() {
        return entradas;
    }
}

public class SistemaVentas {
    public static void main(String[] args) {
        // Datos específicos para cada taquilla que suman exactamente 200 entradas
        int[] entradasTaquilla = {40, 45, 35, 50, 30};
        long[] tiemposTaquilla = {280, 310, 250, 290, 300}; // Simulan ~300 ms en paralelo

        List<TaquillaTask> taquillas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            taquillas.add(new TaquillaTask(i + 1, entradasTaquilla[i], tiemposTaquilla[i]));
        }

        long inicioTiempo = System.currentTimeMillis();

        // Lanzar hilos de forma concurrente
        for (TaquillaTask t : taquillas) {
            t.start();
        }

        // Esperar a que terminen todas las taquillas (join)
        for (TaquillaTask t : taquillas) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long finTiempo = System.currentTimeMillis();
        long tiempoVentas = finTiempo - inicioTiempo;

        // Leer resultados de cada taquilla de forma independiente una vez terminadas
        int totalVendido = 0;
        for (TaquillaTask t : taquillas) {
            int v = t.getEntradas();
            totalVendido += v;
            System.out.println("Taquilla " + t.getIdTaquilla() + ": " + v + " entradas");
        }

        System.out.println("Total vendido: " + totalVendido + " de 200");
        System.out.println("Tiempo de ventas: " + tiempoVentas + " ms");

        // Lanzar el proceso independiente Auditor mediante ProcessBuilder
        int codigoAuditor = -1;
        String salidaAuditor = "";
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "Auditor");
            Process proceso = pb.start();

            // Enviar datos por la entrada estándar del proceso
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(proceso.getOutputStream()), true)) {
                writer.println("200");
                writer.println(totalVendido);
            }

            // Leer la salida estándar del proceso Auditor
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                salidaAuditor = reader.readLine();
            }

            codigoAuditor = proceso.waitFor();
        } catch (Exception e) {
            salidaAuditor = "Veredicto: Error al comunicar con el Auditor.";
            codigoAuditor = 3;
        }

        System.out.println(salidaAuditor);
        System.out.println("Codigo del auditor: " + codigoAuditor);
    }
}