/**
 * Una taquilla del teatro. Cada objeto Taquilla es el trabajo de un hilo
 * y solo usa sus propios datos: su número, su cupo y sus entradas vendidas.
 */
public class Taquilla implements Runnable {

    private final int numero;
    private final int cupo;
    private int vendidas;

    public Taquilla(int numero, int cupo) {
        this.numero = numero;
        this.cupo = cupo;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < cupo; i++) {
                Thread.sleep(5);   // cada venta tarda 5 ms
                vendidas++;
            }
        } catch (InterruptedException e) {
            // Se restaura la marca de interrupción y se deja de vender.
            Thread.currentThread().interrupt();
        }
    }

    public int getNumero() {
        return numero;
    }

    /** Solo debe llamarse después de hacer join() sobre el hilo de esta taquilla. */
    public int getVendidas() {
        return vendidas;
    }
}
