package UA2.tareas.dev_08.tarea_2;

/*
 * FR2: hilo que, desde run(), ingresa su cantidad en la cuenta.
 */
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
		cuenta.ingresar(getName(), cantidad);
	}
}
