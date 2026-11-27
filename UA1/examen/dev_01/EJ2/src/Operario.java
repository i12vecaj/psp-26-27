
//Aqui el thread es para que cada operario sea un hilo
public class Operario extends Thread {

    private final int cupo;
    private int pedidosPreparados;

    public Operario(int cupo) {
        this.cupo = cupo;
        this.pedidosPreparados = 0;
    }

    public int getPedidosPreparados() {
        return pedidosPreparados;
    }

    @Override
    public void run() {
        try {
            // Cada operario prepara sus propios pedidos
            for (int i = 0; i < cupo; i++) {

                // Cada pedido tarda 5 milisegundos
                Thread.sleep(5);
                pedidosPreparados++;
            }
        } catch (InterruptedException e) {
            // Controlamos si se interrumpe el hilo
            Thread.currentThread().interrupt();
        }
    }
}
