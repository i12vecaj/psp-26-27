/*
 * Tarea 3 - Programacion de procesos en Java (II)
 * Andres Martinez Cano (dev_15)
 *
 * Programa que recibe argumentos por main() y devuelve con System.exit():
 *   1 -> si no hay ningun argumento
 *   2 -> si el argumento es una cadena (no es un numero)
 *   3 -> si el argumento es un numero entero menor que 0
 *   0 -> en cualquier otro caso
 * Si se pasan varios argumentos solo se mira el primero.
 *
 * Compilar y ejecutar desde la carpeta tarea3:
 *   javac CompruebaArgumento.java
 *   java CompruebaArgumento -5
 */
public class CompruebaArgumento {

    public static void main(String[] args) {
        // FR1 y FR2: si el numero de argumentos es menor que 1 devolvemos 1
        if (args.length < 1) {
            System.exit(1);
        }

        String argumento = args[0];

        try {
            // si se puede pasar a entero es un numero entero
            int numero = Integer.parseInt(argumento);
            if (numero < 0) {
                // entero menor que 0
                System.exit(3);
            } else {
                // entero 0 o mayor, es "cualquier otro caso"
                System.exit(0);
            }
        } catch (NumberFormatException e) {
            // no es un entero, miramos si por lo menos es un numero con decimales
        }

        try {
            Double.parseDouble(argumento);
            // es un numero con decimales (por ejemplo 2.5), tambien es "cualquier otro caso"
            System.exit(0);
        } catch (NumberFormatException e) {
            // control de errores: no es un numero, asi que es una cadena
            System.exit(2);
        }
    }
}
