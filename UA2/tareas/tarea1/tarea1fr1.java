package UA2.Tarea1;


public class tarea1fr1 {

    static int contador = 0;

    static class MiHilo extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                contador++;
            }
        }
    }

    public static void main(String[] args) {
        MiHilo[] hilos = new MiHilo[5];

        try {
            for (int i = 0; i < 5; i++) {
                hilos[i] = new MiHilo();
                hilos[i].start();
            }

            for (int i = 0; i < 5; i++) {
                hilos[i].join();
            }

            System.out.println("Contador final: " + contador);
            System.out.println("Resultado esperado: 5000");

        } catch (InterruptedException e) {
            System.err.println("Error: el hilo principal ha sido interrumpido.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}