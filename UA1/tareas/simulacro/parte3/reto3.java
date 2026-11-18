import java.io.IOException;

public class reto3 {
    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder comando = new ProcessBuilder(
                "java", "-cp", System.getProperty("java.class.path"), "Espera", "2"
        ).inheritIO();

        long inicio = System.currentTimeMillis();

        comando.start().waitFor();
        comando.start().waitFor();

        System.out.println("Secuencial: " + (System.currentTimeMillis() - inicio) + " ms");

        inicio = System.currentTimeMillis();

        Process primero = comando.start();
        Process segundo = comando.start();
        primero.waitFor();
        segundo.waitFor();

        System.out.println("Paralelo: " + (System.currentTimeMillis() - inicio) + " ms");
    }
}