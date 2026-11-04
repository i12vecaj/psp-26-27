import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Lanza cinco taquillas en paralelo, muestra el resultado de las ventas y
 * despues lanza el proceso independiente Auditor para validar el total.
 */
public class SistemaVentas {

    private static final int AFORO = 200;
    private static final int[] CUPOS = {20, 30, 40, 50, 60};

    public static void main(String[] args) throws InterruptedException {
        List<Taquilla> taquillas = new ArrayList<>();
        List<Thread> hilos = new ArrayList<>();
        for (int i = 0; i < CUPOS.length; i++) {
            Taquilla taquilla = new Taquilla(i + 1, CUPOS[i]);
            taquillas.add(taquilla);
            hilos.add(new Thread(taquilla, "Taquilla-" + (i + 1)));
        }

        // Reglas H1 y H2: arrancar todas, despues esperar a todas, midiendo con nanoTime
        long inicio = System.nanoTime();
        for (Thread hilo : hilos) {
            hilo.start();
        }
        for (Thread hilo : hilos) {
            hilo.join();
        }
        long tiempoMs = (System.nanoTime() - inicio) / 1_000_000;

        // Regla H3: los resultados se leen solo cuando todos los hilos han terminado
        int total = 0;
        for (Taquilla taquilla : taquillas) {
            System.out.println("Taquilla " + taquilla.getNumero() + ": " + taquilla.getVendidas() + " entradas");
            total += taquilla.getVendidas();
        }
        System.out.println("Total vendido: " + total + " de " + AFORO);
        System.out.println("Tiempo de ventas: " + tiempoMs + " ms");

        int codigo = ejecutarAuditor(AFORO, total);
        System.out.println("Codigo del auditor: " + codigo);
    }

    /** Lanza Auditor como proceso independiente, le envia los datos y devuelve su codigo de salida. */
    private static int ejecutarAuditor(int aforo, int total) throws InterruptedException {
        // Regla P1: misma JVM y mismo classpath, sin rutas fijas, y un argumento por elemento
        String java = Paths.get(System.getProperty("java.home"), "bin", "java").toString();
        String classpath = System.getProperty("java.class.path");
        ProcessBuilder pb = new ProcessBuilder(java, "-cp", classpath, "Auditor");
        pb.redirectError(ProcessBuilder.Redirect.INHERIT); // los errores del hijo no se pierden

        try {
            Process proceso = pb.start();

            // Regla P2: escribir en la entrada del hijo y cerrarla
            try (PrintWriter entrada = new PrintWriter(proceso.getOutputStream())) {
                entrada.println(aforo);
                entrada.println(total);
            }

            // Regla P3: leer toda la salida antes de waitFor()
            try (BufferedReader salida = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = salida.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            return proceso.waitFor(); // regla P4
        } catch (IOException e) {
            System.err.println("No se pudo ejecutar el Auditor: " + e.getMessage());
            return -1;
        }
    }
}
