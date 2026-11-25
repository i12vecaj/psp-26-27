public class CuentaCorriente {

    private double saldo;
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public void setSaldo(double saldo) {
        dormir();
        this.saldo = saldo;
    }

    public double getSaldo() {
        dormir();
        return saldo;
    }

    // Duerme el hilo entre 250 y 2000 milisegundos
    private void dormir() {
        int tiempo = (int)(Math.random() * 1751) + 250;
        try {Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Metodo sincronizado para realizar los ingresos
    public synchronized void ingresar(double cantidad, String persona) {
        double saldoAnterior = getSaldo();

        setSaldo(saldoAnterior + cantidad);
        System.out.println("Persona: " + persona);
        System.out.println("Saldo anterior: " + saldoAnterior);
        System.out.println("Cantidad ingresada: " + cantidad);
        System.out.println("Saldo final: " + saldo);
        System.out.println("----------------------");
    }
}