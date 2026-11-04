import java.io.*;

public class Reto3 {

    static Process lanzar(int segundos) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(
                "java", "-cp", System.getProperty("java.class.path"),
                "Espera", String.valueOf(segundos));
        pb.inheritIO();
        return pb.start();
    }

    public static void main(String[] args) throws Exception {

        System.out.println("== Secuencial ==");
        long inicio = System.nanoTime();

        Process h1 = lanzar(2);
        h1.waitFor();
        Process h2 = lanzar(2);
        h2.waitFor();

        long msSecuencial = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("Tiempo secuencial: " + msSecuencial + " ms\n");

        System.out.println("== Paralelo ==");
        inicio = System.nanoTime();

        Process p1 = lanzar(2);
        Process p2 = lanzar(2);
        p1.waitFor();
        p2.waitFor();

        long msParalelo = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("Tiempo paralelo: " + msParalelo + " ms");
    }
}