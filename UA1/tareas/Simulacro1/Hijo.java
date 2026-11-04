/**
 * Clase Hijo: Imprime números del 1 al N con pausas de 200ms.
 */
public class Hijo {
    public static void main(String[] args) {
        if (args.length == 0) System.exit(1); // FR1: Sin argumento

        try {
            int n = Integer.parseInt(args[0]);
            if (n < 0) System.exit(3); // FR1: Número negativo

            // FR1: Imprimir números con espera
            for (int i = 1; i <= n; i++) {
                System.out.println(i);
                Thread.sleep(200);
            }
            System.exit(0); // FR1: Todo OK

        } catch (NumberFormatException e) {
            System.exit(2); // FR1: No es un número
        } catch (InterruptedException e) {
            System.exit(4); // Error de hilo
        }
    }
}