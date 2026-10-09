import java.io.*;

public class Ejemplo2 {
    public static void main(String[] args) throws IOException {

        Process p = new ProcessBuilder("CMD", "/C", "DIR").start();

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            String linea;
            int numLinea = 1;

            while ((linea = br.readLine()) != null) {
                System.out.println(numLinea + ": " + linea);
                numLinea++;
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // COMPROBACION DE ERROR
        try {
            int exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}