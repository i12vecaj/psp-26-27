public class Reto3 {
    public static void main(String[] args) throws Exception {
        long t0 = System.currentTimeMillis();

        Process p1 = new ProcessBuilder("java", "-cp",
            System.getProperty("java.class.path"), "Espera", "2").start();
        p1.waitFor();
        Process p2 = new ProcessBuilder("java", "-cp",
            System.getProperty("java.class.path"), "Espera", "2").start();
        p2.waitFor();

        System.out.println("Secuencial: " + (System.currentTimeMillis() - t0) + " ms");

        t0 = System.currentTimeMillis();
        Process a = new ProcessBuilder("java", "-cp",
            System.getProperty("java.class.path"), "Espera", "2").start();
        Process b = new ProcessBuilder("java", "-cp",
            System.getProperty("java.class.path"), "Espera", "2").start();
        a.waitFor();
        b.waitFor();

        System.out.println("Paralelo: " + (System.currentTimeMillis() - t0) + " ms");
    }
}