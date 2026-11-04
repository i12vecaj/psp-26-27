import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

/**
 * Clase principal que simula la venta de entradas de un teatro
 * con un aforo de 200 personas.
 *
 * Se crean cinco taquillas utilizando hilos (Thread), cada una
 * con su propio cupo de entradas. Las taquillas trabajan de
 * forma concurrente y cada venta tarda 5 milisegundos.
 *
 * Se utiliza start() para iniciar los hilos y join() para
 * esperar a que terminen todas las ventas.
 *
 * Una vez finalizadas, se muestran las entradas vendidas,
 * el total y el tiempo empleado. Finalmente, se ejecuta
 * la clase Auditor como un proceso independiente mediante
 * ProcessBuilder para comprobar el resultado.
 */

public class SistemaVentas {
    private static final int AFORO = 200;

    private static class Taquilla extends Thread {
        private final int cupo;
        private int vendidas;

        Taquilla(int cupo) {
            this.cupo = cupo;
            this.vendidas = 0;
        }

        @Override
        public void run() {
            for (int entrada = 0; entrada < cupo; entrada++) {
                try {
                    Thread.sleep(5);
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
        int[] cupos = {20, 30, 40, 50, 60};
        Taquilla[] taquillas = new Taquilla[cupos.length];

        for (int i = 0; i < cupos.length; i++) {
            taquillas[i] = new Taquilla(cupos[i]);
        }

        long inicio = System.nanoTime();
        for (Taquilla taquilla : taquillas) {
            taquilla.start();
        }
        for (Taquilla taquilla : taquillas) {
            taquilla.join();
        }
        long tiempoMs = (System.nanoTime() - inicio) / 1_000_000;

        int totalVendido = 0;
        for (int i = 0; i < taquillas.length; i++) {
            int vendidas = taquillas[i].getVendidas();
            totalVendido += vendidas;
            System.out.println("Taquilla " + (i + 1) + ": " + vendidas + " entradas");
        }
        System.out.println("Total vendido: " + totalVendido + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + tiempoMs + " ms");

        String ejecutable = System.getProperty("os.name").toLowerCase().contains("win")
                ? "java.exe" : "java";
        String rutaJava = Path.of(System.getProperty("java.home"), "bin", ejecutable).toString();
        String classpath = System.getProperty("java.class.path");
        Process auditor = new ProcessBuilder(rutaJava, "-cp", classpath, "Auditor").start();

        try (BufferedWriter escritor = new BufferedWriter(
                new OutputStreamWriter(auditor.getOutputStream()))) {
            escritor.write(Integer.toString(AFORO));
            escritor.newLine();
            escritor.write(Integer.toString(totalVendido));
            escritor.newLine();
        }

        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(auditor.getInputStream()))) {
            String veredicto = lector.readLine();
            if (veredicto == null) {
                throw new IOException("El auditor no ha enviado un veredicto");
            }
            System.out.println(veredicto);
        }

        int codigo = auditor.waitFor();
        System.out.println("Codigo del auditor: " + codigo);
    }
}
