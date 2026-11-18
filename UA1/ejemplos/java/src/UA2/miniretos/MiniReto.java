
package UA2.miniretos;

public class MiniReto {

    public static void main(String[] args) throws InterruptedException {

        Runnable tarea1 = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Hilo 1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        };

        Runnable tarea2 = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Hilo 2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        };

        Runnable tarea3 = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Hilo 3");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        };

        Thread hilo1 = new Thread(tarea1);
        Thread hilo2 = new Thread(tarea2);
        Thread hilo3 = new Thread(tarea3);

        hilo1.start();
        hilo2.start();
        hilo3.start();

        hilo1.join();
        hilo2.join();
        hilo3.join();

        System.out.println("Todos los hilos han terminado.");
    }
}
