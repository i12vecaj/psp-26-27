/**
 * FUNCIONAMIENTO:
 * Igual que ua2tarea1fr2, pero la tarea de cada hilo se define en una clase
 * que implementa Runnable. Despues se crea un Thread pasandole ese Runnable.
 * El acceso al contador se sincroniza con un bloque synchronized sobre un
 * objeto cerrojo compartido por todos los hilos.
 */
public class ua2tarea1fr2runnable {

    private static final int NUM_HILOS = 5;
    private static final int INCREMENTOS = 1000;
    private static final int REPETICIONES = 10;

    private static int contador = 0;

    // Cerrojo comun para todos los hilos
    private static final Object cerrojo = new Object();

    /** Tarea que ejecutara cada hilo. */
    static class TareaIncremento implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS; i++) {
                synchronized (cerrojo) { // seccion critica
                    contador++;
                }
            }
            System.out.println("  " + Thread.currentThread().getName() + " ha terminado");
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
                    hilos[i] = new Thread(new TareaIncremento(), "hilo-" + (i + 1));
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
