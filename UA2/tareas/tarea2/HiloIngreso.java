package UA2.Tarea2;

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
        try {
            cuenta.ingresar(cantidad, getName());
        } catch (IllegalArgumentException e) {
            System.err.println(getName() + ": ingreso rechazado -> " + e.getMessage());
        }
    }
}
