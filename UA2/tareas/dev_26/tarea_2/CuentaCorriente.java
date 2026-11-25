
import java.util.Random;

/**
 * FR1: cuenta con saldo, constructor, getter y setter con sleep aleatorio (250 a 2000ms)
 * y metodo ingresar sincronizado que muestra saldo previo, final y quien ingresa.
 * FR4: si quitas `synchronized´ de ingresar, varios hilos pueden leer el mismo saldo
 * a la ve y pisarse, dando un saldo final menor al esperado o distinto cada vez
 */

public class CuentaCorriente {

    private double saldo;
    private Random random = new Random();

    public CuentaCorriente(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("Saldo inicial negativo");
        }
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        if (saldo < 0) {
            throw new IllegalArgumentException("Saldo negativo");
        }
        dormir();
        this.saldo = saldo;
    }

    public synchronized void ingresar(double cantidad, String nombreHilo) {
        try {
            if (cantidad <= 0) {
                System.out.println("cantidad no valida en " + nombreHilo);
                return;
            }
            double previo = getSaldo();
            double finalSaldo = previo + cantidad;
            setSaldo(finalSaldo);
            System.out.println(nombreHilo + "-> previo:" + previo + "final: " + finalSaldo);
        } catch (IllegalArgumentException e){
            System.out.println("error en " + nombreHilo + ": " + e.getMessage());
        }
    }
    private void dormir() {
        int ms = 250 + random.nextInt(1751);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


   }
}