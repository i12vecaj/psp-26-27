package UA1.tareas.Tarea2;
import java.io.*;

public class monitor {
    static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] comando = WIN ? new String[]{"cmd", "/c", "dir"}
                : new String[]{"ls", "-l"};

        // 1. Crea el ProcessBuilder con el comando
        ProcessBuilder pb = new ProcessBuilder(comando);

        // 2. Lanza el proceso con start()
        Process p = pb.start();

        // NUEVO: Preparamos el archivo .txt donde vamos a guardar el informe
        File archivo = new File("UA1/tareas/Tarea2/informe_procesos.txt");
        PrintWriter escritor = new PrintWriter(new FileWriter(archivo));

        // 3. Muestra el PID (en consola y en archivo)
        long pid = p.pid();
        System.out.println("PID del proceso hijo: " + pid);
        System.out.println("------------------------------------------------");

        escritor.println("PID del proceso hijo: " + pid);
        escritor.println("------------------------------------------------");

        // 4. Lee la salida línea a línea y numera cada línea
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        int lineasLeidas = 0;

        while ((linea = br.readLine()) != null) {
            lineasLeidas++;
            String textoFila = lineasLeidas + " - " + linea;

            System.out.println(textoFila); // Lo pinta en tu pantalla
            escritor.println(textoFila);   // Lo escribe dentro del txt
        }
        br.close();

        // 5. Espera a que termine con waitFor()
        int codigoSalida = p.waitFor();

        // 6. Muestra líneas leídas y código de salida (0 = correcto)
        String textoInforme = "\nINFORME FINAL:\nTotal de líneas leídas: " + lineasLeidas;
        String textoCodigo = (codigoSalida == 0) ? "Código de salida: 0 (Correcto)"
                : "Código de salida: " + codigoSalida + " (Falló)";

        // Imprimimos el informe final en pantalla
        System.out.println("------------------------------------------------");
        System.out.print(textoInforme + "\n" + textoCodigo + "\n");

        // Guardamos el informe final en el archivo txt y cerramos
        escritor.println("------------------------------------------------");
        escritor.println(textoInforme);
        escritor.println(textoCodigo);
        escritor.close(); // ¡MUY IMPORTANTE! Si no cierras, el archivo se queda en blanco.

        System.out.println("\n Se ha generado el archivo: " + archivo.getName());
    }
}