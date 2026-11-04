import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Proceso independiente: comprueba si el total vendido coincide con el aforo
public class Auditor {
	public static void main(String[] args) {
		int aforo = 0;
		int total = 0;

		// Lee las dos lineas de la entrada estandar: primero el aforo y luego el total
		try {
			BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
			String lineaAforo = entrada.readLine();
			String lineaTotal = entrada.readLine();

			// Si falta alguna linea, los datos no son validos
			if (lineaAforo == null || lineaTotal == null) {
				System.out.println("Datos no validos");
				System.exit(3);
			}

			aforo = Integer.parseInt(lineaAforo);
			total = Integer.parseInt(lineaTotal);
		} catch (IOException | NumberFormatException e) {
			// Error de lectura o alguna linea no es un numero
			System.out.println("Datos no validos");
			System.exit(3);
		}

		// Un aforo o un total negativos tampoco son validos
		if (aforo < 0 || total < 0) {
			System.out.println("Datos no validos");
			System.exit(3);
		}

		if (total == aforo) {
			System.out.println("Correcto: el total coincide con el aforo");
			System.exit(0);
		} else if (total > aforo) {
			System.out.println("Error: el total es mayor que el aforo");
			System.exit(1);
		} else {
			System.out.println("Error: el total es menor que el aforo");
			System.exit(2);
		}
	}
}
