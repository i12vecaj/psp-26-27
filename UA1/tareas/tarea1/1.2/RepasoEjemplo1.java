import java.io.*;

public class RepasoEjemplo1 {
    static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");
    public static void main(String[] args) throws IOException, InterruptedException {

        String[] comando = WIN ? new String[]{"cmd", "/c", "dir"}
                : new String[]{"ls", "-l"};

        ProcessBuilder pb = new ProcessBuilder(comando);

        System.out.println("Informe de ejecución");
        System.out.println("Comando: " + String.join(" ", comando));
        Process proceso = pb.start();

        System.out.println("PID del proceso hijo: " + proceso.pid());

        int lineas = 0;
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(proceso.getInputStream()))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                lineas++;
                System.out.printf("%3d | %s%n", lineas, linea);
            }
        }

        int codigo = proceso.waitFor();

        System.out.println("Resumen");
        System.out.println("Líneas leídas: " + lineas);
        System.out.println("Código de salida: " + codigo + (codigo == 0 ? "Correcto" : "Falló"));

    }
}