/** S1 - Estados reales de Thread.State: NEW, RUNNABLE, TIMED_WAITING, WAITING, BLOCKED, TERMINATED. */
public class Demo02_EstadosHilo {
    public static void main(String[] args) throws InterruptedException {
        Object cerrojo = new Object();

        Thread dormilon = new Thread(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        Thread espera = new Thread(() -> {
            synchronized (cerrojo) {
                try { cerrojo.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });

        System.out.println("dormilon tras new      : " + dormilon.getState()); // NEW
        dormilon.start();
        Thread.sleep(100);
        System.out.println("dormilon en sleep(500) : " + dormilon.getState()); // TIMED_WAITING
        espera.start();
        Thread.sleep(100);
        System.out.println("espera en wait()       : " + espera.getState());   // WAITING

        // BLOCKED: main retiene el cerrojo y otro hilo intenta entrar
        Thread bloqueado = new Thread(() -> { synchronized (cerrojo) { } });
        synchronized (cerrojo) {
            bloqueado.start();
            Thread.sleep(100);
            System.out.println("bloqueado esperando monitor: " + bloqueado.getState()); // BLOCKED
            cerrojo.notifyAll(); // despierta a 'espera' (que luego necesita el monitor)
        }
        dormilon.join(); espera.join(); bloqueado.join();
        System.out.println("dormilon al terminar   : " + dormilon.getState()); // TERMINATED
        System.out.println("\nNota: el PDF simplifica a new/runnable/blocked/dead.");
    }
}