/**
 * @file      ua2tarea2fr4.java
 * @brief     El mismo programa de ua2tarea2 pero sin synchronized, para ver la diferencia
 * @date      25/11/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 * @note      Funcionamiento. Igual que ua2tarea2.java, pero ingresar ya no es synchronized.
 * @note      Diferencia con el FR3. El saldo final sale menor que el esperado y cambia en cada ejecucion.
 *            Todos los hilos entran a la vez en ingresar, leen el mismo saldo previo (sale repetido en
 *            pantalla) y cada uno guarda su suma encima de la del anterior. Solo queda el ultimo ingreso
 *            que se guarda. Es una condicion de carrera, y por eso en el FR3 hace falta synchronized.
 */
package UA2.tareas.dev_14.tarea_2;

// la misma cuenta pero sin proteger
class CuentaCorrienteSinSync {
    private int saldo;

    public CuentaCorrienteSinSync(int saldoInicial) {
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

    // ingresa una cantidad, aqui pueden entrar varios a la vez
    public void ingresar(int cantidad, String quien) {
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

// hilo que hace un ingreso en la cuenta sin proteger
class HiloIngresoSinSync extends Thread {
    private CuentaCorrienteSinSync cuenta;
    private int cantidad;

    public HiloIngresoSinSync(String nombre, int cantidad, CuentaCorrienteSinSync cuenta) {
        super(nombre);
        this.cantidad = cantidad;
        this.cuenta = cuenta;
    }

    @Override
    public void run() {
        cuenta.ingresar(cantidad, getName());
    }
}

public class ua2tarea2fr4 {

    public static void main(String[] args) {
        int saldoInicial = 1000;
        String[] nombres = {"Fede", "Ana", "Luis", "Marta"};
        int[] cantidades = {100, 250, 50, 300};

        // creo la cuenta y enseño el saldo inicial
        CuentaCorrienteSinSync cuenta = new CuentaCorrienteSinSync(saldoInicial);
        System.out.println("Saldo inicial " + cuenta.getSaldo());

        // creo un hilo por persona y los lanzo
        HiloIngresoSinSync[] hilos = new HiloIngresoSinSync[nombres.length];
        int totalIngresos = 0;
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new HiloIngresoSinSync(nombres[i], cantidades[i], cuenta);
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
