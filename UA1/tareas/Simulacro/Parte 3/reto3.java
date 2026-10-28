import java.io.*;
import java.util.concurrent.TimeUnit;

public class Reto3 {
    public static void main(String[] args) throws IOException, InterruptedException {
        String cp = System.getProperty("java.class.path");

        // --- Secuencial ---
        long startSeq = System.currentTimeMillis();
        Process p1 = new ProcessBuilder("java", "-cp", cp, "Espera", "2").start();
        p1.waitFor();
        Process p2 = new ProcessBuilder("java", "-cp", cp, "Espera", "2").start();
        p2.waitFor();
        long endSeq = System.currentTimeMillis();
        System.out.println("Tiempo secuencial: " + (endSeq - startSeq) + "ms");

        // --- Paralelo ---
        long startPar = System.currentTimeMillis();
        Process p3 = new ProcessBuilder("java", "-cp", cp, "Espera", "2").start();
        Process p4 = new ProcessBuilder("java", "-cp", cp, "Espera", "2").start();
        p3.waitFor();
        p4.waitFor();
        long endPar = System.currentTimeMillis();
        System.out.println("Tiempo paralelo: " + (endPar - startPar) + "ms");
    }
}
