import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MiniMonitor {
    static boolean WIN = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] comando = WIN ? new String[]{"cmd","/c","dir"}
                : new String[]{"ls","-l"};

        // 1 crear el proceso ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder(comando);

        // 2 lanzar con start
        Process p = pb.start();

        // 3 mostrar el PID
        long pidHijo =p.pid();
        long pidPadre = ProcessHandle.current().pid();
        System.out.println("PID del proceso padre: " + pidPadre);
        System.out.println("PID del proceso hijo: " + pidHijo);


        // 4 leer la salida linea a linea y numerar cada linea
        BufferedReader br = new BufferedReader
                ( new InputStreamReader(p.getInputStream()));

        String linea;
        int numerolinea = 0;

            //4.1 se enumera linea a linea
        while ((linea = br.readLine()) != null) {
            numerolinea++;
            System.out.println("Linea del proceso: " + linea);

        }
        br.close();


        // 5 Esperar a que termine con waitFor()
        int exit = p.waitFor();

        // 6 mostrar las lineas leidas y el codigo de salida
        System.out.println("Numero de lineas leidas: "+ numerolinea );
        System.out.println("Código de salida: " + exit);

        if (exit==0) {
            System.out.println("Proceso terminado (CORRECTO)");

        }else  {
            System.out.println("Estado en ERROR.");
        }


    }
}
