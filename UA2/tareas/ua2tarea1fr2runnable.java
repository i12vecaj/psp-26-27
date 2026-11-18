public class ua2tarea1fr2runnable {

    static final int NUM_HILOS = 5;
    static final int INCREMENTOS = 1000;
    static final int REPETICIONES = 10;

    static class Contador {
        private int valor = 0;

        public synchronized void incrementar() {
            valor++;
        }

        public synchronized int getValor() {
            return valor;
        }
    }

    static class TareaContador implements Runnable {
        private final Contador contador;

        public TareaContador(Contador contador) {
            if (contador == null) {
                throw new IllegalArgumentException("El contador no puede ser nulo");
            }
            this.contador = contador;
        }

        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS; i++) {
                contador.incrementar();
            }
        }
    }

    public static void main(String[] args) {
        int esperado = NUM_HILOS * INCREMENTOS;
        int fallos = 0;

        for (int rep = 1; rep <= REPETICIONES; rep++) {
            Contador contador = new Contador();
            Thread[] hilos = new Thread[NUM_HILOS];

            try {
                Runnable tarea = new TareaContador(contador);
                for (int i = 0; i < NUM_HILOS; i++) {
                    hilos[i] = new Thread(tarea, "Hilo-" + (i + 1));
                    hilos[i].start();
                }
                for (Thread h : hilos) {
                    h.join();
                }
            } catch (InterruptedException e) {
                System.err.println("Error: el hilo principal fue interrumpido: " + e.getMessage());
                Thread.currentThread().interrupt();
                return;
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
                return;
            }

            boolean ok = (contador.getValor() == esperado);
            if (!ok) fallos++;
            System.out.printf("Ejecucion %2d -> esperado: %d, obtenido: %d %s%n",
                    rep, esperado, contador.getValor(), ok ? "(OK)" : "(INCORRECTO)");
        }

        System.out.println("Ejecuciones con resultado incorrecto: " + fallos + " de " + REPETICIONES);
    }
}
