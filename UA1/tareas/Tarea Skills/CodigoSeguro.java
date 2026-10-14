import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

/**
 * Versión corregida y segura de CodigoConFallos.java,
 * siguiendo la checklist de la skill 'procesos-seguros-java'.
 */
public class CodigoSeguro {

    private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");

    public record Resultado(int codigo, boolean timeout, String salida, String error) {
        public boolean ok() { return !timeout && codigo == 0; }
    }

    /**
     * Ejecuta un proceso con timeout, lectura concurrente de stdout y stderr,
     * y cierre de stdin.
     */
    public static Resultado ejecutar(List<String> comando, String stdin, Duration timeout)
            throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(comando);
        Process p = pb.start();

        // getOutputStream = ESCRIBO al proceso hijo
        if (stdin != null) {
            try (OutputStream os = p.getOutputStream()) {
                os.write(stdin.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }
        } else {
            p.getOutputStream().close(); // stdin no usado: cerrar inmediatamente
        }

        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            // getInputStream / getErrorStream = LEO del proceso hijo (en paralelo)
            Future<String> fOut = pool.submit(() -> leerStream(p.getInputStream()));
            Future<String> fErr = pool.submit(() -> leerStream(p.getErrorStream()));

            boolean termino = p.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS);
            if (!termino) {
                p.destroy();
                if (!p.waitFor(2, TimeUnit.SECONDS)) {
                    p.destroyForcibly();
                }
                p.waitFor();
            }

            String stdout = obtener(fOut);
            String stderr = obtener(fErr);
            int codigo = termino ? p.exitValue() : -1;
            return new Resultado(codigo, !termino, stdout, stderr);
        } finally {
            if (p.isAlive()) p.destroyForcibly();
            pool.shutdownNow();
        }
    }

    private static String leerStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            char[] buf = new char[4096];
            int n;
            int maxChars = 100_000;
            while ((n = br.read(buf)) != -1) {
                int hueco = maxChars - sb.length();
                if (hueco > 0) sb.append(buf, 0, Math.min(n, hueco));
            }
        }
        return sb.toString();
    }

    private static String obtener(Future<String> f) {
        try {
            return f.get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            return "";
        }
    }

    // Caso 1: Saludar seguro frente a inyecciones y multiplataforma
    static void saludar(String nombre) throws Exception {
        // Validación de datos no confiables (alfanumérico y espacios básicos)
        if (nombre == null || !nombre.matches("^[a-zA-Z0-9 _.-]{1,50}$")) {
            System.err.println("Entrada no válida: solo se permiten caracteres alfanuméricos simples.");
            return;
        }

        List<String> comando = IS_WINDOWS
                ? List.of("cmd.exe", "/c", "echo", "Hola " + nombre)
                : List.of("echo", "Hola " + nombre);

        Resultado r = ejecutar(comando, null, Duration.ofSeconds(3));
        if (r.ok()) {
            System.out.print(r.salida());
        } else {
            System.err.println("Error al ejecutar: " + r.error());
        }
    }

    // Caso 2: Producir muchos datos sin bloquear buffers (lectura concurrente)
    static void contarNumeros() throws Exception {
        List<String> comando = IS_WINDOWS
                ? List.of("powershell.exe", "-NoProfile", "-Command", "1..50000")
                : List.of("seq", "1", "50000");

        Resultado r = ejecutar(comando, null, Duration.ofSeconds(10));
        if (r.ok()) {
            long lineas = r.salida().lines().count();
            System.out.println("Números leídos correctamente sin bloqueo: " + lineas);
        } else {
            System.err.println("Fallo o timeout en proceso productor.");
        }
    }

    // Caso 3: sort interactivo cerrando getOutputStream()
    static void ordenar() throws Exception {
        // En Windows sort.exe está en System32; en Linux en /usr/bin/sort
        List<String> comando = List.of("sort");
        String datos = "pera\nmanzana\nbanana\n";

        Resultado r = ejecutar(comando, datos, Duration.ofSeconds(3));
        if (r.ok()) {
            System.out.print(r.salida());
        } else {
            System.err.println("Error al ordenar: " + r.error());
        }
    }

    // Caso 4: mostrar fichero validando ruta y comprobando exitValue/stderr
    static void mostrar(String fichero) throws Exception {
        Path base = Path.of(".").toRealPath();
        Path destino;
        try {
            destino = base.resolve(fichero).normalize();
            if (!destino.startsWith(base)) {
                System.err.println("Ruta rechazada: intento de evasión de directorio.");
                return;
            }
        } catch (Exception e) {
            System.err.println("Ruta inválida: " + e.getMessage());
            return;
        }

        List<String> comando = IS_WINDOWS
                ? List.of("cmd.exe", "/c", "type", destino.toString())
                : List.of("cat", destino.toString());

        Resultado r = ejecutar(comando, null, Duration.ofSeconds(5));
        if (r.ok()) {
            System.out.print(r.salida());
            System.out.println("Fichero mostrado correctamente.");
        } else {
            System.err.println("Error (" + r.codigo() + "): " + r.error().trim());
        }
    }

    public static void main(String[] args) throws Exception {
        String opcion = args.length > 0 ? args[0] : "all";
        String texto = args.length > 1 ? args[1] : "Ana";

        switch (opcion) {
            case "1" -> saludar(texto);
            case "2" -> contarNumeros();
            case "3" -> ordenar();
            case "4" -> mostrar(texto);
            default -> {
                System.out.println("=== Caso 1: Saludo seguro ===");
                saludar("Carlos");

                System.out.println("\n=== Caso 2: Consumo de flujo grande sin deadlock ===");
                contarNumeros();

                System.out.println("\n=== Caso 3: sort con stdin cerrado (EOF) ===");
                ordenar();

                System.out.println("\n=== Caso 4: Mostrar fichero seguro y gestión de errores ===");
                mostrar("src/CodigoConFallos.java");
                System.out.println("\n--- Caso 4 (fichero inexistente): ---");
                mostrar("inexistente.txt");
            }
        }
    }
}
