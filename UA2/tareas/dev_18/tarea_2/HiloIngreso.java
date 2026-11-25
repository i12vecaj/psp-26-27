/**
 * Hilo que realiza un ingreso en una cuenta compartida desde su método run().
 */
public class HiloIngreso extends Thread {

    private final CuentaCorriente cuenta;
    private final double cantidad;
    private final boolean sincronizado;

    /**
     * @param nombre       nombre del hilo / persona que ingresa
     * @param cuenta       cuenta compartida
     * @param cantidad     dinero a ingresar
     * @param sincronizado true usa el método synchronized; false la versión sin sincronizar
     */
    public HiloIngreso(String nombre, CuentaCorriente cuenta, double cantidad, boolean sincronizado) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
        this.sincronizado = sincronizado;
    }

    @Override
    public void run() {
        try {
            if (sincronizado) {
                cuenta.ingresar(cantidad, getName());
            } else {
                cuenta.ingresarSinSincronizar(cantidad, getName());
            }
        } catch (IllegalArgumentException e) {
            System.err.println("[" + getName() + "] ingreso rechazado: " + e.getMessage());
        }
    }
}