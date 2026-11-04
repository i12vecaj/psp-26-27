import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// Programa principal: lanza las taquillas a la vez, muestra el resultado y llama al Auditor
public class SistemaVentas {
	private static final int AFORO = 200;

	public static void main(String[] args) {
		int[] cupos = { 20, 30, 40, 50, 60 };
		Taquilla[] taquillas = new Taquilla[cupos.length];

		for (int i = 0; i < cupos.length; i++) {
			taquillas[i] = new Taquilla(cupos[i]);
		}

		// Se lanzan todas y despues se espera a todas, para que vendan a la vez
		long inicio = System.nanoTime();
		for (int i = 0; i < taquillas.length; i++) {
			taquillas[i].start();
		}
		try {
			for (int i = 0; i < taquillas.length; i++) {
				taquillas[i].join();
			}
		} catch (InterruptedException e) {
			System.err.println("Espera de las taquillas interrumpida: " + e.getMessage());
			Thread.currentThread().interrupt();
			System.exit(1);
		}
		long tiempoMs = (System.nanoTime() - inicio) / 1000000;

		// Las taquillas ya han terminado, ahora se pueden leer sus resultados
		int total = 0;
		for (int i = 0; i < taquillas.length; i++) {
			int vendidas = taquillas[i].getVendidas();
			total += vendidas;
			System.out.println("Taquilla " + (i + 1) + ": " + vendidas + " entradas");
		}
		System.out.println("Total vendido: " + total + " de " + AFORO);
		System.out.println("Tiempo de ventas: " + tiempoMs + " ms");

		lanzarAuditor(total);
	}

	// Lanza el Auditor, le envia los datos y muestra su respuesta y su codigo
	private static void lanzarAuditor(int total) {
		try {
			ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Auditor");
			pb.redirectError(ProcessBuilder.Redirect.INHERIT);
			Process auditor = pb.start();

			// Se envian el aforo y el total, y se cierra la entrada del hijo
			PrintWriter escritor = new PrintWriter(auditor.getOutputStream());
			escritor.println(AFORO);
			escritor.println(total);
			escritor.close();

			// Primero se lee su salida y despues se espera a que termine
			BufferedReader lector = new BufferedReader(new InputStreamReader(auditor.getInputStream()));
			String linea;
			while ((linea = lector.readLine()) != null) {
				System.out.println(linea);
			}

			int codigo = auditor.waitFor();
			System.out.println("Codigo del auditor: " + codigo);
		} catch (IOException e) {
			System.err.println("Error con el proceso Auditor: " + e.getMessage());
			System.exit(1);
		} catch (InterruptedException e) {
			System.err.println("Espera del Auditor interrumpida: " + e.getMessage());
			Thread.currentThread().interrupt();
			System.exit(1);
		}
	}
}

// Hilo de una taquilla: solo trabaja con su cupo y su contador
class Taquilla extends Thread {
	private final int cupo;
	private int vendidas = 0;

	public Taquilla(int cupo) {
		this.cupo = cupo;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < cupo; i++) {
				Thread.sleep(5); // cada venta tarda 5 ms
				vendidas++;
			}
		} catch (InterruptedException e) {
			System.err.println("Taquilla interrumpida: " + e.getMessage());
			Thread.currentThread().interrupt();
		}
	}

	public int getVendidas() {
		return vendidas;
	}
}
