import java.util.concurrent.ThreadLocalRandom;

public class CuentaCorriente {

    private double saldo;

    public CuentaCorriente(double saldoInicial){
        if (saldoInicial < 0){
            throw new IllegalArgumentException("No puedes tener un saldo negativo");
        }
        this.saldo = saldoInicial;
    }

    private void dormir(){
        int tiempo = ThreadLocalRandom.current().nextInt(250, 2000);
        try {
            Thread.sleep(tiempo);
        }catch (InterruptedException e){
            System.out.println("Pausado: " + Thread.currentThread().getName());
        }
    }

    public double getSaldo(){
        dormir();
        return this.saldo;
    }

    public void setSaldo(double saldo){
        dormir();
        if (saldo < 0){
            throw new IllegalArgumentException("El saldo es negativo");
        }
        this.saldo = saldo;
    }

    public synchronized void Ingresar(double cantidad,String nombre){
        if (cantidad <= 0){
            System.out.println("Ingresa una cantidad mayor que 0");
            return;
        }

        double saldoprevio = this.saldo;
        dormir();
        double nuevosaldo = saldoprevio + cantidad;
        this.saldo = nuevosaldo;

        System.out.println("SaldoPrevio: " + saldo + "nuevosaldo: " + nuevosaldo + "ingreso: " + cantidad);
    }

    public void IngresarDineroSinSincronizar(double cantidad, String nombre){
        if (cantidad < 0){
            System.out.println("Ingresa una cantidad mayor que 0");
            return;
        }
        double saldoprevio = this.saldo;
        dormir();
        double nuevosaldo = saldoprevio + cantidad;
        this.saldo = nuevosaldo;

        System.out.println("SaldoPrevio: " + saldo + "nuevosaldo: " + nuevosaldo + "ingreso: " + cantidad);
    }
}
