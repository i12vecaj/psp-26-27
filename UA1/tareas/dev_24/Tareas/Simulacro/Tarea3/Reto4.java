public class Reto4 {

    // Clase que implementa Runnable y duerme los segundos indicados
    static class TareaDormir implements Runnable {
        private final int segundos;

        public TareaDormir(int segundos) {
            this.segundos = segundos;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(segundos * 1000L);
                System.out.println(Thread.currentThread().getName() + " ha terminado tras " + segundos + " s");
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + " fue interrumpido");
            }
        }
    }

    public static void main(String[] args) {
        int[] tiempos = {1, 2, 3};
        Thread[] hilos = new Thread[tiempos.length];

        long inicio = System.currentTimeMillis();

        // 1. Crear y arrancar todos los hilos
        for (int i = 0; i < tiempos.length; i++) {
            hilos[i] = new Thread(new TareaDormir(tiempos[i]), "Hilo-" + tiempos[i] + "s");
            hilos[i].start();
        }

        // 2. Esperar a que terminen todos (en un segundo bucle independiente)
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("El hilo principal fue interrumpido");
            }
        }

        long tiempoTotal = System.currentTimeMillis() - inicio;
        System.out.printf("Tiempo total de ejecución: %.2f s%n", tiempoTotal / 1000.0);
    }
}
