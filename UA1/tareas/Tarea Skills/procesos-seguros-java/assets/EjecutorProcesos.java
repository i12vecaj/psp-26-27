import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

/**
 * Ejecutor de procesos con timeout, captura de stdout/stderr en paralelo
 * y límite de tamaño de salida. Plantilla reutilizable.
 */
public class EjecutorProcesos {

    public record Resultado(long pid, int codigo, boolean timeout,
                            String salida, String error, Duration duracion) {
        public boolean ok() { return !timeout && codigo == 0; }
    }

    private static final int MAX_CARACTERES = 100_000;

    public static Resultado ejecutar(List<String> comando, Duration limite)
            throws IOException, InterruptedException {
        long inicio = System.nanoTime();

        ProcessBuilder pb = new ProcessBuilder(comando);   // argumentos separados: sin shell
        Process p = pb.start();
        long pid = p.pid();

        // stdin no se usa: lo cerramos para que el hijo no espere datos
        p.getOutputStream().close();

        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            Future<String> fOut = pool.submit(() -> leer(p.getInputStream()));
            Future<String> fErr = pool.submit(() -> leer(p.getErrorStream()));

            boolean termino = p.waitFor(limite.toMillis(), TimeUnit.MILLISECONDS);
            if (!termino) {
                p.destroy();
                if (!p.waitFor(2, TimeUnit.SECONDS)) p.destroyForcibly();
                p.waitFor();
            }
            String salida = obtener(fOut);
            String error = obtener(fErr);
            int codigo = termino ? p.exitValue() : -1;
            return new Resultado(pid, codigo, !termino, salida, error,
                    Duration.ofNanos(System.nanoTime() - inicio));
        } finally {
            if (p.isAlive()) p.destroyForcibly();
            pool.shutdownNow();
        }
    }

    private static String leer(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            char[] buf = new char[4096];
            int n;
            while ((n = br.read(buf)) != -1) {            // seguimos leyendo aunque se trunque,
                int hueco = MAX_CARACTERES - sb.length();  // para no bloquear al hijo
                if (hueco > 0) sb.append(buf, 0, Math.min(n, hueco));
            }
        }
        return sb.toString();
    }

    private static String obtener(Future<String> f) throws InterruptedException {
        try { return f.get(2, TimeUnit.SECONDS); }
        catch (ExecutionException | TimeoutException e) { return ""; }
    }

    // ---- Demostración ----
    public static void main(String[] args) throws Exception {
        boolean win = System.getProperty("os.name").toLowerCase().contains("win");

        List<String> listar = win ? List.of("cmd", "/c", "dir") : List.of("ls", "-l");
        List<String> falla  = win ? List.of("cmd", "/c", "dir", "noexiste") : List.of("ls", "noexiste");
        List<String> lento  = win ? List.of("ping", "-n", "30", "127.0.0.1") : List.of("sleep", "30");

        for (var c : List.of(listar, falla, lento)) {
            Resultado r = ejecutar(c, Duration.ofSeconds(3));
            System.out.printf("%s -> pid=%d codigo=%d timeout=%b ok=%b (%d ms)%n",
                    c, r.pid(), r.codigo(), r.timeout(), r.ok(), r.duracion().toMillis());
        }
    }
}
