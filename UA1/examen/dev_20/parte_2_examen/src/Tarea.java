public class Tarea {
    public static void main(String[] args) {
        // VALIDAMOS SI ALGUNO DE LOS ARGUMENTOS FALTA
        if (args.length < 2) System.exit(1);

        try {
            // VALIDAMOS SI EL NUMERO NO ES PARSEABLE (Y LO SACAMOS POR EL CATCH)
            int n = Integer.parseInt(args[1]);
            // VALIMOS SI EL NUMERO ES NEGATIVO
            if (n < 0) System.exit(3);
            // VALIDAMOS SI ES LA PALABRA FALLA
            if (args[0].equals("falla")) {
                System.exit(4);
            }
            System.exit(0);
        } catch (NumberFormatException e) {
            System.exit(2);
        }
    }
}
