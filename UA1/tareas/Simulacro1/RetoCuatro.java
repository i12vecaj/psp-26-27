public class RetoCuatro {

    // 1. Creamos la clase que implementa Runnable
    static class TareaDurmiente implements Runnable {
        private int segundos;
        private String nombre;

        public TareaDurmiente(String nombre, int segundos) {
            this.nombre = nombre;
            this.segundos = segundos;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(segundos * 1000L);
                System.out.println(nombre + " ha terminado.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Arrancando hilos...");
        long inicio = System.currentTimeMillis();

        Thread[] hilos = new Thread[3];

        // PRIMER BUCLE: Solo para crear y arrancar (paralelismo)
        for (int i = 0; i < 3; i++) {
            int segundos = i + 1;
            hilos[i] = new Thread(new TareaDurmiente("Hilo-" + segundos, segundos));
            hilos[i].start();
        }

        // SEGUNDO BUCLE: Solo para esperar (join)
        for (int i = 0; i < 3; i++) {
            try {
                hilos[i].join(); // El main se bloquea esperando a que este hilo muera
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long fin = System.currentTimeMillis();
        System.out.println("Todos han terminado. Tiempo total: " + (fin - inicio) + " ms");
    }
}