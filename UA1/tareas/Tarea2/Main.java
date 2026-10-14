import java.util.Scanner;


public class Main {

    static void main() {
        // Almacena la última línea introducida por el usuario
        String caracter = "";

        // Inicializa el lector para capturar texto desde la entrada estándar (teclado)
        Scanner sc = new Scanner(System.in);

        // Creamos el StringBuilder
        StringBuilder sb = new StringBuilder();

        // El bucle continúa ejecutándose mientras la línea leída no sea exactamente un asterisco "*"
        while (!caracter.equals("*")){
            // Lee una línea completa de la consola
            caracter = sc.nextLine();

            // Añade la línea leída al acumulador de texto (incluyendo el asterisco final)
            sb.append(caracter);
        }

        // Muestra por pantalla todo el texto acumulado durante la ejecución
        System.out.println(sb);
    }
}