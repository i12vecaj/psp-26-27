public class Main {

    public static void main(String[] args) {

        try {

            CuentaCorriente cuenta = new CuentaCorriente(1000);

            System.out.println("Saldo inicial: " + cuenta.getSaldo() + "€\n");

            HiloIngreso h1 = new HiloIngreso("Juan", cuenta, 100);
            HiloIngreso h2 = new HiloIngreso("Ana", cuenta, 200);
            HiloIngreso h3 = new HiloIngreso("Pedro", cuenta, 300);
            HiloIngreso h4 = new HiloIngreso("María", cuenta, 400);

            h1.start();
            h2.start();
            h3.start();
            h4.start();

            h1.join();
            h2.join();
            h3.join();
            h4.join();

            System.out.println("\nSaldo final: " + cuenta.getSaldo() + "€");

        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}