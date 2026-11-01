import java.io.IOException;

public class Padre {

    static final int NUM_HIJOS = 3;

    // Lanza un proceso Hijo con su identificador y hereda su salida por pantalla
    static Process lanzarHijo(int id) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Hijo", String.valueOf(id));
        pb.inheritIO();
        return pb.start();
    }

    public static void main(String[] args) {
        Process[] hijos = new Process[NUM_HIJOS];

        System.out.println("PADRE: lanzo " + NUM_HIJOS + " hijos");
        for (int i = 0; i < NUM_HIJOS; i++) {
            try {
                hijos[i] = lanzarHijo(i + 1);
            } catch (IOException e) {
                System.err.println("Error al lanzar el hijo " + (i + 1) + ": " + e.getMessage());
            }
        }

        for (int i = 0; i < NUM_HIJOS; i++) {
            if (hijos[i] == null) {
                continue;
            }
            try {
                int codigo = hijos[i].waitFor();
                if (codigo != 0) {
                    System.err.println("El hijo " + (i + 1) + " termino con error, codigo " + codigo);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrumpido mientras esperaba al hijo " + (i + 1));
            }
        }

        System.out.println("PADRE: mi PID es " + ProcessHandle.current().pid());
    }
}
