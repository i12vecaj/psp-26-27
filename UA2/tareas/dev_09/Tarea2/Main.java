
public class Main {

    public static void main(String[] args) {

        // Creamos la cuenta con 1000 euros
        CuentaCorriente cuenta = new CuentaCorriente(1000);

        System.out.println("Saldo inicial: " + cuenta.getSaldo());
        System.out.println("------------------------");

        // Creamos tres hilos que comparten la misma cuenta
        HiloIngreso hilo1 = new HiloIngreso(cuenta, "Alvaro", 500);
        HiloIngreso hilo2 = new HiloIngreso(cuenta, "Ruben", 300);
        HiloIngreso hilo3 = new HiloIngreso(cuenta, "Antonio", 200);

        // Iniciamos los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();

        // Esperamos a que terminen los tres hilos
        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Error al esperar los hilos");
            return;
        }

        // Mostramos el saldo final
        System.out.println("Saldo final: " + cuenta.getSaldo());
    }
}
