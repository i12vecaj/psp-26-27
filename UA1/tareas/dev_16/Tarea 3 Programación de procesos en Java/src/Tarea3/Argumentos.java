package Tarea3;

public class Argumentos {

    public static void main(String[] args) {
        // Sin argumentos
        if (args.length < 1) {
            System.exit(1);
        }

        try {
            // Se intenta convertir el primer argumento a entero
            int numero = Integer.parseInt(args[0]);

            // Entero negativo
            if (numero < 0) {
                System.exit(3);
            }
        } catch (NumberFormatException e) {
            // No es un número entero, se considera una cadena
            System.exit(2);
        }

        // Cualquier otro caso
        System.exit(0);
    }
}
