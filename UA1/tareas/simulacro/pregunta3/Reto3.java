public class Reto3 {

    public static void main(String[] args) throws Exception {

        // Secuencial
        long inicio = System.currentTimeMillis();

        Process p1 = new ProcessBuilder(
                "java", "-cp",
                System.getProperty("java.class.path"),
                "Espera", "2"
        ).start();

        p1.waitFor();

        Process p2 = new ProcessBuilder(
                "java", "-cp",
                System.getProperty("java.class.path"),
                "Espera", "2"
        ).start();

        p2.waitFor();

        long fin = System.currentTimeMillis();

        System.out.println("Tiempo secuencial: "
                + (fin - inicio) + " ms");


        // Paralelo
        inicio = System.currentTimeMillis();

        Process p3 = new ProcessBuilder(
                "java", "-cp",
                System.getProperty("java.class.path"),
                "Espera", "2"
        ).start();

        Process p4 = new ProcessBuilder(
                "java", "-cp",
                System.getProperty("java.class.path"),
                "Espera", "2"
        ).start();

        p3.waitFor();
        p4.waitFor();

        fin = System.currentTimeMillis();

        System.out.println("Tiempo paralelo: "
                + (fin - inicio) + " ms");
    }
}
