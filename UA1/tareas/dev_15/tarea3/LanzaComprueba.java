/*
 * Tarea 3 - Programacion de procesos en Java (II)
 * Andres Martinez Cano (dev_15)
 *
 * FR3: programa que ejecuta CompruebaArgumento como un proceso hijo y
 * muestra por pantalla lo que ha pasado segun el valor que devuelve.
 * Los argumentos que se le pasan a este programa se le pasan tal cual al hijo.
 *
 * Compilar y ejecutar desde la carpeta tarea3:
 *   javac CompruebaArgumento.java LanzaComprueba.java
 *   java LanzaComprueba            (sin argumentos -> 1)
 *   java LanzaComprueba hola       (cadena -> 2)
 *   java LanzaComprueba -5         (entero negativo -> 3)
 *   java LanzaComprueba 7          (otro caso -> 0)
 */
import java.io.*;
import java.util.*;

public class LanzaComprueba {

    public static void main(String[] args) {
        // control de errores: si no esta compilado el otro programa no se puede lanzar
        File clase = new File("CompruebaArgumento.class");
        if (!clase.exists()) {
            System.out.println("Error: no se encuentra CompruebaArgumento.class");
            System.out.println("Compila primero con: javac CompruebaArgumento.java");
            System.exit(1);
        }

        // montamos el comando: java CompruebaArgumento y detras los argumentos
        List<String> comando = new ArrayList<String>();
        comando.add("java");
        comando.add("CompruebaArgumento");
        for (int i = 0; i < args.length; i++) {
            comando.add(args[i]);
        }

        ProcessBuilder pb = new ProcessBuilder(comando);
        // si el hijo saca algun error se ve en la consola
        pb.inheritIO();

        System.out.println("Comando que se lanza: " + comando);

        try {
            Process p = pb.start();
            int codigo = p.waitFor();
            System.out.println("Valor devuelto: " + codigo);

            // segun el valor devuelto decimos que ha pasado
            switch (codigo) {
                case 0:
                    System.out.println("0 -> el argumento es correcto (numero que no es un entero negativo)");
                    break;
                case 1:
                    System.out.println("1 -> no se ha pasado ningun argumento");
                    break;
                case 2:
                    System.out.println("2 -> el argumento es una cadena, no un numero");
                    break;
                case 3:
                    System.out.println("3 -> el argumento es un numero entero menor que 0");
                    break;
                default:
                    System.out.println("Valor desconocido");
                    break;
            }
        } catch (IOException e) {
            // control de errores: no se ha podido arrancar el proceso
            System.out.println("Error al lanzar el proceso: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            // control de errores: nos han interrumpido mientras esperabamos al hijo
            System.out.println("Se ha interrumpido la espera del proceso hijo");
            System.exit(1);
        }
    }
}
