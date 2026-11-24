/**
 
 * FUNCIONAMIENTO:
 * Es el mismo programa que FR1 (5 hilos, 1000 incrementos cada uno, se repite
 * varias veces) pero ahora el acceso a "contador" esta sincronizado.
 *
 * El metodo incrementar() es "static synchronized"
 */
public class ua2tarea1fr2 {

    private static final int NUM_HILOS = 5;
    private static final int INCREMENTOS = 1000;
    private static final int REPETICIONES = 10;

    private static int contador = 0;

    private static synchronized void incrementar() {

        contador++;
    }

    /** Hilo creado extendiendo la clase Thread. */
    static class HiloSincronizado extends Thread {

        HiloSincronizado(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS; i++) {
                incrementar();
            }
            System.out.println("  " + getName() + " ha terminado");
        }
    }

    public static void main(String[] args) {
        int esperado = NUM_HILOS * INCREMENTOS;
        int fallos = 0;

        for (int r = 1; r <= REPETICIONES; r++) {
            contador = 0;
            Thread[] hilos = new Thread[NUM_HILOS];
            System.out.println("Ejecucion " + r + ":");

            try {
                for (int i = 0; i < NUM_HILOS; i++) {
                    hilos[i] = new HiloSincronizado("hilo-" + (i + 1));
                    hilos[i].start();
                }
                for (Thread hilo : hilos) {
                    hilo.join();
                }
            } catch (InterruptedException e) {
                System.err.println("El hilo principal fue interrumpido: " + e.getMessage());
                Thread.currentThread().interrupt();
                return;
            } catch (IllegalThreadStateException e) {
                System.err.println("Error al arrancar un hilo: " + e.getMessage());
                return;
            }

            boolean correcto = contador == esperado;
            if (!correcto) {
                fallos++;
            }
            System.out.println("  contador = " + contador + (correcto ? "  (correcto)" : "  (INCORRECTO)"));
        }

        System.out.println("\nResultado esperado: " + esperado);
        System.out.println("Ejecuciones incorrectas: " + fallos + " de " + REPETICIONES);
    }
}
