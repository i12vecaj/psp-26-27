/*
 * UA2 - Tarea 1 - FR2 con la clase Thread (dev_15)
 *
 * Es el programa del FR1, pero ahora el contador se incrementa dentro de un
 * metodo synchronized. Asi solo un hilo puede estar sumando en cada momento.
 *
 * Diferencia con el FR1: ya no se pierde ninguna suma y sale siempre 5000.
 * El metodo es static porque cada hilo es un objeto distinto y tienen que
 * usar todos el mismo cerrojo, que en este caso es el de la clase.
 */
public class ua2tarea1fr2 {

    static final int NUM_HILOS = 5;
    static final int INCREMENTOS = 1000;

    // variable compartida por todos los hilos
    static int contador = 0;

    // seccion critica: solo entra un hilo cada vez
    static synchronized void incrementar() {
        contador++;
    }

    static class HiloContador extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS; i++) {
                incrementar();
            }
        }
    }

    public static void main(String[] args) {
        HiloContador[] hilos = new HiloContador[NUM_HILOS];

        // primero se lanzan todos
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new HiloContador();
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
        System.out.println("Resultado obtenido: " + contador);
    }
}
