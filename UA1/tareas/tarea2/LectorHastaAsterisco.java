/**
 * Programa que lee caracteres desde la entrada estándar hasta recibir un '*'.
 * Una vez recibido el carácter de terminación, muestra todo lo leído.
 * Incluye control de errores.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LectorHastaAsterisco {

    public static void main(String[] args) {

        System.out.println("Introduce texto. El programa finalizará al recibir '*':");

        StringBuilder acumulado = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            int c;
            while ((c = br.read()) != -1) {   // lectura carácter a carácter

                if ((char) c == '*') {        // FR1: carácter de terminación
                    break;
                }

                acumulado.append((char) c);   // acumulamos lo leído
            }

            // FR2: mostrar todo lo leído
            System.out.println("Contenido leido: " + acumulado.toString());

        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}

