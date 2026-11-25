
public class Main {

    public static void main(String[] args) {

        CuentaCorriente cuenta = new CuentaCorriente(1000);

        System.out.println("Saldo inicial: " + cuenta.getSaldo());

        HiloIngreso h1 = new HiloIngreso("Ana", cuenta, 100);
        HiloIngreso h2 = new HiloIngreso("Luis", cuenta, 200);
        HiloIngreso h3 = new HiloIngreso("Marta", cuenta, 150);
        HiloIngreso h4 = new HiloIngreso("Pedro", cuenta, 300);
        HiloIngreso h5 = new HiloIngreso("Lucia", cuenta, 250);
 
        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();

        try {
            h1.join();
            h2.join();
            h3.join();
            h4.join();
            h5.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Error esperando a los hilos.");
            return;
        }

        System.out.println("SALDO FINAL: " + cuenta.getSaldo());
    }
}