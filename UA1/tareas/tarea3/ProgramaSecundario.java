import java.io.IOException;

public class ProgramaSecundario {

    public static void main(String[] args) {

        // Comprueba que se haya indicado el argumento para el programa principal.
        if (args.length < 1) {
            System.out.println("Error: debes indicar un argumento.");
            System.exit(1);
        }

        try {
            // Prepara la ejecución del programa principal.
            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "ProgramaPrincipal",
                    args[0]
            );

            // Ejecuta el programa principal.
            Process proceso = pb.start();

            // Espera a que termine y obtiene su código de salida.
            int codigo = proceso.waitFor();

            // Muestra el resultado según el código devuelto.
            switch (codigo) {
                case 0:
                    System.out.println("Correcto: el argumento es un entero no negativo.");
                    break;
                case 1:
                    System.out.println("Error: no se han introducido argumentos.");
                    break;
                case 2:
                    System.out.println("Error: el argumento no es un número entero.");
                    break;
                case 3:
                    System.out.println("Error: el número entero es negativo.");
                    break;
                default:
                    System.out.println("Error: código de salida desconocido.");
                    break;
            }

        } catch (IOException e) {
            System.out.println("Error al ejecutar el programa principal.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("La ejecución fue interrumpida.");
            Thread.currentThread().interrupt();
        }
    }
}