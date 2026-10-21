/*
 * Tarea 2 - Programacion de procesos en Java (I)
 * Andres Martinez Cano (dev_15)
 *
 * Programa que lee caracteres de la entrada estandar hasta que llega un
 * asterisco (*). Cuando llega el asterisco muestra todo lo que se ha leido.
 * Lo que venga despues del asterisco ya no se lee.
 *
 * Codigos de salida:
 *   0 -> todo bien, se ha leido hasta el asterisco
 *   2 -> se ha acabado la entrada sin escribir el asterisco
 *   3 -> error al leer la entrada
 * (no se usa el 1 porque es el que devuelve java cuando no encuentra la clase)
 *
 * Compilar y ejecutar desde la carpeta tarea2:
 *   javac LecturaAsterisco.java
 *   java LecturaAsterisco
 */
import java.io.*;

public class LecturaAsterisco {

    public static void main(String[] args) {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);
        String texto = "";
        int caracter;
        boolean hayAsterisco = false;

        System.out.println("Escribe el texto que quieras, para terminar pon un *");

        try {
            // FR1: leemos caracter a caracter hasta el asterisco
            // read() devuelve -1 si se acaba la entrada
            caracter = br.read();
            while (caracter != -1 && !hayAsterisco) {
                if (caracter == '*') {
                    hayAsterisco = true;
                } else {
                    texto = texto + (char) caracter;
                    caracter = br.read();
                }
            }
            br.close();
        } catch (IOException e) {
            // control de errores: no se ha podido leer la entrada
            System.err.println("Error al leer la entrada: " + e.getMessage());
            System.exit(3);
        }

        if (!hayAsterisco) {
            // control de errores: la entrada se ha acabado y no ha llegado el *
            System.err.println("Error: se ha terminado la entrada sin escribir el *");
            System.out.println("Texto leido hasta ese momento:");
            System.out.println(texto);
            System.exit(2);
        }

        // FR2: mostramos todo lo leido
        System.out.println("Texto leido:");
        System.out.println(texto);
        System.out.println("Caracteres leidos: " + texto.length());
        System.exit(0);
    }
}
