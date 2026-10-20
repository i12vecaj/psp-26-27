import java.io.*;

/**
 * CÓDIGO CON FALLOS A PROPÓSITO (para revisar en clase).
 * Uso: java CodigoConFallos.java <1|2|3|4> [texto]
 */
public class CodigoConFallos {

    // Caso 1: saluda al nombre que escribe el usuario
    static void saludar(String nombre) throws Exception {
        // FALLO: Usa 'sh' (incompatible con Windows) y es vulnerable a inyección.
        String comando = "echo Hola " + nombre;
        Process p = new ProcessBuilder("sh", "-c", comando).start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);
    }

    // Caso 2: cuenta cuántos números genera "seq"
    static void contarNumeros() throws Exception {
        // FALLO: Usa 'seq' (de Linux) y puede bloquearse si se llena el búfer antes del waitFor.
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

        // FALLO: Riesgo de bloqueo al escribir en el proceso sin cerrar el flujo de datos.
        p.getOutputStream().write("pera\nmanzana\nbanana\n".getBytes());
        p.getOutputStream().flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);
    }

    // Caso 4: muestra el contenido de un fichero
    static void mostrar(String fichero) throws Exception {
        // FALLO: Usa 'cat' (de Linux) y es vulnerable a accesos indebidos de ficheros.
        Process p = new ProcessBuilder("cat", fichero).start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);
        p.waitFor();
        System.out.println("Fichero mostrado correctamente");
    }

    public static void main(String[] args) throws Exception {
        // FALLO: Lanza un error imprevisto si se ejecuta el programa sin argumentos.
        String texto = args.length > 1 ? args[1] : "Ana";
        switch (args[0]) {
            case "1" -> saludar(texto);
            case "2" -> contarNumeros();
            case "3" -> ordenar();
            case "4" -> mostrar(texto);
        }
    }
}