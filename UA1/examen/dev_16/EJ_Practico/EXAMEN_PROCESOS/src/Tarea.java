import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Tarea {
    public static void main(String[] args) throws IOException, InterruptedException {
        Process p = new ProcessBuilder("java", "-version").start();



        int codigo = p.waitFor();
        System.out.println("Código de OK: " + codigo);




        if (args.length < 2) {
            System.exit(1);
        }

        try {
            // Primer argumento se convierte a entero
            int numero = Integer.parseInt(args[0]);



            // En cado de Numero Entero negativo
            if (numero < 0) {
                System.out.println("Error por numero negativo");
                System.exit(3);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error por formato incorrecto "+e.getMessage());
            System.exit(2);
        }

        // Cualquier otro caso
        System.exit(0);
    }
    }

