package tarea_2;

import java.util.Random;

public class CuentaCorriente {

    private double saldo;
    private final Random random = new Random();

    // Constructor: valor inicial del saldo
    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Duerme el hilo entre 250 y 2000 ms (control de errores básico incluido)
    private void dormirAleatorio() {
        try {
            int ms = 250 + random.nextInt(1751); // 250..2000
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Hilo interrumpido: " + e.getMessage());
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

    // FR1: método synchronized que añade una cantidad al saldo
    public synchronized void ingresar(double cantidad, String quien) {
        double saldoPrevio = getSaldo();
        System.out.println(quien + " -> saldo previo: " + saldoPrevio
                + " | ingresa: " + cantidad);

        setSaldo(saldoPrevio + cantidad);

        System.out.println(quien + " -> saldo final:  " + getSaldo());
    }
}