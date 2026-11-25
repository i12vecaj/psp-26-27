package UA2.Tarea2;

public class Main {

    public static void main(String[] args) {
        CuentaCorriente cuenta = new CuentaCorriente(1000);
        System.out.println("Saldo inicial: " + cuenta.getSaldo());

        HiloIngreso[] hilos = {
                new HiloIngreso("Ana", cuenta, 100),
                new HiloIngreso("Luis", cuenta, 200),
                new HiloIngreso("Marta", cuenta, 300),
                new HiloIngreso("Pedro", cuenta, 400)
        };

        for (HiloIngreso h : hilos) {
            h.start();
        }

        for (HiloIngreso h : hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
                System.err.println("Main interrumpido esperando a " + h.getName());
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Saldo final: " + cuenta.getSaldo());
    }
}
