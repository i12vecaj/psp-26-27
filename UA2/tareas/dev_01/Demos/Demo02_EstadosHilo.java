/** Tres estados de un hilo: NEW, TIMED_WAITING y TERMINATED. */
public class Demo02_EstadosHilo {
    public static void main(String[] args) throws InterruptedException {
        Thread h = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("Antes de start : " + h.getState()); // NEW
        h.start();
        Thread.sleep(100);
        System.out.println("Durmiendo      : " + h.getState()); // TIMED_WAITING
        h.join();
        System.out.println("Al terminar    : " + h.getState()); // TERMINATED
    }
}
