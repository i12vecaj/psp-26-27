public class ua2tarea1fr2 {

    static int contador = 0;

    static synchronized void incrementar() {
        contador++;
    }

    static class MiHilo extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                incrementar();
            }
        }
    }

    public static void main(String[] args) {

        try {
            MiHilo[] hilos = new MiHilo[5];

            for (int i = 0; i < 5; i++) {
                hilos[i] = new MiHilo();
                hilos[i].start();
            }

            for (int i = 0; i < 5; i++) {
                hilos[i].join();
            }

            System.out.println("Valor final del contador sincronizado: " + contador);

        } catch (InterruptedException e) {
            System.out.println("Error al ejecutar los hilos.");
        }
    }
}