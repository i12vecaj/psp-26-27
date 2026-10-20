import java.io.*;

/**
  * CÓDIGO CON FALLOS A PROPÓSITO (para revisar en clase).
  * Uso: java CodigoConFallos.java <1|2|3|4> [texto]
  */
public class CodigoConFallos {

 // Caso 1: saluda al nombre que escribe el usuario
        static void saludar(String nombre) throws Exception {
            // Error Nº1: Concatenar la entrada del usuario directamente dentro de un comando de shell ("echo Hola " + nombre)
 String comando = "echo Hola " + nombre;
            // usando sh -c.
 Process p = new ProcessBuilder("sh", "-c", comando).start();

 BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
 String linea;
 while ((linea = br.readLine()) != null) System.out.println(linea);
 }

 // Caso 2: cuenta cuántos números genera "seq"
         static void contarNumeros() throws Exception {
 Process p = new ProcessBuilder("seq", "1", "1000000").start();
 // Error Nº2: Hacer p.waitFor() antes de leer el buffer de salida (getInputStream()).
 p.waitFor();
 BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
 int n = 0;
 while (br.readLine() != null) n++;
 System.out.println("Números generados: " + n);
 }

 // Caso 3: ordena unas palabras con el comando "sort"
         static void ordenar() throws Exception {
 Process p = new ProcessBuilder("sort").start();
 // Error Nº3: No se cierra el canal de salida del proceso (p.getOutputStream().close()).
 p.getOutputStream().write("pera\nmanzana\nbanana\n".getBytes());
 p.getOutputStream().flush();
 BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
 String linea;
 while ((linea = br.readLine()) != null) System.out.println(linea);
 }

 // Caso 4: muestra el contenido de un fichero
         static void mostrar(String fichero) throws Exception {
 Process p = new ProcessBuilder("cat", fichero).start();
 // Error Nº4: Solo se lee la salida estándar (getInputStream()), ignorando el canal de errores (getErrorStream()).
 BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
 String linea;
 //  Errir Nº4: Imprime "Fichero mostrado correctamente" sin comprobar si el proceso finalizó con éxito o si el fichero realmente existía.
 while ((linea = br.readLine()) != null) System.out.println(linea);
 p.waitFor();
 System.out.println("Fichero mostrado correctamente");
 }

public static void main(String[] args) throws Exception {
            // Error Nº5: No se verifica si args tiene al menos un elemento antes de acceder a args[0].
 String texto = args.length > 1 ? args[1] : "Ana";
 switch (args[0]) {
 case "1" -> saludar(texto);
case "2" -> contarNumeros();
 case "3" -> ordenar();
 case "4" -> mostrar(texto);
 }
 }
}