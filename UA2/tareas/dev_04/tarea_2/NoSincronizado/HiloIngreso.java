// Hilo que hace un ingreso en la cuenta compartida
public class HiloIngreso extends Thread {
    private final CuentaCorriente cuenta;
    private final int cantidad;

    public HiloIngreso(String nombre, CuentaCorriente cuenta, int cantidad) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        cuenta.ingresar(cantidad, getName());
    }
}
