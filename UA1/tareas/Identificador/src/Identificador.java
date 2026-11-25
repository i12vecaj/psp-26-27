public class Identificador implements Runnable {
    private String[] args;

    public Identificador(String[] args) {
        this.args = args;
    }

    @Override
    public void run() {
        int id;
        if (args.length < 1) {
        } else {
            try {
                int numero = Integer.parseInt(args[0]);
                if (numero < 0) {
                    System.exit(3);
                } else {
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                System.exit(2) ;
            }




        }
    }
}