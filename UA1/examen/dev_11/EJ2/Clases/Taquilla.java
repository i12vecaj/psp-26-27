public class Taquilla implements Runnable {
    private static final int MILISEGUNDOS_POR_VENTA = 5;

    private final int cupo;
    private int vendidas;

    public Taquilla(int cupo) {
        this.cupo = cupo;
    }

    @Override
    public void run() {
        for (int i = 0; i < cupo; i++) {
            try {
                Thread.sleep(MILISEGUNDOS_POR_VENTA);
                vendidas++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Venta interrumpida en " + Thread.currentThread().getName());
                return;
            }
        }
    }

    public int getVendidas() {
        return vendidas;
    }
}
