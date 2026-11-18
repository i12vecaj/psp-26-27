/**
 * Reto 4 · Hilos con Runnable y join().
 * 3 hilos duermen 1, 2 y 3 s; el main imprime el tiempo total al terminar los tres.
 * Se ejecutan tres versiones: join() en un segundo bucle, join() tras cada start()
 * y sin join() (ampliación).
 */
public class Reto4 {

    static class Durmiente implements Runnable {
        private final int segundos;

        Durmiente(int segundos) {
            this.segundos = segundos;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(segundos * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("  Termina " + Thread.currentThread().getName()
                    + " (" + segundos + " s)");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== A) start() de todos y join() en un segundo bucle ===");
        long inicio = System.currentTimeMillis();
        Thread[] hilos = new Thread[3];
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(new Durmiente(i + 1), "Hilo-" + (i + 1));
            hilos[i].start();
        }
        for (Thread h : hilos) {
            h.join();
        }
        System.out.println("main: total " + (System.currentTimeMillis() - inicio) + " ms");

        System.out.println("=== B) join() justo después de cada start() ===");
        inicio = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            Thread h = new Thread(new Durmiente(i + 1), "Hilo-" + (i + 1));
            h.start();
            h.join(); // el main espera aquí: se vuelve secuencial
        }
        System.out.println("main: total " + (System.currentTimeMillis() - inicio) + " ms");

        System.out.println("=== C) Ampliación: sin join() ===");
        inicio = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            new Thread(new Durmiente(i + 1), "Hilo-" + (i + 1)).start();
        }
        System.out.println("main: total " + (System.currentTimeMillis() - inicio) + " ms");
    }
}
