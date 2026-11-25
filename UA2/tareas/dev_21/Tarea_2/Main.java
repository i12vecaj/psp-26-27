/**
 * FR3: Clase principal que crea una cuenta corriente, lanza varios hilos
 * compartiendo dicha cuenta y espera a que finalicen para mostrar el saldo final.
 */
public class Main {
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
