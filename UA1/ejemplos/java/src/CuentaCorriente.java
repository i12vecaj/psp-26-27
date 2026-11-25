
public class CuentaCorriente {

    private double saldo;

    // Constructor de la cuenta
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Modifica el saldo
    public void setSaldo(double saldo) {
        dormir();
        this.saldo = saldo;
    }

    // Devuelve el saldo
    public double getSaldo() {
        dormir();
        return saldo;
    }

    // Espera un tiempo aleatorio entre 250 y 2000 ms
    private void dormir() {
        int tiempo = (int)(Math.random() * 1751) + 250;

        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Realiza el ingreso en la cuenta
    public void ingresar(double cantidad, String persona) {

        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que 0");
            return;
        }

        double saldoAnterior = saldo;
        saldo += cantidad;

        System.out.println("Persona: " + persona);
        System.out.println("Saldo anterior: " + saldoAnterior);
        System.out.println("Cantidad ingresada: " + cantidad);
        System.out.println("Saldo final: " + saldo);
        System.out.println("------------------------");
    }
}
