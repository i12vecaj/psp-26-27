public class Reto3 {
    public static void main(String[] args) throws Exception {
        String cp = System.getProperty("java.class.path");

        long inicio = System.currentTimeMillis();

        Process p1 = new ProcessBuilder(
            "java", "-cp", cp, "Espera", "2").start();
        p1.waitFor();

        Process p2 = new ProcessBuilder(
            "java", "-cp", cp, "Espera", "2").start();
        p2.waitFor();

        long fin = System.currentTimeMillis();
        System.out.println("Secuencial: " + (fin - inicio) + " ms");

        inicio = System.currentTimeMillis();

        p1 = new ProcessBuilder(
            "java", "-cp", cp, "Espera", "2").start();
        p2 = new ProcessBuilder(
            "java", "-cp", cp, "Espera", "2").start();

        p1.waitFor();
        p2.waitFor();

        fin = System.currentTimeMillis();
        System.out.println("Paralelo: " + (fin - inicio) + " ms");
    }
}