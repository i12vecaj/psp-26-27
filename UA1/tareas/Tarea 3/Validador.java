public class Validador {

    public static void main(String[] args) {

        // si no hay argumentos
        if (args.length < 1) {
            System.out.println("Falta el argumento");
            System.exit(1);
        }

        String arg = args[0];

        // si no es un numero es una cadena
        if (!arg.matches("-?[0-9]+")) {
            System.out.println("Es una cadena");
            System.exit(2);
        }

        // numero negativo
        if (arg.startsWith("-")) {
            System.out.println("Es un entero menor que 0");
            System.exit(3);
        }

        System.out.println("Es un entero");
        System.exit(0);
    }
}