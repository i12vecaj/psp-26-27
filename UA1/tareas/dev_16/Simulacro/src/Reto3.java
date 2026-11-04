import java.io.IOException;

/**
 * Reto 3 · Secuencial frente a paralelo.
 * Lanza hijos "Espera 2" uno tras otro y luego a la vez, y mide el tiempo.
 * Ampliación: 3 hijos de 2 s en paralelo.
 */
public class Reto3 {

    private static ProcessBuilder espera(int segundos) {
        return new ProcessBuilder("java", "-cp",
                System.getProperty("java.class.path"), "Espera", String.valueOf(segundos));
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // Secuencial: start() + waitFor() del primero, y luego el segundo
        long inicio = System.currentTimeMillis();
        espera(2).start().waitFor();
        espera(2).start().waitFor();
        long fin = System.currentTimeMillis();
        System.out.println("Secuencial (2 hijos de 2 s): " + (fin - inicio) + " ms");

        // Paralelo: start() de los dos ANTES del primer waitFor()
        inicio = System.currentTimeMillis();
        Process p1 = espera(2).start();
        Process p2 = espera(2).start();
        p1.waitFor();
        p2.waitFor();
        fin = System.currentTimeMillis();
        System.out.println("Paralelo   (2 hijos de 2 s): " + (fin - inicio) + " ms");

        // Ampliación: 3 hijos en paralelo
        inicio = System.currentTimeMillis();
        Process[] hijos = new Process[3];
        for (int i = 0; i < hijos.length; i++) {
            hijos[i] = espera(2).start();
        }
        for (Process h : hijos) {
            h.waitFor();
        }
        fin = System.currentTimeMillis();
        System.out.println("Paralelo   (3 hijos de 2 s): " + (fin - inicio) + " ms");
    }
}
