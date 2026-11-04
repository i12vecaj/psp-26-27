import java.io.IOException;

public class RetoTres {

    // Método auxiliar para crear e iniciar el proceso hijo pasándole los segundos
    private static Process lanzarHijo(String segundos) throws IOException {
        return new ProcessBuilder(
                "java",
                "-cp",
                System.getProperty("java.class.path"),
                "Espera",
                segundos
        ).start();
    }

    public static void main(String[] args) {
        try {
            // ==========================================
            // 1. EJECUCIÓN EN SECUENCIA (Uno tras otro)
            // ==========================================
            System.out.println("--- Inicio Secuencial (2 hijos de 2s) ---");
            long inicioSec = System.currentTimeMillis();

            // Lanzar y esperar al primer hijo
            Process p1 = lanzarHijo("2");
            p1.waitFor();

            // Lanzar y esperar al segundo hijo
            Process p2 = lanzarHijo("2");
            p2.waitFor();

            long finSec = System.currentTimeMillis();
            long tiempoSec = (finSec - inicioSec) / 1000;
            System.out.println("Tiempo total secuencial: " + tiempoSec + " segundos\n");


            // ==========================================
            // 2. EJECUCIÓN EN PARALELO (Los dos a la vez)
            // ==========================================
            System.out.println("--- Inicio Paralelo (2 hijos de 2s) ---");
            long inicioPar = System.currentTimeMillis();

            // Lanzar ambos procesos ANTES de esperar a ninguno
            Process p3 = lanzarHijo("2");
            Process p4 = lanzarHijo("2");

            // Esperar a que terminen ambos
            p3.waitFor();
            p4.waitFor();

            long finPar = System.currentTimeMillis();
            long tiempoPar = (finPar - inicioPar) / 1000;
            System.out.println("Tiempo total paralelo: " + tiempoPar + " segundos\n");


            // ==========================================
            // 3. AMPLIACIÓN: 3 HIJOS DE 2s EN PARALELO
            // ==========================================
            System.out.println("--- Ampliación: Inicio Paralelo (3 hijos de 2s) ---");
            long inicioAmp = System.currentTimeMillis();

            Process p5 = lanzarHijo("2");
            Process p6 = lanzarHijo("2");
            Process p7 = lanzarHijo("2");

            p5.waitFor();
            p6.waitFor();
            p7.waitFor();

            long finAmp = System.currentTimeMillis();
            long tiempoAmp = (finAmp - inicioAmp) / 1000;
            System.out.println("Tiempo total paralelo (3 hijos): " + tiempoAmp + " segundos");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}