/*
 * UA2 - Tarea 1 - FR2 con la interfaz Runnable (dev_15)
 *
 * Hace lo mismo que ua2tarea1fr2, pero la tarea implementa Runnable. Se crea
 * un solo objeto Tarea y se le pasa a los 5 hilos, por eso todos comparten su
 * contador y su cerrojo. El metodo incrementar es synchronized.
 *
 * Resultado: sale siempre 5000, igual que con Thread.
 * Diferencia con Thread: la clase no hereda de Thread, solo dice lo que hay
 * que hacer, y el hilo se crea aparte con new Thread(tarea). Asi la clase
 * podria heredar de otra si hiciera falta.
 */
public class ua2tarea1fr2runnable {

    static final int NUM_HILOS = 5;
    static final int INCREMENTOS = 1000;

    static class Tarea implements Runnable {
        // contador compartido porque todos los hilos usan este mismo objeto
        private int contador = 0;

        // seccion critica: solo entra un hilo cada vez
        private synchronized void incrementar() {
            contador++;
        }

        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS; i++) {
                incrementar();
            }
        }

        public int getContador() {
            return contador;
        }
    }

    public static void main(String[] args) {
        Tarea tarea = new Tarea();
        Thread[] hilos = new Thread[NUM_HILOS];

        // primero se lanzan todos
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new Thread(tarea);
            hilos[i].start();
        }

        // despues se espera a todos
        try {
            for (int i = 0; i < NUM_HILOS; i++) {
                hilos[i].join();
            }
        } catch (InterruptedException e) {
            System.err.println("Se ha interrumpido la espera: " + e.getMessage());
            Thread.currentThread().interrupt();
            return;
        }

        System.out.println("Resultado esperado: " + (NUM_HILOS * INCREMENTOS));
        System.out.println("Resultado obtenido: " + tarea.getContador());
    }
}
