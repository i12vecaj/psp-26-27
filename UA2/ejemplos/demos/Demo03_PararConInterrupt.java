/** Parar un hilo con interrupt() (nunca con stop()). */
public class Demo03_PararConInterrupt {

    // en esta demo03 lo que pasa se crea un hilo nuevo entra en el bucle while el hilo actual si es interrumpido
    // siga trabajando hasta que es interrumpido se mete en el try tiene un tiempo que se duerme en 200 millis
    //entra en el catch salta la InterruptedException e y nunca se hace con stop
    // remalcamos la interrupcion y decimos que el hilo es parado
    //se vuelve a iniciar con start() se vuelve a dormir 700 millis se interrumpe porque los pedimos
    // y hacemos con el join que se espera a que terminen los hilos y no empiezen otros

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
