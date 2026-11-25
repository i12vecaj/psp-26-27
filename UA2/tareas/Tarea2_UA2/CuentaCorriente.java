
public class CuentaCorriente {

    private double saldo;

    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        dormir();
        return saldo;
    }

    public void setSaldo(double saldo) {
        dormir();
        this.saldo = saldo;
    }

    public void ingresar(double cantidad, String nombre) {

        double saldoAnterior = getSaldo();

        double saldoFinal = saldoAnterior + cantidad;

        setSaldo(saldoFinal);

        System.out.println(nombre + " realiza un ingreso de "
                + cantidad + " euros.");

        System.out.println("Saldo anterior: " + saldoAnterior);
        System.out.println("Saldo final: " + saldoFinal);
        System.out.println("--------------------------");
    }

 
    private void dormir() {
        try {
            int tiempo = (int) (Math.random() * 1751) + 250;
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Hilo interrumpido.");
        }
    }
}
