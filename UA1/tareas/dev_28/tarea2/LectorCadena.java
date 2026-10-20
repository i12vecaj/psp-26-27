import java.io.InputStreamReader;
import java.io.IOException;

/**

 */
public class LectorCadena {

    public static void main(String[] args) {
        // Usamos StringBuilder para ir guardando el texto que leemos
        StringBuilder texto = new StringBuilder();

        System.out.println("Escribe lo que quieras (pon un '*' para terminar): ");

        // Control de errores: try-with-resources para que se cierre el lector automáticamente
        try (InputStreamReader lector = new InputStreamReader(System.in)) {
            int c;

            // FR1: Leemos un carácter de la consola en cada vuelta del bucle
            while ((c = lector.read()) != -1) {
                char letra = (char) c;

                // Si encontramos el asterisco, cortamos el bucle
                if (letra == '*') {
                    break;
                }

                // Vamos añadiendo la letra a nuestro texto
                texto.append(letra);
            }

            // FR2: Mostramos por pantalla toda la información acumulada
            System.out.println("\n--- TEXTO INTRODUCIDO ---");
            System.out.println(texto.toString());

        } catch (IOException e) {
            // Control de errores básico por si falla la entrada del teclado
            System.err.println("Ha ocurrido un error al leer de la consola: " + e.getMessage());
        }
    }
}