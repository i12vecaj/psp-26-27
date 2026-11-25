package UA2.tareas.dev_08.tarea_2;

/*
 * FR1: cuenta con saldo. getSaldo y setSaldo duermen entre 250 y 2000 ms.
 * ingresar() es synchronized para que solo un hilo a la vez lea y escriba el saldo.
 */
public class CuentaCorriente {

	private int saldo;

	public CuentaCorriente(int saldo) {
		this.saldo = saldo;
	}

	private void dormir() {
		try {
			Thread.sleep((int) (Math.random() * 1751) + 250);
		} catch (InterruptedException e) {
			System.err.println("Hilo interrumpido");
		}
	}

	public int getSaldo() {
		dormir();
		return saldo;
	}

	public void setSaldo(int saldo) {
		dormir();
		this.saldo = saldo;
	}

	public synchronized void ingresar(String nombre, int cantidad) {
		int previo = getSaldo();
		setSaldo(previo + cantidad);
		System.out.println(nombre + " ingresa " + cantidad + " | previo: " + previo + " | final: " + saldo);
	}
}
