/*
 * UA2 - Tarea 1 - FR1 (dev_15)
 *
 * Se lanzan 5 hilos y cada uno suma 1000 veces 1 a un contador compartido.
 * El contador NO esta sincronizado.
 *
 * Resultado esperado: 5 * 1000 = 5000.
 * En mis pruebas casi siempre sale 5000, porque cada hilo acaba muy rapido y
 * apenas coinciden, pero no esta garantizado: de 40 ejecuciones una dio 4031.
 *
 * Por que pasa: contador++ no es una sola operacion, son tres pasos (leer el
 * valor, sumarle 1 y guardarlo). Si dos hilos leen el mismo valor a la vez,
 * los dos guardan lo mismo y se pierde una de las sumas.
 */
public class ua2tarea1fr1 {

    static final int NUM_HILOS = 5;
    static final int INCREMENTOS = 1000;

    // variable compartida por todos los hilos
    static int contador = 0;

    // hilo que incrementa el contador sin ninguna proteccion
    static class HiloContador extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS; i++) {
                contador++;
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
