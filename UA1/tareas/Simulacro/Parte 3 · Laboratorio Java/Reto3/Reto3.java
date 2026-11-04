import java.io.IOException;

public class Reto3 {
    public static void main(String[] args) throws IOException, InterruptedException {
        String path = System.getProperty("java.class.path");
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", path, "Espera", "2");

        // SECUENCIAL: el segundo no arranca hasta que acaba el primero
        long inicio = System.currentTimeMillis();

        Process p1 = pb.start();
        p1.waitFor();

        Process p2 = pb.start();
        p2.waitFor();

        long fin = System.currentTimeMillis();
        System.out.println("Secuencial: " + (fin - inicio) + " ms");

        // PARALELO
        // reutiliza la variable inicio
        inicio = System.currentTimeMillis();

        //lanza los dos hijos
        Process p3 = pb.start();
        Process p4 = pb.start();

        //espera a los dos
        p3.waitFor();
        p4.waitFor();

        //guarda la hora de fin e imprime "Paralelo: ... ms"
        fin = System.currentTimeMillis();
        System.out.println("Paralelo: " + (fin - inicio) + " ms");
    }
}

