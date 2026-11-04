public class Taquilla extends Thread {

    private static final long MILIS_POR_VENTA = 5;

    private final int cupo;
    private int vendidas;

    public Taquilla(int numero, int cupo) {
        super("Taquilla-" + numero);
        this.cupo = cupo;
    }

    @Override
    public void run() {
        for (int i = 0; i < cupo; i++) {
            try {
                Thread.sleep(MILIS_POR_VENTA);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            vendidas++;
        }
    }

    public int getVendidas() {
        return vendidas;
    }
}
