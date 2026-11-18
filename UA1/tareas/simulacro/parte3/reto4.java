public class reto4 {
    public static void main(String[] args) throws InterruptedException {
        Thread[] hilos = new Thread[3];
        long inicio = System.currentTimeMillis();

        for (int i = 0; i < hilos.length; i++) {
            int segundos = i + 1;
            hilos[i] = new Thread(new Tarea(segundos), "Hilo " + segundos);
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println("Tiempo total: " + (System.currentTimeMillis() - inicio) + " ms");
    }

    private static class Tarea implements Runnable {
        private final int segundos;

        Tarea(int segundos) {
            this.segundos = segundos;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(segundos * 1000L);
                System.out.println(Thread.currentThread().getName() + " terminado");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}