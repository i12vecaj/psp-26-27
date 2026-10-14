package prueba.java;

public class CodigoConFallos {
    // Caso 1: saluda al nombre que escribe el usuario
    static void saludar(String nombre) throws Exception {
        String comando = "echo Hola " + nombre;
        Process p = new ProcessBuilder("sh", "-c", comando).start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);
    }

    // Caso 2: cuenta cuántos números genera "seq"
    static void contarNumeros() throws Exception {
        Process p = new ProcessBuilder("seq", "1", "1000000").start();
        p.waitFor();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        int n = 0;
        while (br.readLine() != null) n++;
        System.out.println("Números generados: " + n);
    }

    // Caso 3: ordena unas palabras con el comando "sort"
    static void ordenar() throws Exception {
        Process p = new ProcessBuilder("sort").start();
        p.getOutputStream().write("pera\nmanzana\nbanana\n".getBytes());
        p.getOutputStream().flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);
    }

    // Caso 4: muestra el contenido de un fichero
    static void mostrar(String fichero) throws Exception {
        Process p = new ProcessBuilder("cat", fichero).start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);
        p.waitFor();
        System.out.println("Fichero mostrado correctamente");
    }

    public static void main(String[] args) throws Exception {
        String texto = args.length > 1 ? args[1] : "Ana";
        switch (args[0]) {
            case "1" -> saludar(texto);
            case "2" -> contarNumeros();
            case "3" -> ordenar();
            case "4" -> mostrar(texto);
        }
    }
}

/*
Fallos que he visto:

1. Faltan los imports de BufferedReader e InputStreamReader.

2. si metes un nombre con ciertos caracteres puede dar problemas porque se ejecuta como un comando.

3. se espera a que termine el proceso antes de leer los números y puede quedarse pillado

4. falta cerrar la entrada de sort para que sepa que ya se han enviado todas las palabras y pueda ordenarlas

5. pone que el fichero se ha mostrado bien aunque puede que no exista o dé algún error

6. Si ejecutas el programa sin poner ninguna opción falla porque intenta acceder a args[0] cuando no hay argumentos

7. Si pones una opción que no sea del 1 al 4, no muestra ningún mensaje diciendo que la opción es incorrecta

8. Los comandos sh, seq, sort y cat pueden no funcionar si se ejecuta el programa en un sistema que no los tenga

9. Si los asteriscos de System.*out*.println y de los nombres de los métodos están en el código original hay que quitarlos porque así Java no compila */

/*
package prueba.java;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CodigoConFallos {

    // Caso 1: saluda al nombre indicado sin construir ni ejecutar un comando.
    static void saludar(String nombre) {
        System.out.println("Hola " + nombre);
    }

    // Caso 2: cuenta los números del 1 al 1 000 000.
    static void contarNumeros() {
        int n = 0;
        for (int numero = 1; numero <= 1_000_000; numero++) {
            n++;
        }
        System.out.println("Números generados: " + n);
    }

    // Caso 3: ordena las palabras en orden natural.
    static void ordenar() {
        List<String> palabras = new ArrayList<>();
        Collections.addAll(palabras, "pera", "manzana", "banana");
        Collections.sort(palabras);

        for (String palabra : palabras) {
            System.out.println(palabra);
        }
    }

    // Caso 4: muestra el contenido de un fichero en UTF-8.
    static void mostrar(String nombreFichero) throws IOException {
        Path fichero = Path.of(nombreFichero);

        for (String linea : Files.readAllLines(fichero, StandardCharsets.UTF_8)) {
            System.out.println(linea);
        }

        System.out.println("Fichero mostrado correctamente");
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println(
                "Uso: java prueba.java.CodigoConFallos <1|2|3|4> [nombre o fichero]"
            );
            return;
        }

        String texto = args.length > 1 ? args[1] : "Ana";

        try {
            switch (args[0]) {
                case "1":
                    saludar(texto);
                    break;
                case "2":
                    contarNumeros();
                    break;
                case "3":
                    ordenar();
                    break;
                case "4":
                    mostrar(texto);
                    break;
                default:
                    System.err.println("Opción no válida. Usa 1, 2, 3 o 4.");
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer el fichero: " + e.getMessage());
        }
    }
}
 */
//este es el codigo bien hecho con la SKILL que nos has proporcionado