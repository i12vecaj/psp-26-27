/**
 * Representa una taquilla de venta de entradas concurrente e independiente.
 * Encapsula su propio estado mutable de forma aislada, sin variables estáticas
 * ni memoria compartida, cumpliendo con la Regla 4 de la skill.
 */
public class Taquilla extends Thread {

    private final int id;
    private final int cupoAsignado;
    private int entradasVendidas;

    public Taquilla(int id, int cupoAsignado) {
        this.id = id;
        this.cupoAsignado = cupoAsignado;
        this.entradasVendidas = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < cupoAsignado; i++) {
            try {
                Thread.sleep(5); // Demora requerida por entrada vendida
                this.entradasVendidas++;
            } catch (InterruptedException e) {
                // Regla 7: Restaurar estado de interrupcion explicitamente
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public int getIdTaquilla() {
        return id;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }
}

