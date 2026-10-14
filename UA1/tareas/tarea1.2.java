import java.io.*;

public class MiniMonitor {
    static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] comando = WIN ? new String[]{"cmd", "/c", "dir"}
                : new String[]{"ls", "-l"};
        ProcessBuilder pb = new ProcessBuilder(comando);
        Process proceso = pb.start();

        System.out.println("PID del proceso hijo: " + proceso.pid());
        System.out.println("Salida del comando:");

        BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        String linea;
        int numLineas = 0;
        while ((linea = br.readLine()) != null) {
            numLineas++;
            System.out.println(numLineas + " - " + linea);


            int codigoSalida = proceso.waitFor();
            System.out.println("\n--- Informe final ---");
            System.out.println("Número de líneas leídas: " + numLineas);
            if (codigoSalida == 0) {
                System.out.println("Código de salida: " + codigoSalida + " (Correcto)");
            } else {
                System.out.println("Código de salida: " + codigoSalida + " (Falló)");

            }

        }    }}