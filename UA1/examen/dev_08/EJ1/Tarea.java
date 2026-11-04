import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Tarea {
	// Escribe el mensaje por la salida de error y termina con el codigo.
	static void fallar(String mensaje, int codigo) {
		System.err.println(mensaje);
		System.exit(codigo);
	}
	public static void main(String[] args) throws Exception {
		if (args.length < 2) fallar("Tarea de java: <nombre> <segundos>", 1);
		String nombre = args[0];
		int segundos = 0;
		try {
			segundos = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			fallar("[" + nombre + "] segundos no numericos", 2);
		}
		if (segundos < 0) fallar("[" + nombre + "] segundos negativos", 3);
		// Lee UNA linea de la entrada estandar, el mensaje que envia el padre
		String mensaje = new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.println("[" + nombre + "] Mensaje recibido: " + mensaje);
		if (nombre.equals("falla")) fallar("[falla] error simulado", 4);

		System.out.println("[" + nombre + "] Trabajando " + segundos + " s");
		// La tarea duerme durante 1 segundo (sleep lo recibe en milisegundos, por eso * 1000)
		Thread.sleep(segundos * 1000L);
		System.out.println("[" + nombre + "] Terminado");
	}
}