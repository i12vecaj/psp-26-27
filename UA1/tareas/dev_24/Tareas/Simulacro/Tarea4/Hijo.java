public class Hijo {

    public static void main(String[] args) {
        // FR1: 1 si no hay argumento
        if (args.length == 0) {
            System.exit(1);
        }

        int n = 0;

        // FR1: 2 si no es un número
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.exit(2);
        }

        // FR1: 3 si es negativo
        if (n < 0) {
            System.exit(3);
        }

        // FR1: Escribir de 1 a n con espera de 200 ms
        try {
            for (int i = 1; i <= n; i++) {
                System.out.println(i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // FR1: Fin correcto
        System.exit(0);
    }
}