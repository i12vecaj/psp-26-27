
public class Hijo {

    public static void main(String[] args) {
        // 1. Comprobar si se ha pasado algún argumento (Código de salida 1)
        if (args.length < 1) {
            System.exit(1);
        }

        int n = 0;
        try {
            // 2. Comprobar si el argumento es un número entero válido (Código de salida 2)
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.exit(2);
        }

        // 3. Comprobar si el número es negativo (Código de salida 3)
        if (n < 0) {
            System.exit(3);
        }

        // 4. Si todo es correcto, iterar del 1 al n
        for (int i = 1; i <= n; i++) {
            System.out.println(i);
            try {
                // Espera de 200 milisegundos entre cada número
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // Restaurar el estado de interrupción y salir con error general si ocurre
                Thread.currentThread().interrupt();
                System.exit(4);
            }
        }

        // Finalización exitosa
        System.exit(0);
    }
}