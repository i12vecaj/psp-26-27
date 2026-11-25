/**
 * synchronized evita que dos hilos se pisen, pero NO coordina.
 * Aquí el buffer tiene un solo hueco: si está lleno, el dato se pierde.
 */

//en esta demo04 en el Buffer permitimos solo un hueco y usamos
// el synchronized para evitar que dos hilos se pisen
// pero no los coordinamos luego usamos
// se crea un hilo nuevo llamado productor lo metemos en un bucle for
// para recorer todos los hilos y no se pisen
// tambien tenemos paradas en las que los hilos se duermen
// tenemos una exception con catch con InterruptedException e que para el hilo actual
// luego  tenemos otro hilo llamado consumidor
// con su try catch igual que productor y luego arrancamos productor y consumidor con start
// lo unimos con join para que no se pisen y acabe uno y empieze el otro

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
