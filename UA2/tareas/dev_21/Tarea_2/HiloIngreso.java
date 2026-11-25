/**
 * FR2: Clase que extiende de Thread y utiliza el metodo de CuentaCorriente
 * para anadir saldo desde el metodo run().
 */
public class HiloIngreso extends Thread {
    private final CuentaCorriente cuenta;
    private final double cantidad;

    public HiloIngreso(String nombre, CuentaCorriente cuenta, double cantidad) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        // Llama al metodo de la cuenta para anadir el saldo de forma concurrente
        cuenta.ingresar(cantidad);
    }
}
