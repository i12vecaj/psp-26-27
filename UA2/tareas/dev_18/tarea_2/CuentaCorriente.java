import java.util.concurrent.ThreadLocalRandom;

/**
 * Cuenta corriente compartida entre varios hilos.
 *
 * Los métodos getSaldo() y setSaldo() simulan un acceso lento (p. ej. a una base de datos)
 * durmiendo al hilo entre 250 y 2000 ms. Eso hace muy probable que aparezcan condiciones
 * de carrera cuando varios hilos operan a la vez sin sincronización.
 */
public class CuentaCorriente {

    private double saldo;

    /**
     * @param saldoInicial saldo con el que se abre la cuenta (no puede ser negativo)
     * @throws IllegalArgumentException si el saldo inicial es negativo
     */
    public CuentaCorriente(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo: " + saldoInicial);
        }
        this.saldo = saldoInicial;
    }

    /** Duerme al hilo actual entre 250 y 2000 ms (ambos incluidos). */
    private void dormirAleatorio() {
        int ms = ThreadLocalRandom.current().nextInt(250, 2001);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // Buena práctica: restaurar el estado de interrupción para que el hilo pueda reaccionar
            Thread.currentThread().interrupt();
            System.err.println("[" + Thread.currentThread().getName() + "] interrumpido durante la espera");
        }
    }

    /** Devuelve el saldo tras una espera aleatoria. */
    public double getSaldo() {
        dormirAleatorio();
        return saldo;
    }

    /** Modifica el saldo tras una espera aleatoria. */
    public void setSaldo(double saldo) {
        dormirAleatorio();
        this.saldo = saldo;
    }

    /**
     * Ingreso SINCRONIZADO: el monitor (this) garantiza que solo un hilo a la vez ejecuta
     * el ciclo leer-calcular-escribir, por lo que no se pierden ingresos.
     *
     * @param cantidad importe a ingresar (debe ser > 0)
     * @param quien    nombre de quien realiza el ingreso
     */
    public synchronized void ingresar(double cantidad, String quien) {
        operarIngreso(cantidad, quien);
    }

    /**
     * Mismo código que {@link #ingresar(double, String)} pero SIN synchronized.
     * Equivale a quitar el modificador del método (apartado 4 de la tarea); se mantiene
     * como método aparte para poder comparar ambos comportamientos en una misma ejecución.
     */
    public void ingresarSinSincronizar(double cantidad, String quien) {
        operarIngreso(cantidad, quien);
    }

    /** Lógica común: lee saldo, suma y escribe, mostrando estado previo y final. */
    private void operarIngreso(double cantidad, String quien) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a ingresar debe ser positiva: " + cantidad);
        }
        double previo = getSaldo();                 // lectura (con sleep)
        System.out.printf("  %s -> saldo previo: %.2f | ingresa: %.2f%n", quien, previo, cantidad);
        setSaldo(previo + cantidad);                // escritura (con sleep)
        System.out.printf("  %s -> saldo final tras su ingreso: %.2f%n", quien, previo + cantidad);
    }
}