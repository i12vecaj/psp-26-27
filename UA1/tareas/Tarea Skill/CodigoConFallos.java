import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CodigoConFallos {

    // Caso 1: saluda al nombre indicado.
    static void saludar(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            nombre = "invitado";
        }
        System.out.println("Hola " + nombre);
    }

    // Caso 2: cuenta los números del 1 al 1 000 000.
    static void contarNumeros() {
        long cantidad = 1_000_000L;
        System.out.println("Números generados: " + cantidad);
    }

    // Caso 3: ordena palabras alfabéticamente.
    static void ordenar() {
        List<String> palabras = new ArrayList<>(
                List.of("pera", "manzana", "banana")
        );
        palabras.sort(String.CASE_INSENSITIVE_ORDER);
        palabras.forEach(System.out::println);
    }

    // Caso 4: muestra el contenido de un fichero.
    static void mostrar(String nombreFichero) {
        try {
            Path fichero = Path.of(nombreFichero);

            if (!Files.isRegularFile(fichero) || !Files.isReadable(fichero)) {
                System.err.println("Error: el fichero no existe o no se puede leer.");
                return;
            }

            Files.lines(fichero, StandardCharsets.UTF_8)
                    .forEach(System.out::println);

            System.out.println("Fichero mostrado correctamente");
        } catch (InvalidPathException e) {
            System.err.println("Error: la ruta del fichero no es válida.");
        } catch (IOException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Uso: java CodigoConFallos <1|2|3|4> [texto o fichero]");
            return;
        }

        String texto = args.length > 1 ? args[1] : "Ana";

        try {
            switch (args[0]) {
                case "1" -> saludar(texto);
                case "2" -> contarNumeros();
                case "3" -> ordenar();
                case "4" -> mostrar(texto);
                default -> System.err.println(
                        "Opción no válida. Usa 1, 2, 3 o 4."
                );
            }
        } catch (RuntimeException e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}