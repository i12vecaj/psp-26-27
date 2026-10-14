import java.util.Scanner;

/**
 * Programa que lee una cadena de caracteres desde teclado
 * hasta recibir un asterisco (*).
 * Al terminar, muestra todo el texto introducido.
 */
public class LeerCadena {

    public static void main(String[] args) {

        // Creamos el Scanner para leer desde el teclado
        Scanner teclado = new Scanner(System.in);

        // Variable donde se guardará el texto
        StringBuilder cadena = new StringBuilder();

        System.out.println("Introduce un texto (termina con *):");

        try {
            // Leemos caracteres hasta encontrar el asterisco
            while (teclado.hasNextLine()) {
                String linea = teclado.nextLine();

                // Comprobamos si la línea contiene el asterisco
                int posicion = linea.indexOf('*');

                if (posicion != -1) {
                    // Guardamos solamente el texto anterior al asterisco
                    cadena.append(linea, 0, posicion);
                    break;
                } else {
                    // Guardamos la línea completa
                    cadena.append(linea).append(System.lineSeparator());
                }
            }

            // Mostramos todo el texto leído
            System.out.println("\nTexto introducido:");
            System.out.println(cadena);

        } catch (Exception e) {
            // Controlamos posibles errores de lectura
            System.err.println("Error al leer los datos: " + e.getMessage());

        } finally {
            // Cerramos el Scanner
            teclado.close();
        }
    }
}