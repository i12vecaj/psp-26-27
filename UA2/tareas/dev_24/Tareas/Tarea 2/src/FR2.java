
public class FR2 extends Thread{

    private final FR4 cuenta;
    private final double cantidad;


    public FR2(FR4 cuenta, double cantidad) {
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    public double getCantidad() {
        return cantidad;
    }

    @Override
    public void run() {
        try {
            cuenta.ingresar(cantidad, getName());
        } catch (IllegalArgumentException e) {
            System.err.println("Error en " + getName() + ": " + e.getMessage());
        }
    }
}