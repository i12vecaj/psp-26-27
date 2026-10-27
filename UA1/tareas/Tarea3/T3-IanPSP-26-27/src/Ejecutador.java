import java.io.IOException;

public class Ejecutador {
    static void main() {
        // Obtiene la ruta actual de las clases compiladas
        String classPath = System.getProperty("java.class.path");
        // Prepara la ejecución de la clase "Procesos" en un proceso aparte
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", classPath, "Procesos");
        pb.inheritIO(); // Conecta la consola de este programa con la del nuevo proceso

        try {
            System.out.println("Iniciando programa");

            Process proceso = pb.start(); // Inicia el proceso de la clase Procesos
            int exitCode = proceso.waitFor(); // Espera a que termine y guarda cómo ha salido
            // Comprueba el código de salida devuelto por "Procesos"
            switch (exitCode) {
                case 1: {
                    System.out.println("No has escrito nada");
                    break;
                }
                case 2: {
                    System.out.println("Has introducido caracteres");
                    break;
                }
                case 3: {
                    System.out.println("Es un numero menor que 0");
                    break;
                }
                case 0: {
                    System.out.println("Otro"); // Número positivo válido
                    break;
                }
            }
            System.out.println("Programa finalizado con salida: " + exitCode);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}