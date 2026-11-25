import java.util.Random;

public class TareaCuentaCorriente {

    // FR1: Clase CuentaCorriente con atributos, constructor, getters/setters y 
    // metodo sincronizado con sleep() aleatorio entre 250 y 2000 ms.
    static class CuentaCorriente {
        private double saldo;
        private final Random random = new Random();

        public CuentaCorriente(double saldoInicial) {
            this.saldo = saldoInicial;
        }

        public synchronized double getSaldo() {
            try {
                // Sleep aleatorio entre 250 y 2000 ms (0.25s y 2s)
                Thread.sleep(random.nextInt(1751) + 250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return saldo;
        }

        public synchronized void setSaldo(double saldo) {
            try {
                Thread.sleep(random.nextInt(1751) + 250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            this.saldo = saldo;
        }

        public synchronized void ingresar(double cantidad) {
            try {
                double saldoPrevio = this.saldo;
                
                // Simulación de retardo aleatorio entre 250 y 2000 ms
                int tiempoSleep = random.nextInt(1751) + 250;
                Thread.sleep(tiempoSleep);
                
                this.saldo = saldoPrevio + cantidad;
                
                double saldoFinal = this.saldo;
                String nombreHilo = Thread.currentThread().getName();
                
                System.out.println("Hilo: " + nombreHilo + 
                                   " | Ingreso: " + cantidad + 
                                   " | Saldo previo: " + saldoPrevio + 
                                   " | Saldo final: " + saldoFinal);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("El hilo " + Thread.currentThread().getName() + " fue interrumpido.");
            }
        }
    }

    // FR2: Clase que extienda de Thread y use el metodo de CuentaCorriente en run()
    static class HiloIngreso extends Thread {
        private final CuentaCorriente cuenta;
        private final double cantidad;

        public HiloIngreso(String nombre, CuentaCorriente cuenta, double cantidad) {
            super(nombre);
            this.cuenta = cuenta;
            this.cantidad = cantidad;
        }

        @Override
        public void run() {
            // Llama al metodo de la cuenta para anadir el saldo de forma concurrente
            cuenta.ingresar(cantidad);
        }
    }

    // FR3: Metodo main para probar la concurrencia y esperar a que finalicen
    public static void main(String[] args) {
        System.out.println("=== INICIO DE LA PRUEBA CON SYNCHRONIZED (FR3) ===");
        try {
            // Crear objeto CuentaCorriente con un valor inicial
            CuentaCorriente cuenta = new CuentaCorriente(1000.0);
            System.out.println("Saldo inicial de la cuenta: " + cuenta.getSaldo());

            // Crear varios hilos que compartan dicho objeto, asignando nombre y cantidad
            HiloIngreso h1 = new HiloIngreso("Hilo-1", cuenta, 100.0);
            HiloIngreso h2 = new HiloIngreso("Hilo-2", cuenta, 250.0);
            HiloIngreso h3 = new HiloIngreso("Hilo-3", cuenta, 500.0);

            // Lanzar los hilos
            h1.start();
            h2.start();
            h3.start();

            // Esperar a que finalicen usando join()
            h1.join();
            h2.join();
            h3.join();

            System.out.println("Saldo final de la cuenta: " + cuenta.getSaldo());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El hilo principal fue interrumpido: " + e.getMessage());
        }
    }
}
