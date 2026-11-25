
public class HiloIngreso extends Thread {

    private CuentaCorriente cuenta;
    private double cantidad;

    // Constructor del hilo
    public HiloIngreso(CuentaCorriente cuenta, String nombre, double cantidad) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    // Realiza el ingreso cuando se inicia el hilo
    @Override
    public void run() {
        cuenta.ingresar(cantidad, getName());
    }
}
