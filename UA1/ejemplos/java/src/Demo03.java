/** S1 - Parar un hilo de forma correcta: interrupt() + condición de parada (nunca stop()). */
public class Demo03 {
    public static void main(String[] args) throws InterruptedException {
        Thread trabajador = new Thread(() -> {
            int vueltas = 0;
            while (!Thread.currentThread().isInterrupted()) {
                vueltas++;
                try {
                    Thread.sleep(100); // sleep lanza InterruptedException si lo interrumpen
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // restaurar la marca y salir del bucle
                }
            }
            System.out.println("Trabajador: limpiando recursos y terminando tras " + vueltas + " vueltas");
        }, "trabajador");

        trabajador.start();
        Thread.sleep(550);
        trabajador.interrupt();
        trabajador.join();
        System.out.println("Estado final: " + trabajador.getState());
    }
}

