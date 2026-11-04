import java.io.IOException;

public class RetoTres {
    public static void main(String[] args) {
        try {
            // Comando base para llamar a la clase compilada Espera con el argumento "2"
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Espera", "2");

            System.out.println("=== 1. EJECUCIÓN SECUENCIAL ===");
            long inicioSecuencial = System.currentTimeMillis();

            Process secuencial1 = pb.start();
            secuencial1.waitFor(); // El padre se bloquea y espera a que termine el 1º

            Process secuencial2 = pb.start();
            secuencial2.waitFor(); // El padre se bloquea y espera a que termine el 2º

            long finSecuencial = System.currentTimeMillis();
            System.out.println("Tiempo secuencial: " + (finSecuencial - inicioSecuencial) + " ms");


            System.out.println("\n=== 2. EJECUCIÓN PARALELA ===");
            long inicioParalelo = System.currentTimeMillis();

            Process paralelo1 = pb.start(); // Arranca el 1º
            Process paralelo2 = pb.start(); // Arranca el 2º sin esperar al 1º
            // (Ampliación) Process paralelo3 = pb.start(); 

            // Ahora el padre se bloquea a esperar, pero los procesos ya están corriendo a la vez
            paralelo1.waitFor();
            paralelo2.waitFor();
            // paralelo3.waitFor();

            long finParalelo = System.currentTimeMillis();
            System.out.println("Tiempo paralelo: " + (finParalelo - inicioParalelo) + " ms");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}