import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Procesos {
    static void main() {
        List<String> lista = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        // Lee lo que escribe el usuario por consola y lo guarda
        lista.add(sc.nextLine());
        int numero = 0;
        // Si el usuario le dio a "Enter" sin escribir nada, borra ese elemento
        if (lista.getFirst().isBlank()) {
            lista.removeFirst();
        } else {
            try {
                // Intenta convertir el texto introducido a un número
                numero = Integer.parseInt(lista.getFirst());
            } catch (Exception e) {
                // Si falla (porque ha escrito letras), sale del programa con código 2
                System.exit(2);
            }
        }
        // Evalúa la lista y el número para salir con el código que corresponda
        if (lista.isEmpty()) { // Antes era lista.size() < 1
            System.exit(1); // Código 1: Estaba vacío
        } else if (numero < 0) {
            System.exit(3); // Código 3: Escribió un número negativo
        } else {
            System.exit(0); // Código 0: Finalización normal (número positivo)
        }
    }
}
