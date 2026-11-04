import java.io.IOException;

public class Reto3 {

    private static Process lanzarEspera(int segundos) throws IOException {
        String cp = System.getProperty("java.class.path");
        return new ProcessBuilder("java", "-cp", cp, "Espera", String.valueOf(segundos))
                .inheritIO() // Muestra la salida del hijo por consola
                .start();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // --- 1. EJECUCIÓN SECUENCIAL ---
        System.out.println("=== Ejecución Secuencial ===");
        long inicioSec = System.currentTimeMillis();

        Process p1 = lanzarEspera(2);
        p1.waitFor();

        Process p2 = lanzarEspera(2);
        p2.waitFor();

        long tiempoSec = System.currentTimeMillis() - inicioSec;
        System.out.printf("Tiempo secuencial: %.2f s%n%n", tiempoSec / 1000.0);

        // --- 2. EJECUCIÓN EN PARALELO ---
        System.out.println("=== Ejecución en Paralelo ===");
        long inicioPar = System.currentTimeMillis();

        // Lanzar ambos procesos antes de esperar
        Process p3 = lanzarEspera(2);
        Process p4 = lanzarEspera(2);

        // Esperar a que finalicen los dos
        p3.waitFor();
        p4.waitFor();

        long tiempoPar = System.currentTimeMillis() - inicioPar;
        System.out.printf("Tiempo paralelo: %.2f s%n", tiempoPar / 1000.0);
    }
}
