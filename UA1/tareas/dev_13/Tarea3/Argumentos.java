public class Argumentos {
    static void main(String[] argumentos) {

        if (argumentos.length < 1){  // Si los argumentos son < 1 devuelve 1
            System.exit(1);
        }

        String argumento = argumentos[0];

        try {

            int numero = Integer.parseInt(argumento);

            if (numero < 0){             // Si los argumentos son menores que 0 devuelve 3
                System.exit(3);
            }else {
                System.exit(0);  // En caso contrario devuelve 0
            }

        }catch (NumberFormatException e){
            System.exit(2);          // Si le damos una cadena nos devuelve 2

        }

    }
}
