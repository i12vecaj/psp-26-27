import java.io.*;

/**
 * CÓDIGO CON FALLOS A PROPÓSITO (para revisar en clase).
 */
public class CodigoConFallos {

    // Caso 1: saluda al nombre que escribe el usuario
    static void saludar(String nombre) throws Exception {
        // FALLO: inyección de comandos. Si nombre vale "x; rm -rf ~" el shell
        // ejecuta las dos cosas. Al meter "sh -c" todo lo que venga de fuera
        // pasa a ser código. Aquí no hace falta shell para nada: bastaría
        // new ProcessBuilder("echo", "Hola " + nombre) y se acabó el problema.
        String comando = "echo Hola " + nombre;
        Process p = new ProcessBuilder("sh", "-c", comando).start();
        // FALLO: InputStreamReader sin charset, usa el del sistema. Con acentos
        // en el nombre sale basura según la máquina.
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);
        // FALLO: no se cierra br ni se llama a waitFor(). El proceso queda
        // zombie y se van filtrando descriptores si esto se llama en bucle.
        // FALLO (en los 4 casos): nadie lee el stderr, así que los errores del
        // comando se pierden. O se usa redirectErrorStream(true) o se lee aparte.
    }

    // Caso 2: cuenta cuántos números genera "seq"
    static void contarNumeros() throws Exception {
        Process p = new ProcessBuilder("seq", "1", "1000000").start();
        // FALLO GORDO: waitFor() ANTES de leer la salida. El pipe tiene un buffer
        // de unos pocos KB; seq lo llena enseguida, se queda bloqueado esperando
        // que alguien lea, y nosotros esperamos a que termine. Deadlock, el
        // programa se cuelga para siempre. Hay que leer primero y esperar después.
        p.waitFor();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        int n = 0;
        while (br.readLine() != null) n++;
        // Con un "seq 1 10" parecería funcionar, y eso es lo traicionero del fallo.
        System.out.println("Números generados: " + n);
    }

    // Caso 3: ordena unas palabras con el comando "sort"
    static void ordenar() throws Exception {
        Process p = new ProcessBuilder("sort").start();
        // FALLO: getOutputStream() en cada línea devuelve el mismo stream, vale,
        // pero no se cierra nunca. sort no puede ordenar hasta recibir EOF en su
        // entrada, y el EOF solo llega al cerrar el stream. Con flush() no basta:
        // se queda esperando más palabras y el readLine() de abajo no devuelve nada.
        p.getOutputStream().write("pera\nmanzana\nbanana\n".getBytes());
        p.getOutputStream().flush();
        // FALLO: getBytes() sin charset, mismo problema que el caso 1.
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);
        // FALLO: tampoco hay waitFor() ni cierre de streams.
    }

    // Caso 4: muestra el contenido de un fichero
    static void mostrar(String fichero) throws Exception {
        Process p = new ProcessBuilder("cat", fichero).start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);
        // FALLO: se ignora el valor que devuelve waitFor(). Si el fichero no
        // existe, cat devuelve 1, no imprime nada y aun así mentimos diciendo
        // que todo fue bien. Habría que comprobar: if (p.waitFor() != 0) ...
        p.waitFor();
        System.out.println("Fichero mostrado correctamente");
        // FALLO: ruta sin validar. Pasando "../../etc/passwd" o cualquier ruta
        // absoluta se lee lo que sea; si esto fuera un servicio, es un path
        // traversal de libro.
    }

    public static void main(String[] args) throws Exception {
        // FALLO: se lee args[1] y args[0] sin comprobar antes args.length.
        // Ejecutar el programa sin argumentos revienta con
        // ArrayIndexOutOfBoundsException en el switch.
        String texto = args.length > 1 ? args[1] : "Ana";
        switch (args[0]) {
            case "1" -> saludar(texto);
            case "2" -> contarNumeros();
            case "3" -> ordenar();
            case "4" -> mostrar(texto);
            // FALLO: no hay default. Si se pasa "9" el programa termina sin
            // decir nada y el usuario no sabe si funcionó o no.
        }
        // FALLO general: main declara "throws Exception", así que cualquier
        // problema sale como traza de pila en la cara del usuario.
    }
}
