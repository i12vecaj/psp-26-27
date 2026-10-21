public class ProgramaPrincipal {

    public static void main(String[] args) {

        // Comprueba si se ha introducido algún argumento.
        if (args.length < 1) {
            System.exit(1);
        }

        try {
            // Intenta convertir el argumento a un número entero.
            int numero = Integer.parseInt(args[0]);

            // Si el número es negativo, devuelve el código 3.
            if (numero < 0) {
                System.exit(3);
            }

            // Si todo es correcto, devuelve el código 0.
            System.exit(0);

        } catch (NumberFormatException e) {
            // Si el argumento no es un entero, devuelve el código 2.
            System.exit(2);
        }
    }
}