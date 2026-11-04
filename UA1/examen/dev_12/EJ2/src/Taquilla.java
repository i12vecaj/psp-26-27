/**
 * Hilo que representa una taquilla del teatro.
 * Trabaja solo con sus propios datos: el programa principal lee
 * el resultado con getVendidas() únicamente después de join().
 */
public class Taquilla extends Thread {

    private static final int TIEMPO_VENTA_MS = 5;

    private final int numero;
    private final int cupo;
    private int vendidas = 0;

    public Taquilla(int numero, int cupo) {
        super("Taquilla-" + numero);
        this.numero = numero;
        this.cupo = cupo;
    }

    @Override
    public void run() {
        for (int i = 0; i < cupo; i++) {
            try {
                Thread.sleep(TIEMPO_VENTA_MS); // simula lo que tarda una venta
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // se detiene la venta, vendidas refleja lo vendido hasta ahora
            }
            vendidas++;
        }
    }

    public int getNumero() {
        return numero;
    }

    public int getVendidas() {
        return vendidas;
    }
}
