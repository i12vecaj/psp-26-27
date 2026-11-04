public class Tarea {
   public static void main(String[] args) {


        if (args.length != 2) {
            System.exit(1);
        }

        int n = 0;

        //
        try {
            n = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.exit(2);
        }

        if (n < 0) {
            System.exit(3);
        }
    }
}
