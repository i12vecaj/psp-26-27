package UA2.Tarea2;

import java.util.Random;

public class CuentaCorriente {

    private double saldo;
    private final Random random = new Random();

    public CuentaCorriente(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.saldo = saldoInicial;
    }

    private void dormirAleatorio() {
        int ms = 250 + random.nextInt(1751);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + " fue interrumpido durante el sleep");
            Thread.currentThread().interrupt();
        }
    }

    public double getSaldo() {
        dormirAleatorio();
        return saldo;
    }

    public void setSaldo(double saldo) {
        dormirAleatorio();
        this.saldo = saldo;
    }

    public synchronized void ingresar(double cantidad, String nombre) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a ingresar debe ser positiva");
        }
        double saldoPrevio = getSaldo();
        System.out.println(nombre + " va a ingresar " + cantidad + " | saldo previo: " + saldoPrevio);
        setSaldo(saldoPrevio + cantidad);
        System.out.println(nombre + " ha ingresado " + cantidad + " | saldo final: " + getSaldo());
    }
}
