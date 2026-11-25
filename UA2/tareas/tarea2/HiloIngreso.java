package tarea_2;

// FR2: clase que extiende de Thread y usa el método ingresar()
public class HiloIngreso extends Thread {

    private final CuentaCorriente cuenta;
    private final double cantidad;

    public HiloIngreso(String nombre, CuentaCorriente cuenta, double cantidad) {
        super(nombre); // asigna el nombre al hilo
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        cuenta.ingresar(cantidad, getName());
    }
}