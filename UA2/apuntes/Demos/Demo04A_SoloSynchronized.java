/**
 * synchronized evita que dos hilos se pisen, pero NO coordina.
 * Aquí el buffer tiene un solo hueco: si está lleno, el dato se pierde.
 */
public class Demo04A_SoloSynchronized {

    static class Buffer {
        private Integer dato = null; // un solo hueco

        public synchronized boolean poner(int d) {
            if (dato != null) return false; // lleno: no sabe esperar
            dato = d;
            return true;
        }

        public synchronized void sacar() {
            dato = null;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();
        int[] perdidos = {0};

        Thread productor = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    if (!buffer.poner(i)) perdidos[0]++;
                    Thread.sleep(10);   // produce rápido
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumidor = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(50);   // consume lento
                    buffer.sacar();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        productor.start();
        consumidor.start();
        productor.join();
        consumidor.join();
        System.out.println("Datos perdidos: " + perdidos[0] + " de 5");
    }
}
