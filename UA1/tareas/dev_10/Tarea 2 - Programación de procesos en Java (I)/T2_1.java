import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Lee caracteres desde la entrada estándar hasta encontrar el carácter
 * de terminación '*'. Después muestra por pantalla todo lo leído.
 *
 * El carácter '*' NO se incluye en el texto mostrado.
 */
public class LectorAsterisco {

    /** Carácter que indica el final de la lectura. */
    private static final char TERMINADOR = '*';

    public static void main(String[] args) {
        // StringBuilder acumula el texto de forma eficiente
        StringBuilder leido = new StringBuilder();
        boolean terminadorRecibido = false;

        // try-with-resources: el lector se cierra solo al acabar
        try (Reader entrada = new InputStreamReader(System.in)) {

            int c; // read() devuelve int para poder representar -1 (fin de datos)

            // FR1: leer carácter a carácter hasta '*' o fin de la entrada
            while ((c = entrada.read()) != -1) {
                if (c == TERMINADOR) {
                    terminadorRecibido = true;
                    break;
                }
                leido.append((char) c);
            }

        } catch (IOException e) {
            // Error de lectura: se informa por el flujo de errores y se sale con código 1
            System.err.println("Error al leer la entrada estándar: " + e.getMessage());
            System.exit(1);
        }

        // Control de error: la entrada se cerró sin recibir el '*'
        if (!terminadorRecibido) {
            System.err.println("Aviso: la entrada terminó sin recibir el carácter '" + TERMINADOR + "'.");
            System.exit(2);
        }

        // FR2: mostrar toda la información leída
        System.out.println("Información leída:");
        System.out.println(leido);
    }
}