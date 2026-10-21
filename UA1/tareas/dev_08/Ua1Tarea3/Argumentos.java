/*
 * FR1: admite argumentos desde main()
 * FR2: devuelve con System.exit() los siguientes valores:
 *   - Si el numero de argumentos es < 1 devuelve 1
 *   - Si el argumento es una cadena devuelve 2
 *   - Si el argumento es un numero entero menor que 0 devuelve 3
 *   - En cualquier otro caso devuelve 0
 * Control de errores: NumberFormatException con try/catch
 * Documentacion: comentarios en el codigo
 */
/**
 * Recibe un argumento por main() y devuelve con System.exit():
 * 1 si no hay argumentos, 2 si es una cadena, 3 si es un entero menor que 0
 * y 0 en cualquier otro caso.
 */
public class Argumentos {

	public static void main(String[] args) {

		// Sin argumentos: codigo 1
		if (args.length < 1) {
			System.exit(1);
		}

		try {
			int numero = Integer.parseInt(args[0]);

			if (numero < 0) {
				System.exit(3);
			}
			System.exit(0);
		} catch (NumberFormatException e) {
			// No se puede convertir a entero, asi que es una cadena
			System.exit(2);
		}
	}
}
