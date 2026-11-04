import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Proceso independiente: lee el aforo y el total por la entrada estandar y da su veredicto.
 * Codigos de salida: 0 coincide, 1 total mayor, 2 total menor, 3 datos no validos.
 */
public class Auditor {
    public static void main(String[] args) {
        int aforo = 0;
        int total = 0;

        // Si falta una linea, readLine da null y parseInt lanza NumberFormatException
        try {
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            aforo = Integer.parseInt(lector.readLine());
            total = Integer.parseInt(lector.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("ERROR: los datos no son validos");
            System.exit(3);
        }

        // Comprueba que los numeros no son negativos
        if (aforo < 0 || total < 0) {
            System.out.println("ERROR: los datos no son validos");
            System.exit(3);
        }

        if (total == aforo) {
            System.out.println("OK: el total coincide con el aforo");
            System.exit(0);
        } else if (total > aforo) {
            System.out.println("ERROR: el total es mayor que el aforo");
            System.exit(1);
        } else {
            System.out.println("ERROR: el total es menor que el aforo");
            System.exit(2);
        }
    }
}
