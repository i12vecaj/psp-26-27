/**
 * Una taquilla que vende las entradas de su propio cupo.
 * Cada instancia la usa un unico hilo: sus campos no se comparten con otros hilos (regla H4).
 */
public class Taquilla implements Runnable {

    private static final long MS_POR_VENTA = 5;

    private final int numero;
    private final int cupo;
    private int vendidas; // solo lo modifica el hilo de esta taquilla

    public Taquilla(int numero, int cupo) {
        this.numero = numero;
        this.cupo = cupo;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < cupo; i++) {
                Thread.sleep(MS_POR_VENTA); // simula el tiempo de una venta
                vendidas++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // regla H5: restaurar el estado de interrupcion
        }
    }

    public int getNumero() {
        return numero;
    }

    /** Solo debe llamarse despues de join() sobre el hilo de esta taquilla (regla H3). */
    public int getVendidas() {
        return vendidas;
    }
}
