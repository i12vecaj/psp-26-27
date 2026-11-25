
public class ua2tarea1fr2runnable { // siempre da 5000. Los hilos comparten un Contador

    static final int NUM_HILOS = 5;
    static final int INCREMENTOS = 1000;
    static final int REPETICIONES = 100; // se repite para comprobar que nunca falla

    // El propio objeto hace de cerrojo
    static class Contador {
        private int valor = 0;

        synchronized void incrementar() { valor++; }
        synchronized int getValor() { return valor; }
    }

    // Lo que hace cada hilo: sumar INCREMENTOS al contador compartido
    static class TareaIncrementar implements Runnable {
        private final Contador contador;

        TareaIncrementar(Contador contador) { this.contador = contador; }

        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS; i++) {
                contador.incrementar();
            }
        }
    }

    // Lanza los hilos con la misma tarea, espera a todos y devuelve el valor final
    static int ejecutarPrueba() throws InterruptedException {
        Contador contador = new Contador();
        Runnable tarea = new TareaIncrementar(contador);
        Thread[] hilos = new Thread[NUM_HILOS];
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new Thread(tarea, "hilo-" + (i + 1));
            hilos[i].start();
        }
        for (Thread hilo : hilos) {
            hilo.join();
        }
        return contador.getValor();
    }

    public static void main(String[] args) {
        int esperado = NUM_HILOS * INCREMENTOS;
        try {
            int fallos = 0;
            for (int r = 1; r <= REPETICIONES; r++) {
                int resultado = ejecutarPrueba();
                if (r == 1) {
                    System.out.println("[Runnable] Primera ejecucion -> esperado: " + esperado + " | obtenido: " + resultado);
                }
                if (resultado != esperado) fallos++;
            }
            System.out.println("[Runnable] Repetido " + REPETICIONES + " veces -> ejecuciones con resultado incorrecto: " + fallos);
            System.out.println(fallos == 0
                    ? "Con synchronized no se pierde ningun incremento."
                    : "Error inesperado: la sincronizacion no ha funcionado.");
        } catch (InterruptedException e) {
            System.err.println("Error: el hilo main fue interrumpido mientras esperaba a los hilos: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
