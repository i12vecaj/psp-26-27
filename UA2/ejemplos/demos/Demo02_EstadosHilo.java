/** Tres estados de un hilo: NEW, TIMED_WAITING y TERMINATED. */
public class Demo02_EstadosHilo {

   // con el nuevo hilo se queda dormido 500 millis
    // y se encuentra algun error lo caza con catch InterruptedExcepetion e
    // se crea con el start se duerme 100 millis luego tiene un tiempo muerto
    // se arranca con start() y se une con el join()
    // y hace que se pare cada hilo  a que termine cada uno personalmente
    //y se termina los hilos

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
