import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Procesos {
    public static void main(String[] args) {
        // Creo una lista y genero con scanner una posbilidad de escribir por pantalla
        List<String> lista = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        lista.add(sc.nextLine());
        // Inicio una variable numero por si tengo que usarla posteriormente para validaciones.
        int num = 0;
        // Si la lista esta vacia (O tiene un espacio) se remueve el primer objeto.
        if (lista.getFirst().isBlank()) {
            lista.removeFirst();
        } else {
            // Si no se intenta parsear a un numero entero, en caso error deducimos que es una cadena
            try {
                num = Integer.parseInt(lista.getFirst());
            } catch (Exception e) {
                System.exit(2);
            }

        }
        // Si pasa el filtro anterior buscaremos saber si esta vacia (Que su longitud sea menor que uno)
        // En el segundo if buscaremos si es un numero y menor que 0
        // En el else final comprenderemos que si no ha pasado ningun de los otros filtros es un numero positivo
        if (lista.size() < 1) {
            System.exit(1);
        } else if (num < 0) {
            System.exit(3);
        } else {
            System.exit(0);
        }
    }
}


/**
 * import java.util.ArrayList;
 * import java.util.List;
 * import java.util.Scanner;
 *
 * public class Procesos {
 *     public static void main(String[] args) {
 *         if (args.length < 1) {
 *             System.exit(1);
 *         }
 *         int num = Integer.parseInt(args.toString());
 *         if (num <= 0) {
 *             System.exit(3);
 *         } else {
 *             System.exit(0);
 *         }
 *     }
 * }
 */