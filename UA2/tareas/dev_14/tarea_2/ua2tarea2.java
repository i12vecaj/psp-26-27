/**
 * @file      ua2tarea2.java
 * @brief     Varios hilos ingresan dinero en la misma cuenta corriente con un metodo synchronized
 * @date      25/11/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 * @note      Funcionamiento. CuentaCorriente guarda el saldo. Su getter y su setter duermen entre 250 y
 *            2000 ms al azar. El metodo ingresar es synchronized: lee el saldo, le suma la cantidad y lo
 *            guarda, diciendo el saldo previo, el final y quien ingresa. Cada HiloIngreso tiene un nombre
 *            y una cantidad, y en su run llama a ingresar. El main crea la cuenta, lanza los hilos, espera
 *            con join y muestra el saldo final.
 * @note      Diferencia con el FR4 (ua2tarea2fr4.java). Aqui el saldo final siempre es el inicial mas
 *            todos los ingresos, porque solo un hilo a la vez puede estar dentro de ingresar. Sin
 *            synchronized varios hilos leen el mismo saldo previo, cada uno guarda su suma encima de la
 *            del otro y se pierden ingresos. El sleep del getter y del setter hace que pase casi siempre.
 */
package UA2.tareas.dev_14.tarea_2;

// la cuenta que comparten los hilos
class CuentaCorriente {
    private int saldo;

    public CuentaCorriente(int saldoInicial) {
        saldo = saldoInicial;
    }

    // devuelve el saldo tras una espera al azar
    public int getSaldo() {
        dormir();
        return saldo;
    }

    // cambia el saldo tras una espera al azar
    public void setSaldo(int saldo) {
        dormir();
        this.saldo = saldo;
    }

    // ingresa una cantidad, solo un hilo a la vez
    public synchronized void ingresar(int cantidad, String quien) {
        if (cantidad <= 0) {
            System.err.println(quien + " intenta ingresar una cantidad no valida " + cantidad);
            return;
        }
        int previo = getSaldo();
        System.out.println(quien + " ingresa " + cantidad + ". Saldo previo " + previo);
        setSaldo(previo + cantidad);
        System.out.println(quien + " termina. Saldo final " + getSaldo());
    }

    // duerme entre 250 y 2000 ms
    private void dormir() {
        try {
            Thread.sleep((int) (Math.random() * 1751) + 250);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Espera interrumpida en la cuenta");
        }
    }
}

// hilo que hace un ingreso en la cuenta
class HiloIngreso extends Thread {
    private CuentaCorriente cuenta;
    private int cantidad;

    public HiloIngreso(String nombre, int cantidad, CuentaCorriente cuenta) {
        super(nombre);
        this.cantidad = cantidad;
        this.cuenta = cuenta;
    }

    @Override
    public void run() {
        cuenta.ingresar(cantidad, getName());
    }
}

public class ua2tarea2 {

    public static void main(String[] args) {
        int saldoInicial = 1000;
        String[] nombres = {"Fede", "Ana", "Luis", "Marta"};
        int[] cantidades = {100, 250, 50, 300};

        // creo la cuenta y enseño el saldo inicial
        CuentaCorriente cuenta = new CuentaCorriente(saldoInicial);
        System.out.println("Saldo inicial " + cuenta.getSaldo());

        // creo un hilo por persona y los lanzo
        HiloIngreso[] hilos = new HiloIngreso[nombres.length];
        int totalIngresos = 0;
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new HiloIngreso(nombres[i], cantidades[i], cuenta);
            totalIngresos = totalIngresos + cantidades[i];
            hilos[i].start();
        }

        // espero a que acaben todos
        for (int i = 0; i < hilos.length; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Se interrumpio la espera de " + hilos[i].getName());
                System.exit(1);
            }
        }

        // muestro el saldo final y el que tendria que salir
        System.out.println("Saldo final    " + cuenta.getSaldo());
        System.out.println("Saldo esperado " + (saldoInicial + totalIngresos));
    }
}
