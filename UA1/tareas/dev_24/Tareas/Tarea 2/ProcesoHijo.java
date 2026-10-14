import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcesoHijo {

    public static void main(String[] args) {
        StringBuilder textoAcumulado = new StringBuilder();

        // Control de errores al gestionar los flujos de entrada
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(System.in))) {
            int caracterLeido;

            // FR1: Lectura continua hasta encontrar '*' o fin de flujo (-1)
            while ((caracterLeido = lector.read()) != -1) {
                char caracter = (char) caracterLeido;

                if (caracter == '*') {
                    break; // Fin de lectura al detectar el asterisco
                }

                textoAcumulado.append(caracter);
            }

            // FR2: Muestra el contenido acumulado por la salida estándar
            System.out.println("--- Contenido recibido en el Proceso Hijo ---");
            System.out.println(textoAcumulado.toString());

        } catch (IOException e) {
            System.err.println("Error de E/S en ProcesoHijo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado en ProcesoHijo: " + e.getMessage());
        }
    }
}