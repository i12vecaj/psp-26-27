public class Reto4 {
    static class Tarea implements Runnable {
        private final int segundos;
        Tarea(int segundos) { this.segundos = segundos; }

        public void run() {
            try {
                Thread.sleep(segundos * 1000L);
                System.out.println(Thread.currentThread().getName() + " terminó");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long t0 = System.currentTimeMillis();

        Thread[] hilos = {
            new Thread(new Tarea(1), "Hilo-1"),
            new Thread(new Tarea(2), "Hilo-2"),
            new Thread(new Tarea(3), "Hilo-3")
        };

        for (Thread h : hilos) h.start();
        for (Thread h : hilos) h.join();

        System.out.println("Total: " + (System.currentTimeMillis() - t0) + " ms");
    }
}