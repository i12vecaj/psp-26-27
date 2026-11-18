public class ua2tarea1fr2runnable {

    static int contador = 0;

    static synchronized void incrementar() {
        contador++;
    }

    static class Tarea implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                incrementar();
            }
        }
    }

    public static void main(String[] args) {

        try {

            Thread[] hilos = new Thread[5];

            for (int i = 0; i < 5; i++) {
                hilos[i] = new Thread(new Tarea());
                hilos[i].start();
            }

            for (int i = 0; i < 5; i++) {
                hilos[i].join();
            }

            System.out.println("Valor final del contador sincronizado con Runnable: " + contador);

        } catch (InterruptedException e) {
            System.out.println("Error al ejecutar los hilos.");
        }
    }
}