public class Hijo {

    public static void main(String[] args) {

        // Comprobar que existe un argumento
        if (args.length < 1) {
            System.exit(1);
        }

        int n;

        // Comprobar que el argumento es un número
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.exit(2);
            return;
        }

        // Comprobar que el número no es negativo
        if (n < 0) {
            System.exit(3);
        }

        // Mostrar los números del 1 hasta n
        for (int i = 1; i <= n; i++) {
            System.out.println(i);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.exit(4);
            }
        }

        System.exit(0);
    }
}