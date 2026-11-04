import java.io.IOException;

public class Reto3 {
    public static void main(String[] args) throws IOException, InterruptedException {
        String cp = System.getProperty("java.class.path");

        // secuencial: lanzo uno, espero a que acabe y luego el otro
        long inicio = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            Process p = new ProcessBuilder("java", "-cp", cp, "Espera", "2").inheritIO().start();
            p.waitFor();
        }
        long fin = System.currentTimeMillis();
        System.out.println("Secuencial: " + (fin - inicio) + " ms");

        // paralelo: lanzo los dos y despues espero a los dos
        inicio = System.currentTimeMillis();
        Process p1 = new ProcessBuilder("java", "-cp", cp, "Espera", "2").inheritIO().start();
        Process p2 = new ProcessBuilder("java", "-cp", cp, "Espera", "2").inheritIO().start();
        p1.waitFor();
        p2.waitFor();
        fin = System.currentTimeMillis();
        System.out.println("Paralelo (2 hijos): " + (fin - inicio) + " ms");

        // ampliacion: 3 hijos en paralelo
        inicio = System.currentTimeMillis();
        Process[] hijos = new Process[3];
        for (int i = 0; i < 3; i++) {
            hijos[i] = new ProcessBuilder("java", "-cp", cp, "Espera", "2").inheritIO().start();
        }
        for (int i = 0; i < 3; i++) {
            hijos[i].waitFor();
        }
        fin = System.currentTimeMillis();
        System.out.println("Paralelo (3 hijos): " + (fin - inicio) + " ms");
    }
}
