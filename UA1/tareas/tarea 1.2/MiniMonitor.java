import java.io.*;

public class MiniMonitor {
    public static void main(String[] args) throws IOException, InterruptedException {

        // Elegimos el comando según tu sistema (Windows o Linux/Mac)
        String sistema = System.getProperty("os.name").toLowerCase();
        String[] comando;
        if (sistema.contains("win")) {
            comando = new String[]{"cmd", "/c", "dir"};
        } else {
            comando = new String[]{"ls", "-l"};
        }

        // 1. Preparar el comando
        ProcessBuilder pb = new ProcessBuilder(comando);

        // 2. Lanzarlo
        Process proceso = pb.start();

        // 3. Mostrar el PID
        System.out.println("PID: " + proceso.pid());

        // 4. Leer la salida línea a línea y numerarla
        BufferedReader lector = new BufferedReader(
                new InputStreamReader(proceso.getInputStream()));

        int contador = 0;
        String linea = lector.readLine();
        while (linea != null) {
            contador++;
            System.out.println(contador + ": " + linea);
            linea = lector.readLine();
        }
        lector.close();

        // 5. Esperar a que termine
        int codigo = proceso.waitFor();

        // 6. Informe final
        System.out.println("----- INFORME -----");
        System.out.println("Líneas leídas: " + contador);
        System.out.println("Código de salida: " + codigo);

        if (codigo == 0) {
            System.out.println("Resultado: CORRECTO");
        } else {
            System.out.println("Resultado: FALLÓ");
        }
    }
}