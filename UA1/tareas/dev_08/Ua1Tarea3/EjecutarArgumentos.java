/*
 * FR3: programa que ejecuta al anterior (Argumentos)
 * Control de errores: try/catch al lanzar y esperar al proceso
 * Documentacion: comentarios en el codigo
 */
/**
 * Ejecuta Argumentos como otro proceso y muestra por pantalla
 * lo que pasa segun el valor que devuelve.
 */
public class EjecutarArgumentos {

	public static void main(String[] args) {

		// Si no pasamos argumento, lanzamos Argumentos sin nada
		ProcessBuilder pb;
		if (args.length < 1) {
			pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Argumentos");
		} else {
			pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Argumentos", args[0]);
		}

		try {
			int valor = pb.start().waitFor();

			if (valor == 0) {
				System.out.println("Valor 0: todo correcto");
			} else if (valor == 1) {
				System.out.println("Valor 1: no se ha pasado ningun argumento");
			} else if (valor == 2) {
				System.out.println("Valor 2: el argumento es una cadena");
			} else if (valor == 3) {
				System.out.println("Valor 3: el argumento es un entero menor que 0");
			}
		} catch (Exception e) {
			// Error al lanzar el proceso o al esperarlo
			System.out.println("Error: " + e.getMessage());
		}
	}
}
