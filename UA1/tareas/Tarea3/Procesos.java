public class Procesos {
    static void main(String[] args) {

        if (args.length < 1) {
            System.exit(1);
        }
        try {
            if (Integer.parseInt(args[0]) <= 0) {
                System.exit(3);
            }
        } catch (NumberFormatException e) {
            System.exit(2);
        }
        System.exit(0);


    }
}

