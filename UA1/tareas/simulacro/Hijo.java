/**
 * Programa hijo que recibe un numero y cuenta desde 1 hasta ese numero.
 * Si se le pasa algo raro, avisa al padre con el codigo de error que sea.
 */
public class Hijo {
    public static void main(String[] args) {
        // 1. Miro si se me pasa un argumento
        if (args.length == 0) {
            System.exit(1); // Error 1: falta argumento
        }

        int numero = 0;
        // 2. Se intenta pasar el texto a numero por si se le meten letras
        try {
            numero = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.exit(2); // Error 2: no es un numero
        }

        // 3. Compruebo si el numero es negativo
        if (numero < 0) {
            System.exit(3); // Error 3: numero negativo
        }

        // 4. Si todo está bien se imprime del 1 al número esperando 200ms
        try {
            for (int i = 1; i <= numero; i++) {
                System.out.println(i);
                Thread.sleep(200); // La pausa de 200 milisegundos entre numeros
            }
        } catch (InterruptedException e) {
            // Por si se corta la pausa del hilo
        }

        // Termina con 0, señalando que todo salio bien
        System.exit(0);
    }
}
