import java.io.IOException;

public class reto3 {
    static void main(String[] args) {
        String cp = System.getProperty("java.class.path");

        try {
            long iniciosecuencial = System.currentTimeMillis();

            Process p1 = new ProcessBuilder("java", cp, "espera", "2").start();
            p1.waitFor();
            Process p2 = new ProcessBuilder("java", cp, "espera", "2").start();
            p2.waitFor();

            long finSecuencial = System.currentTimeMillis();
            System.out.println("Tiempo secuencial " + (finSecuencial - iniciosecuencial));

            long iniciopararelo = System.currentTimeMillis();

            Process p3 = new ProcessBuilder("java", cp, "espera", "2").start();
            Process p4 = new ProcessBuilder("java", cp, "espera", "2").start();

            p3.waitFor();
            p4.waitFor();

            long finpararelo = System.currentTimeMillis();
            System.out.println("tiempo pararelo " + (finpararelo - iniciopararelo));


        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
