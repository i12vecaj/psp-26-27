public class Reto4 {

    public static void main(String[] args) throws InterruptedException {
        Thread[] hilos = new Thread[3];
        long inicio = System.currentTimeMillis();

        // Primero arrancan todos los hilos.
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(
                    new Tarea(i + 1),
                    "Hilo-" + (i + 1)
            );
            hilos[i].start();
        }

        // Después se espera a que terminen todos.
        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println(
                "Tiempo total: "
                        + (System.currentTimeMillis() - inicio) + " ms"
        );

        // Con join() en el segundo bucle: aproximadamente 3 segundos.
        // Con join() justo después de cada start(): unos 6 segundos.
        // Sin join(): main imprime el tiempo casi inmediatamente.
        // La JVM sigue activa hasta que terminen los hilos no daemon.
    }

    private static class Tarea implements Runnable {
        private final int segundos;

        public Tarea(int segundos) {
            this.segundos = segundos;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(segundos * 1000L);

                System.out.println(
                        Thread.currentThread().getName() + " ha terminado."
                );

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}