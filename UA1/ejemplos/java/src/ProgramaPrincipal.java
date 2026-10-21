public class ProgramaPrincipal {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.exit(1);
        }

        try {
            int numero = Integer.parseInt(args[0]);

            if (numero < 0) {
                System.exit(3);
            } else {
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            System.exit(2);
        }
    }
}