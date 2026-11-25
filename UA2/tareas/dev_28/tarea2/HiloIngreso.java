
  //FR2 - Hilo que hace un ingreso en la cuenta
 // Cada hilo tiene un nombre y una cantidad de dinero. Todos los hilos
 //comparten el mismo objeto CuentaCorriente.
public class HiloIngreso extends Thread {

    private CuentaCorriente cuenta;
    private int cantidad;

    public HiloIngreso(String nombre, CuentaCorriente cuenta, int cantidad) {
        super(nombre);
        this.cuenta = cuenta;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        cuenta.ingresarSaldo(cantidad, getName());
    }
}
