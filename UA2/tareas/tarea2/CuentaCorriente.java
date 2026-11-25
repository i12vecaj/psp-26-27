import java.util.Random;

public class CuentaCorriente {

    private double saldo;
    private Random random = new Random();

    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public synchronized double getSaldo() {
        dormirAleatorio();
        return saldo;
    }

    public synchronized void setSaldo(double saldo) {
        dormirAleatorio();
        this.saldo = saldo;
    }

    private void dormirAleatorio() {
        try {
            int tiempo = random.nextInt(1751) + 250; // entre 250 y 2000 ms
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            System.out.println("Error en sleep: " + e.getMessage());
        }
    }

    public synchronized void ingresar(double cantidad, String nombreHilo) {

        double saldoAnterior = saldo;

        System.out.println(nombreHilo + " va a ingresar " + cantidad + "€");
        System.out.println("Saldo anterior: " + saldoAnterior + "€");

        saldo += cantidad;

        System.out.println("Saldo nuevo: " + saldo + "€");
        System.out.println("--------------------------------");
    }
}