import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void main() throws IOException {

        // Inicio scanner y variables varias, como el boolean para salir del while...
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        List<String> lista = new ArrayList<>();
        String dato;

        // FR1: lea una cadena de caracteres desde la entrada estándar hasta recibir un carácter
        // de terminación, en concreto, un asterisco * - 2 puntos

        // Ejecuto el bucle do while, en el que se busca un string que sea *
        do {
            dato = sc.next();
            if (dato.equals("*")) {
                flag = false;
            } else {
                lista.add(dato);
            }
        }while (flag);

        // FR2: una vez recibido el caracter de terminación,
        // muestre por pantalla toda la información leída - 2 puntos

        // Imprimo por pantalla
        lista.forEach(System.out::println);
    }
}
