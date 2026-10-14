
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Programa que lee texto desde la entrada estándar hasta recibir
 * un asterisco (*) y muestra todo el texto leído.
 *
 * @author Alumno
 * @version 1.0
 */
public class LectorTexto {

    /**
     * Método principal del programa.
     *
     * @param args argumentos recibidos desde la línea de comandos
     */
    public static void main(String[] args) {

        StringBuilder texto = new StringBuilder();

        // Lector de caracteres procedentes de la entrada estándar.
        try (BufferedReader entrada = new BufferedReader(
                new InputStreamReader(System.in))) {

            System.out.println("Introduce un texto. Escribe * para terminar:");

            int caracter;

            // Leer caracteres hasta encontrar '*' o el fin de la entrada.
            while ((caracter = entrada.read()) != -1) {

                // Si se encuentra el asterisco, termina la lectura.
                if (caracter == '*') {
                    break;
                }

                // Añadir el carácter leído al texto acumulado.
                texto.append((char) caracter);
            }

            // Mostrar el resultado cuando termina la lectura.
            System.out.println("\nTexto leído:");
            System.out.println(texto);

        } catch (IOException e) {
            // Controlar posibles errores de entrada/salida.
            System.err.println(
                    "Error al leer la entrada: " + e.getMessage()
            );
        }
    }
}
