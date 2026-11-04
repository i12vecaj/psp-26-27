// Examen UA1 EJ2 Taquilla - Federico Lora

public class Taquilla extends Thread {

    // cada taquilla tiene sus propios datos
    private int cupo;
    private int vendidas = 0;

    public Taquilla(int cupo) {
        this.cupo = cupo;
    }

    // vende sus entradas una a una
    @Override
    public void run() {
        for (int i = 0; i < cupo; i++) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.err.println("Se corto la taquilla");
                return;
            }
            vendidas++;
        }
    }

    public int getVendidas() {
        return vendidas;
    }
}
