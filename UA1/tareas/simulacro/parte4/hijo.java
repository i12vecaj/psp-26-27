public class hijo {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.exit(1);
        }

        int numero;
        try {
            numero = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.exit(2);
            return;
        }

        if (numero < 0) {
            System.exit(3);
        }

        // Espera 200 ms entre un número y el siguiente.
        try {
            for (long i = 1; i <= numero; i++) {
                if (i > 1) {
                    Thread.sleep(200);
                }
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.exit(4);
        }

        System.exit(0);
    }
}