public class Hijo {
    public static void main(String[] args) {
        // FR1: Validaciones de argumentos
        if (args.length == 0) System.exit(1);

        try {
            int n = Integer.parseInt(args[0]);
            if (n < 0) System.exit(3);

            // Bucle que imprime de 1 a n con espera de 200 ms
            for (int i = 1; i <= n; i++) {
                System.out.println(i);
                Thread.sleep(200);
            }
            System.exit(0);

        } catch (NumberFormatException e) {
            System.exit(2);
        } catch (InterruptedException e) {
            System.exit(4);
        }
    }
}