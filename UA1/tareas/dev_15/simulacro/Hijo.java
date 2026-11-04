/*
 * Hijo: recibe un numero n y escribe los numeros de 1 a n, uno por linea,
 * esperando 200 ms entre cada uno.
 * Codigos de salida: 0 todo bien, 1 no hay argumento, 2 no es un numero, 3 numero negativo.
 */
public class Hijo {
    public static void main(String[] args) {
        // sin argumento
        if (args.length < 1) {
            System.exit(1);
        }

        int n;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            // el argumento no es un numero
            System.exit(2);
            return;
        }

        // numero negativo
        if (n < 0) {
            System.exit(3);
        }

        for (int i = 1; i <= n; i++) {
            System.out.println(i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.exit(4);
            }
        }
        System.exit(0);
    }
}
