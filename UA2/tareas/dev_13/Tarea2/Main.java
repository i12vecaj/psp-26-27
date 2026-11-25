public class Main {
    static void main(String[]args)throws InterruptedException {
        CuentaCorriente cuenta = new CuentaCorriente(500.0);
        System.out.println("Saldo inicial: " + cuenta.getSaldo());

        HiloIngreso h1 = new HiloIngreso(cuenta, 300.0, true);
        h1.setName("hilo1");
        HiloIngreso h2 = new HiloIngreso(cuenta, 100.0, true);
        h1.setName("hilo2");
        HiloIngreso h3 = new HiloIngreso(cuenta, 30.0, true);
        h1.setName("hilo3");

        h1.start(); h2.start(); h3.start();

        h1.join(); h2.join(); h3.join();

        System.out.println("Saldo final con synchronized: " + cuenta.getSaldo());

        CuentaCorriente cuentasinsincronizar = new CuentaCorriente(200.0);

        HiloIngreso h4 = new HiloIngreso(cuenta, 500.0, false);
        h1.setName("hilo4");
        HiloIngreso h5 = new HiloIngreso(cuenta, 10.0, false);
        h1.setName("hilo5");
        HiloIngreso h6 = new HiloIngreso(cuenta, 80.0, false);
        h1.setName("hilo6");

        h4.start(); h5.start(); h6.start();

        h4.join(); h5.join(); h6.join();

        System.out.println("Saldo final sin synchronized: " + cuentasinsincronizar.getSaldo());

    }
}
