public class Hijo {

    public static void main(String[] args) {
        // Si no me pasan argumento salgo con codigo 1
        if (args.length == 0) {
            System.exit(1);
        }

        int n = 0;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            // El argumento no es un numero, salgo con codigo 2
            System.exit(2);
        }

        // Si el numero es negativo salgo con codigo 3
        if (n < 0) {
            System.exit(3);
        }

        // Escribo del 1 al n, uno por linea, esperando 200 ms entre cada uno
        for (int i = 1; i <= n; i++) {
            System.out.println(i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.err.println("Me han interrumpido");
                System.exit(4);
            }
        }

        System.exit(0);
    }
}
