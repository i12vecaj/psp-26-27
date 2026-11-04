import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class SistemaVentas {
    static class TaquillaThread extends Thread {
        private String nombre;
        private int cupo;
        private int vendidas;

        public TaquillaThread(String nombre, int cupo){
            this.nombre = nombre;
            this.cupo = cupo;
            this.vendidas = 0;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < cupo; i++) {
                    Thread.sleep(5);
                    vendidas++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public int getVendidas(){
            return vendidas;
        }
    }

    public static void main(String[] args){
        int aforoTotal = 200;
        int[] cupos = {20, 30, 40, 50, 60};
        TaquillaThread[] taquillas = new TaquillaThread[5];

        // Medir el tiempo real de ejecución en paralelo
        long inicioTiempo = System.currentTimeMillis();

        // Lanzar todas las taquillas a la vez
        for (int i = 0; i < 5; i++) {
            taquillas[i] = new TaquillaThread("Taquilla " + (i + 1), cupos[i]);
            taquillas[i].start();
        }

        for (int i = 0; i < 5; i++) {
            try {
                taquillas[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long finTiempo = System.currentTimeMillis();
        long tiempoTotal = finTiempo - inicioTiempo;

        // Recoger y mostrar los resultados obligatorios
        int totalVendido = 0;
        for (int i = 0; i < 5; i++) {
            int v = taquillas[i].getVendidas();
            totalVendido += v;
            System.out.println("Taquilla " + (i + 1) + ": " + v + " entradas");
        }

        System.out.println("Total vendido: " + totalVendido + " de " + aforoTotal);
        System.out.println("Tiempo de ventas: " + tiempoTotal + " ms");

        // Lanzar el proceso independiente Auditor
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp", System.getProperty("java.class.path"), "Auditor"
            );
            Process procesoAuditor = pb.start();

            // Enviar datos por la entrada estándar del proceso
            try (PrintWriter pw = new PrintWriter(procesoAuditor.getOutputStream())) {
                pw.println(aforoTotal);
                pw.println(totalVendido);
            }

            // Leer la salida del Auditor
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(procesoAuditor.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            int codigoAuditor = procesoAuditor.waitFor();
            System.out.println("Codigo del auditor: " + codigoAuditor);

        } catch (IOException | InterruptedException e) {
            System.err.println("Error al ejecutar o comunicar con el proceso Auditor: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}