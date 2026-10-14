import java.io.*;

public class EjercicioMiniMonitor {
    static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] comando = WIN ? new String[]{"cmd", "/c", "dir"}
                               : new String[]{"ls", "-l"};

        // 1. Creo el ProcessBuilder con el comando
        ProcessBuilder pb = new ProcessBuilder(comando);

        // 2. Lanzo el proceso con start()
        Process proceso = pb.start();

        // 3. Muestro el PID
        System.out.println("PID del proceso hijo: " + proceso.pid());

        // 4. Leo la salida línea a línea y numero cada línea
        BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        String linea;
        int contador = 0;
        while ((linea = lector.readLine()) != null) {
            contador++;
            System.out.println(contador + ": " + linea);
        }

        // 5. Espero a que termine con waitFor()
        int codigo = proceso.waitFor();

        // 6. Muestro líneas leídas y código de salida (0 = correcto)
        System.out.println("Líneas leídas: " + contador);
        if (codigo == 0) {
            System.out.println("Código de salida: " + codigo + " -> correcto");
        } else {
            System.out.println("Código de salida: " + codigo + " -> falló");
        }
    }
}
