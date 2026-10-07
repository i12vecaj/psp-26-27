
public class Ejemplo2 {
    public static void main(String[] args) throws IOException {
        Process p = new ProcessBuilder("CMD", "/C", "DIR").start();

        try {

            InputStream is = p.getInputStream();

            // Leemos línea a línea
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String linea;
            int numeroLinea = 1;

            while ((linea = br.readLine()) != null) {
                System.out.println(numeroLinea + ": " + linea);
                numeroLinea++;
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // comprobamos el ERROR - 0 bien - 1 mal
        int exitVal;

        try {
            exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}// Ejemplo2
