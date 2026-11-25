package UA1.tareas.Tarea3;
import java.io.*;


public class codigoErrores {

        // Caso 1: saluda al nombre que escribe el usuario (Corregido: Inyección de comandos)
        static void saludar(String nombre) throws Exception {
            // Se pasan los argumentos por separado, eliminando 'sh -c'
            Process p = new ProcessBuilder("echo", "Hola", nombre).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            while ((linea = br.readLine()) != null) System.out.println(linea);
        }

        // Caso 2: cuenta cuántos números genera "seq" (Corregido: Bloqueo por waitFor)
        static void contarNumeros() throws Exception {
            Process p = new ProcessBuilder("seq", "1", "1000000").start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int n = 0;

            // Primero se lee toda la salida para vaciar el buffer
            while (br.readLine() != null) n++;

            // Después se espera a que el proceso termine
            p.waitFor();
            System.out.println("Números generados: " + n);
        }

        // Caso 3: ordena unas palabras con el comando "sort" (Corregido: Cierre de flujo)
        static void ordenar() throws Exception {
            Process p = new ProcessBuilder("sort").start();
            p.getOutputStream().write("pera\nmanzana\nbanana\n".getBytes());
            p.getOutputStream().flush();

            // Es vital cerrar el flujo para enviar la señal EOF (Fin de archivo) a 'sort'
            p.getOutputStream().close();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            while ((linea = br.readLine()) != null) System.out.println(linea);
        }

        // Caso 4: muestra el contenido de un fichero (Corregido: Validación de errores)
        static void mostrar(String fichero) throws Exception {
            ProcessBuilder pb = new ProcessBuilder("cat", fichero);
            // Redirigimos la salida de error para poder leerla en caso de fallo
            pb.redirectErrorStream(true);
            Process p = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            while ((linea = br.readLine()) != null) System.out.println(linea);

            // Comprobamos el código de salida
            int exitCode = p.waitFor();
            if (exitCode == 0) {
                System.out.println("Fichero mostrado correctamente");
            } else {
                System.out.println("Error al mostrar el fichero. Código de salida: " + exitCode);
            }
        }

        public static void main(String[] args) throws Exception {
            // Corregido: Prevención de ArrayIndexOutOfBoundsException si no hay argumentos
            if (args.length == 0) {
                System.out.println("Por favor, indica un caso a ejecutar (1, 2, 3 o 4).");
                return;
            }

            String texto = args.length > 1 ? args[1] : "Ana";
            switch (args[0]) {
                case "1" -> saludar(texto);
                case "2" -> contarNumeros();
                case "3" -> ordenar();
                case "4" -> mostrar(texto);
                default -> System.out.println("Caso no válido.");
            }
        }
    }

