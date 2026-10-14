import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tarea2 {
    public static void main(String[] args) {
        StringBuilder cadena = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Martín\\OneDrive\\Documentos\\Tarea 2\\src\\datos\\lectura.txt"))) {
            int c;
            while ((c = br.read()) != -1 && c != '*') {
                cadena.append((char) c);
            }
            System.out.println(cadena);

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}