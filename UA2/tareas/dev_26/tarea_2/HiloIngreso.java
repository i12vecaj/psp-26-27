/**
 * FR2: hilo qu extiende de Thread y llama al metodo de ingresar saldo en cuentacorriente
 */
public class HiloIngreso extends Thread {

    private CuentaCorriente cuenta;
    private double cantidad;

    public HiloIngreso(String nombre, CuentaCorriente cuenta, double cantidad) {
        super(nombre);
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta nula en " + nombre);
        }
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        try {
            cuenta.ingresar(cantidad, getName());
        } catch (Exception e) {
            System.out.println("Error ejecutando " + getName() + ": " + e.getMessage());
        }
    }
}