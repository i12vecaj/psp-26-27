import java.io.*;

public class MiniMonitor {
    static final boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] comando = WIN ? new String[]{"cmd", "/c", "dir"}
                               : new String[]{"ls", "-l"};

        ProcessBuilder pb = new ProcessBuilder(comando);
        Process p = pb.start();

        System.out.println("PID: " + p.pid());

        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int n = 0;
        String s = br.readLine();
        while (s != null) {
            n = n + 1;
            System.out.println(n + " -> " + s);
            s = br.readLine();
        }

        int salida = p.waitFor();

        System.out.println("Total de lineas: " + n);
        System.out.println("Codigo: " + salida);
        if (salida == 0)
            System.out.println("Todo bien");
        else
            System.out.println("Ha fallado");
    }
}
