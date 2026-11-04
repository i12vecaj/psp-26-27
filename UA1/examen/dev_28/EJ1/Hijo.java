public class Hijo {

    static void main(String[] args) {
        if (args.length == 0){
            System.exit(1);
            return;
        }

        final int limite;


        try {
            limite = Integer.parseInt(args[0]);
        }catch (NumberFormatException e){
            System.exit(2);
            return;
        }

        if (limite < 0 ){
            System.exit(3);
            return;
        }

        try{
            // Uso long por si usamos numeros tan largos para evitar errores
            for (long numero = 1; numero < limite ; numero++) {
                System.out.println(numero);

                //Aqui es el metodo que usamos que entre cada numero se dan 200 ms
                if (numero < limite){
                    Thread.sleep(200);
                }

            }

            System.exit(0);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.err.println("Hijo interrumpido");
            System.exit(4);

        }


    }


}
