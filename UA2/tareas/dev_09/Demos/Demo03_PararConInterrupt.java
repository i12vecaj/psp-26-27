/** Parar un hilo con interrupt() (nunca con stop()). */
public class Demo03_PararConInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread h = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Trabajando...");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // volver a marcar la interrupción
                }
            }
            System.out.println("Hilo parado.");
        });

        h.start();
        Thread.sleep(700);
        h.interrupt();   // pedimos al hilo que pare
        h.join();
    }
}
