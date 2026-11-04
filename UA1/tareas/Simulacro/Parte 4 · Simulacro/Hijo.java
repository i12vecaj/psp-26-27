/**
 * Proceso hijo: recibe un numero n y escribe los numeros de 1 a n.
 * Codigos de salida: 0 correcto, 1 falta argumento, 2 no es un numero,
 * 3 numero negativo, 4 espera interrumpida.
 */
public class Hijo {
    public static void main(String[] args) {
        // Comprueba que hay argumento
        if (args.length == 0) {
            System.exit(1);
        }

        int n = 0;

        // Comprueba que el argumento es un numero
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.exit(2);
        }

        // Comprueba que el numero no es negativo
        if (n < 0) {
            System.exit(3);
        }

        // Escribe solo el numero en cada linea, para que el padre pueda sumarlo
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
