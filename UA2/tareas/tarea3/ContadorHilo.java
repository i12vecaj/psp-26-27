package UA2.Tarea3;


import java.io.FileReader;
import java.io.IOException;

public class ContadorHilo extends Thread {

    private String nombreFichero;

    public ContadorHilo(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    @Override
    public void run() {

        int contador = 0;

        try (FileReader lector = new FileReader(nombreFichero)) {

            while (lector.read() != -1) {
                contador++;
            }

            System.out.println("Fichero: " + nombreFichero);
            System.out.println("Número de caracteres: " + contador);

        } catch (IOException e) {
            System.out.println("Error al leer el fichero "
                    + nombreFichero + ": " + e.getMessage());
        }
    }
}