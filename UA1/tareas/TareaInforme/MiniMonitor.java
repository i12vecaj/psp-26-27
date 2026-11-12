import java.io.*;
public class MiniMonitor {

    static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException{
        String[] comando = WIN ? new String[]{"cmd","/c","dir"}
                :new String[]{"ls","-l"};

        ProcessBuilder pb = new ProcessBuilder(comando);

        Process proceso = pb.start();

        System.out.println("PID del proceso hijo: " + proceso.pid());
        
        int lineasLeidas = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineasLeidas++;
                System.out.println(lineasLeidas + ": " + linea);
            }
        }

        int codigoSalida = proceso.waitFor();

        System.out.println("\n            INFORME FINAL               ");
        System.out.println("Total de líneas leídas: " + lineasLeidas);
        System.out.println("Código de salida: " + codigoSalida +
                (codigoSalida == 0 ? " (Correcto)" : " (Falló)"));
    }

}