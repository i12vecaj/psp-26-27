public class ValidadorArgumentos {

    public static void main(String[] args) {
        if (args == null || args.length < 1) {
            System.err.println("Error: No se han proporcionado argumentos.");
            System.exit(1);

            String argumento = args[0];

            try {
                int numero = Integer.parseInt(argumento);

                if (numero < 0) {
                    System.out.println("El número introducido es negativo: " + numero);
                    System.exit(3); // Devuelve 3 si es menor que 0
                }

                System.out.println("El número es válido y mayor o igual que 0: " + numero);
                System.exit(0); // Devuelve 0 en caso de éxito general

            } catch (NumberFormatException e) {
                System.err.println("Error: El argumento '" + argumento + "' no es un número entero válido (es una cadena).");
                System.exit(2);
            }
        }
    }
}