public class ProgramarArgumentos {

    public static void main(String[] args) {

        // Si no hay argumentos salimos con 1
        if (args.length < 1) {
            System.exit(1);
        }

        // Cogemos el primer argumento
        String arg = args[0];

        try {
            // Intentamos convertirlo a entero
            int numero = Integer.parseInt(arg);

            if (numero < 0) {
                // Entero menor que 0
                System.exit(3);
            } else {
                // Entero mayor o igual que 0
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            // Si no se puede convertir a entero es una cadena
            System.exit(2);
        }
    }
}