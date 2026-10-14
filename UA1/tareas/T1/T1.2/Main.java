import java.io.*;

public class Main {
    static void main() throws IOException {
        int numlineas = 0;

        // CREACION DEL PROCESS BUILDER
        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "DIR");

        // LANZAMIENTO DEL PROCESO
        Process p = pb.start();

        // SACAR EL PID DEL PROCESO
        System.out.println("PID DEL PROCESO PADRE: "+p.toHandle().pid());
        System.out.println("PID DEL PROCESO HIJO: "+p.pid());

        // LEER LA SALIDA
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            int numeroLinea = 0;

            while((linea = br.readLine()) != null) {
                numeroLinea++;
                System.out.println(numeroLinea+". "+linea);
            }

            numlineas = numeroLinea;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // ESPERAR A QUE TERMINE CON WAIT FOR
        int salida;
        try {
            salida = p.waitFor();
            System.out.println("Salida: "+salida +" ; Lineas leidas: "+numlineas);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
