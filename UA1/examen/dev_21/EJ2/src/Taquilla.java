/*
 * Taquilla - PSP UA1, ejercicio 2
 * Juan Raigón Mínguez
 *
 * Tarea que ejecuta un hilo: vender su cupo de entradas, una cada 5 ms.
 */

public class Taquilla implements Runnable {

    // datos propios de esta taquilla, no se comparten con nadie
    private final int numero;
    private final int cupo;
    private int vendidas;
    private long msAbierta;

    public Taquilla(int numero, int cupo) {
        this.numero = numero;
        this.cupo = cupo;
    }

    @Override
    public void run() {
        long apertura = System.nanoTime();

        // vende hasta llenar su cupo
        while (vendidas < cupo) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + " interrumpida con " + vendidas + " vendidas");
                break;
            }
            vendidas++;
        }

        // cuanto ha tardado ella sola
        msAbierta = (System.nanoTime() - apertura) / 1000000;
    }

    public int getNumero() {
        return numero;
    }

    public int getCupo() {
        return cupo;
    }

    public int getVendidas() {
        return vendidas;
    }

    public long getMsAbierta() {
        return msAbierta;
    }
}
