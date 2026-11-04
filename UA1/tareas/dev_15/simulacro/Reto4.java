public class Reto4 {

    // tarea que duerme los segundos que le pasan y dice su nombre al acabar
    static class Tarea implements Runnable {
        private int segundos;

        public Tarea(int segundos) {
            this.segundos = segundos;
        }

        public void run() {
            try {
                Thread.sleep(segundos * 1000L);
            } catch (InterruptedException e) {
                System.out.println("Me han interrumpido");
            }
            System.out.println(Thread.currentThread().getName() + " ha terminado (" + segundos + " s)");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // los 3 hilos a la vez y los join en otro bucle
        long inicio = System.currentTimeMillis();
        Thread[] hilos = new Thread[3];
        for (int i = 0; i < 3; i++) {
            hilos[i] = new Thread(new Tarea(i + 1), "Hilo-" + (i + 1));
            hilos[i].start();
        }
        for (int i = 0; i < 3; i++) {
            hilos[i].join();
        }
        System.out.println("Con join al final: " + (System.currentTimeMillis() - inicio) + " ms\n");

        // join justo despues de cada start
        inicio = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            Thread h = new Thread(new Tarea(i + 1), "Hilo-" + (i + 1));
            h.start();
            h.join();
        }
        System.out.println("Con join despues de cada start: " + (System.currentTimeMillis() - inicio) + " ms\n");

        // ampliacion: sin join
        inicio = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            new Thread(new Tarea(i + 1), "Hilo-" + (i + 1)).start();
        }
        System.out.println("Sin join, el main acaba en: " + (System.currentTimeMillis() - inicio) + " ms");
    }
}
