public class Taquilla extends Thread {

    private final int cupo;
    private int vendidas;

    public Taquilla(int cupo) {
        this.cupo = cupo;
        this.vendidas = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < cupo; i++) {
            try {
                Thread.sleep(5);
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
