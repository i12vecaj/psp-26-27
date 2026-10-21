public class Validador {

    public static void main(String[] args) {

        // Si no hay argumentos devolvemos 1
        if (args.length < 1) {
            System.exit(1);
        }

        try {
            // Intentamos convertir el primer argumento a entero
            int numero = Integer.parseInt(args[0]);

            if (numero < 0) {
                System.exit(3); // entero negativo
            } else {
                System.exit(0); // entero mayor o igual que 0
            }
        } catch (NumberFormatException e) {
            // Si no se puede convertir a numero, es una cadena
            System.exit(2);
        }
    }
}
