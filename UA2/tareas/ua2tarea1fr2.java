public class ua2tarea1fr2 {

    static final int NUM_HILOS = 5;
    static final int INCREMENTOS = 1000;
    static final int REPETICIONES = 10;

    static int contador = 0;

    static synchronized void incrementar() {
        contador++;
    }

    static class HiloContador extends Thread {
        public HiloContador(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            for (int i = 0; i < INCREMENTOS; i++) {
                incrementar();
            }
        }
    }

    public static void main(String[] args) {
        int esperado = NUM_HILOS * INCREMENTOS;
        int fallos = 0;

        for (int rep = 1; rep <= REPETICIONES; rep++) {
            contador = 0;
            Thread[] hilos = new Thread[NUM_HILOS];

            try {
                for (int i = 0; i < NUM_HILOS; i++) {
                    hilos[i] = new HiloContador("Hilo-" + (i + 1));
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

            boolean ok = (contador == esperado);
            if (!ok) fallos++;
            System.out.printf("Ejecucion %2d -> esperado: %d, obtenido: %d %s%n",
                    rep, esperado, contador, ok ? "(OK)" : "(INCORRECTO)");
        }

        System.out.println("Ejecuciones con resultado incorrecto: " + fallos + " de " + REPETICIONES);
    }
}
