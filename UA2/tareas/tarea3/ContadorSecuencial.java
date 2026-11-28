package UA2.Tarea3;


import java.io.FileReader;
import java.io.IOException;

public class ContadorSecuencial {

    public static void contarCaracteres(String nombreFichero) {

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

    public static void ejecutar(String[] ficheros) {

        for (String fichero : ficheros) {
            contarCaracteres(fichero);
        }
    }
}