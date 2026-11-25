import java.util.Random;

/**
 * FR1: Clase CuentaCorriente con atributo saldo, constructor, getters/setters
 * y metodo sincronizado para ingresar dinero con retardo aleatorio.
 */
public class CuentaCorriente {
    private double saldo;
    private final Random random = new Random();

    public CuentaCorriente(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public synchronized double getSaldo() {
        try {
            // Sleep aleatorio entre 250 y 2000 ms (0.25s y 2s)
            Thread.sleep(random.nextInt(1751) + 250);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return saldo;
    }

    public synchronized void setSaldo(double saldo) {
        try {
            Thread.sleep(random.nextInt(1751) + 250);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.saldo = saldo;
    }

    /**
     * Metodo sincronizado que recibe una cantidad, la anade al saldo y muestra
     * por pantalla el estado previo, el estado final y quien realiza el ingreso.
     * (Para comprobar FR4, elimina 'synchronized' de este metodo y de getSaldo/setSaldo).
     */
    public synchronized void ingresar(double cantidad) {
        try {
            double saldoPrevio = this.saldo;
            
            // Simulación de retardo aleatorio entre 250 y 2000 ms
            int tiempoSleep = random.nextInt(1751) + 250;
            Thread.sleep(tiempoSleep);
            
            this.saldo = saldoPrevio + cantidad;
            
            double saldoFinal = this.saldo;
            String nombreHilo = Thread.currentThread().getName();
            
            System.out.println("Hilo: " + nombreHilo + 
                               " | Ingreso: " + cantidad + 
                               " | Saldo previo: " + saldoPrevio + 
                               " | Saldo final: " + saldoFinal);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El hilo " + Thread.currentThread().getName() + " fue interrumpido.");
        }
    }
}
