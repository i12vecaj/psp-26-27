package UA2.tareas.dev_08.tarea_2;

/*
 * FR3: 4 hilos comparten la misma cuenta (1000) e ingresan 100, 200, 300 y 400.
 * El main espera con join() y muestra el saldo final.
 * Como ingresar() es synchronized, los hilos entran de uno en uno y el saldo
 * final siempre es 2000.
 * Diferencia con el FR4: alli no hay synchronized y el saldo final sale mal
 * (ver ua2tarea2fr4).
 */
public class ua2tarea2 {

	public static void main(String[] args) {
		CuentaCorriente cuenta = new CuentaCorriente(1000);
		System.out.println("Saldo inicial: " + cuenta.getSaldo());

		HiloIngreso[] hilos = {
			new HiloIngreso("Ana", cuenta, 100),
			new HiloIngreso("Luis", cuenta, 200),
			new HiloIngreso("Marta", cuenta, 300),
			new HiloIngreso("Pedro", cuenta, 400)
		};

		for (HiloIngreso hilo : hilos) {
			hilo.start();
		}

		try {
			for (HiloIngreso hilo : hilos) {
				hilo.join();
			}
		} catch (InterruptedException e) {
			System.err.println("Hilo principal interrumpido");
		}

		System.out.println("Saldo final: " + cuenta.getSaldo());
	}
}
