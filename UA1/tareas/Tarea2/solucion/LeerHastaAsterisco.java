package UA1.tareas.Tarea2.solucion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class LeerHastaAsterisco {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder texto = new StringBuilder();
        boolean asteriscoEncontrado = false;

        System.out.println("Escribe el texto que quieras (termina con *):");

        try {
            int c;
            while ((c = br.read()) != -1) {
                if (c == '*') {
                    asteriscoEncontrado = true;
                    break; 
                }
                texto.append((char) c);
            }
        } catch (IOException e) {
            System.err.println("Error al leer la entrada: " + e.getMessage());
            System.exit(2);
        }

        if (!asteriscoEncontrado) {
            System.err.println("Error: la entrada ha terminado sin el carácter de terminación *");
            System.exit(1);
        }

        
        System.out.println("Texto leído:");
        System.out.println(texto);

        System.out.println("Total de caracteres leídos: " + texto.length());
    }
}
