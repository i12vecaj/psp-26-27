import java.io.*;

public class MiniMonitor {
static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

public static void main(String[] args) throws IOException, InterruptedException {
String[] comando = WIN ? new String[]{"cmd", "/c", "dir"}
: new String[]{"ls", "-l"};

// 1. TODO: crea el ProcessBuilder con el comando
    ProcessBuilder pb = new ProcessBuilder(comando);
// 2. TODO: lanza el proceso con start()
    Process p = new ProcessBuilder(comando).start();

// 3. TODO: muestra el PID
    System.out.println("PID: "+p.pid());

// 4. TODO: lee la salida línea a línea (BufferedReader + InputStreamReader)
//y numera cada línea

    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

// 5. TODO: espera a que termine con waitFor()
while (r.readLine()!= null);
int codigo = p.waitFor();



// 6. TODO: muestra líneas leídas y código de salida (0 = correcto)
    System.out.println("Lineas leidas y codigo"+ codigo + (codigo == 0 ?"(Correcto)" : "(Fallo)"));



}
}