import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Lanzador {

    public static void main(String[] args) {

        // comando para ejecutar Validador
        String[] comando;
        if (args.length > 0) {
            comando = new String[] {"java", "-cp", System.getProperty("java.class.path"), "Validador", args[0]};
        } else {
            comando = new String[] {"java", "-cp", System.getProperty("java.class.path"), "Validador"};
        }

        try {
            Process p = new ProcessBuilder(comando).redirectErrorStream(true).start();

            // leer lo que escribe Validador
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("Validador: " + linea);
            }

            // esperar y ver que devuelve
            int codigo = p.waitFor();
            System.out.println("Devuelve: " + codigo);

            if (codigo == 0) {
                System.out.println("Todo ok");
            } else if (codigo == 1) {
                System.out.println("No hay argumentos");
            } else if (codigo == 2) {
                System.out.println("El argumento es una cadena");
            } else if (codigo == 3) {
                System.out.println("El entero es menor que 0");
            }

        } catch (Exception e) {
            // si falla algo
            System.out.println("Error: " + e.getMessage());
        }
    }
}