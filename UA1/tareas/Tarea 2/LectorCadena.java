/*
 * T2 - Programacion de procesos en Java (I)
 *
 * Lee caracteres de la entrada estandar hasta que llega un asterisco y
 * entonces muestra todo lo que ha leido.
 *
 * javac LectorCadena.java
 * java LectorCadena
 *
 * Salidas: 0 bien, 1 se acabo la entrada sin asterisco, 2 error de lectura.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LectorCadena {

    static final char TERMINADOR = '*';

    public static void main(String[] args) {

        System.out.println("Escribe texto y termina con un " + TERMINADOR);

        StringBuilder texto = new StringBuilder();
        boolean terminado = false;

        // el try-with-resources cierra el lector aunque salte una excepcion
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in))) {

            int c;

            // read() devuelve -1 cuando se acaba la entrada (Ctrl+Z en Windows)
            while ((c = entrada.read()) != -1) {
                if ((char) c == TERMINADOR) {
                    terminado = true;
                    break;
                }
                texto.append((char) c);
            }

        } catch (IOException e) {
            System.out.println("Error al leer la entrada: " + e.getMessage());
            System.exit(2);
        }

        if (!terminado) {
            System.out.println("Se ha acabado la entrada y no ha llegado el " + TERMINADOR);
            System.exit(1);
        }

        System.out.println("\nTexto leido:");

        if (texto.length() == 0) {
            System.out.println("(vacio, el asterisco era el primer caracter)");
        } else {
            System.out.println(texto);
            System.out.println("\nCaracteres: " + texto.length());
        }
    }
}
