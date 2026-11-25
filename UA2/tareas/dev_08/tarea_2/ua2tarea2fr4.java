package UA2.tareas.dev_08.tarea_2;

/*
 * FR4: igual que el FR3 pero con ingresar() SIN synchronized.
 * El saldo final no es 2000 (por ejemplo 1400). Ingresar son tres pasos: leer
 * el saldo, sumar y escribirlo. Como los hilos van a la vez, varios leen el
 * mismo saldo previo y el ultimo en escribir pisa a los demas, perdiendose
 * ingresos. Con synchronized (FR3) un hilo no empieza hasta que el anterior
 * termina, y no se pierde nada.
 */
public class ua2tarea2fr4 {

	static class CuentaSinSincronizar {

		private int saldo;

		CuentaSinSincronizar(int saldo) {
			this.saldo = saldo;
		}

		private void dormir() {
			try {
				Thread.sleep((int) (Math.random() * 1751) + 250);
			} catch (InterruptedException e) {
				System.err.println("Hilo interrumpido");
			}
		}

		int getSaldo() {
			dormir();
			return saldo;
		}

		void setSaldo(int saldo) {
			dormir();
			this.saldo = saldo;
		}

		void ingresar(String nombre, int cantidad) {
			int previo = getSaldo();
			setSaldo(previo + cantidad);
			System.out.println(nombre + " ingresa " + cantidad + " | previo: " + previo + " | final: " + saldo);
		}
	}

	static class HiloIngresoSinSincronizar extends Thread {

		private final CuentaSinSincronizar cuenta;
		private final int cantidad;

		HiloIngresoSinSincronizar(String nombre, CuentaSinSincronizar cuenta, int cantidad) {
			super(nombre);
			this.cuenta = cuenta;
			this.cantidad = cantidad;
		}

		@Override
		public void run() {
			cuenta.ingresar(getName(), cantidad);
		}
	}

	public static void main(String[] args) {
		CuentaSinSincronizar cuenta = new CuentaSinSincronizar(1000);
		System.out.println("Saldo inicial: " + cuenta.getSaldo());

		HiloIngresoSinSincronizar[] hilos = {
			new HiloIngresoSinSincronizar("Ana", cuenta, 100),
			new HiloIngresoSinSincronizar("Luis", cuenta, 200),
			new HiloIngresoSinSincronizar("Marta", cuenta, 300),
			new HiloIngresoSinSincronizar("Pedro", cuenta, 400)
		};

		for (HiloIngresoSinSincronizar hilo : hilos) {
			hilo.start();
		}

		try {
			for (HiloIngresoSinSincronizar hilo : hilos) {
				hilo.join();
			}
		} catch (InterruptedException e) {
			System.err.println("Hilo principal interrumpido");
		}

		System.out.println("Saldo final: " + cuenta.getSaldo());
	}
}
