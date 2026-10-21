package UA1.tareas.Tarea3;

import java.io.*;
import java.util.Scanner;

public class codigoErrores {

    // Caso 1: saluda al nombre que escribe el usuario
    static void saludar(String nombre) throws Exception {
        Process p = new ProcessBuilder("echo", "Hola", nombre).start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);
    }

    // Caso 2: cuenta cuántos números genera "seq"
    static void contarNumeros() throws Exception {
        Process p = new ProcessBuilder("seq", "1", "1000000").start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        int n = 0;
        while (br.readLine() != null) n++;
        p.waitFor();
        System.out.println("Números generados: " + n);
    }

    // Caso 3: ordena unas palabras con el comando "sort"
    static void ordenar() throws Exception {
        Process p = new ProcessBuilder("sort").start();
        p.getOutputStream().write("pera\nmanzana\nbanana\n".getBytes());
        p.getOutputStream().flush();
        p.getOutputStream().close();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);
    }

    // Caso 4: muestra el contenido de un fichero
    static void mostrar(String fichero) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("cat", fichero);
        pb.redirectErrorStream(true);
        Process p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String linea;
        while ((linea = br.readLine()) != null) System.out.println(linea);

        int exitCode = p.waitFor();
        if (exitCode == 0) {
            System.out.println("Fichero mostrado correctamente");
        } else {
            System.out.println("Error al mostrar el fichero. Código de salida: " + exitCode);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        // Bucle para mantener el menú abierto hasta que el usuario decida salir
        while (!salir) {
            System.out.println("\n=== MENÚ INTERACTIVO DE PROCESOS ===");
            System.out.println("1. Saludar a un usuario");
            System.out.println("2. Contar números generados por 'seq'");
            System.out.println("3. Ordenar palabras con 'sort'");
            System.out.println("4. Mostrar el contenido de un fichero");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            String opcion = scanner.nextLine();

            try {
                switch (opcion) {
                    case "1" -> {
                        System.out.print("Introduce el nombre para saludar (pulsa Enter para 'Ana'): ");
                        String nombre = scanner.nextLine();
                        saludar(nombre.isEmpty() ? "Ana" : nombre);
                    }
                    case "2" -> contarNumeros();
                    case "3" -> ordenar();
                    case "4" -> {
                        System.out.print("Introduce solo el nombre del fichero a leer (ejemplo: documento.txt): ");
                        String nombreFichero = scanner.nextLine();

                        // El programa añade la ruta de las carpetas automáticamente
                        String rutaCompleta = "UA1/tareas/Tarea3/" + nombreFichero;

                        System.out.println("Buscando en: " + rutaCompleta);
                        mostrar(rutaCompleta);
                    }
                    case "5" -> {
                        salir = true;
                        System.out.println("Saliendo del programa...");
                    }
                    default -> System.out.println("Caso no válido. Por favor, elige un número del 1 al 5.");
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error en la ejecución: " + e.getMessage());
            }
        }
        scanner.close();
    }
}