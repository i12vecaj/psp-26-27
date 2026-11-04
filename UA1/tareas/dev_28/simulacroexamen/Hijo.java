
public class Hijo {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.exit(1);
            return;
        }

        final int limite;

        try {
            limite = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.exit(2);
            return;
        }

        if (limite < 0) {
            System.exit(3);
            return;
        }

        try {
            // long evita desbordar el contador con Integer.MAX_VALUE.
            for (long numero = 1; numero <= limite; numero++) {
                System.out.println(numero);

                // Espera 200 ms entre un número y el siguiente.
                if (numero < limite) {
                    Thread.sleep(200);
                }
            }

            System.exit(0);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hijo interrumpido.");
            System.exit(4);
        }
    }
}
