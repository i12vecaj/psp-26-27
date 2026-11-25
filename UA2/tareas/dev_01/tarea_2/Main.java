public class Main {

    public static void main(String[] args) {
        CuentaCorriente cuenta = new CuentaCorriente(500);
        System.out.println("Saldo inicial: " + cuenta.getSaldo());

        // Creamos los hilos compartiendo con la misma cuenta
        HiloIngreso h1 = new HiloIngreso(cuenta, "Ana", 100);
        HiloIngreso h2 = new HiloIngreso(cuenta, "Pedro", 200);
        HiloIngreso h3 = new HiloIngreso(cuenta, "Juan", 300);

        // Lanzamos  hilos
        h1.start();
        h2.start();
        h3.start();

        // Esperar a que los hilos terminen
        try {
            h1.join();
            h2.join();
            h3.join();
            System.out.println("Saldo final de la cuenta: " + cuenta.getSaldo());
        } catch (InterruptedException e) {
            System.out.println("Error al esperar a los hilos");
            Thread.currentThread().interrupt();
        }
    }
}