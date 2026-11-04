import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Padre {

    private static String getDescripcionCodigo(int codigo) {
        switch (codigo) {
            case 0:
                return "OK";
            case 1:
                return "falta argumento";
            case 2:
                return "no es un numero";
            case 3:
                return "numero negativo";
            default:
                return "error desconocido";
        }
    }

    public static void main(String[] args) {
        // Argumentos que pide el enunciado
        String[] argumentos = {"4", "6", "-2", "abc"};
        Process[] procesos = new Process[argumentos.length];
        String cp = System.getProperty("java.class.path");

        // FR2: Lanzamos los 4 a la vez antes de esperar a ninguno
        for (int i = 0; i < argumentos.length; i++) {
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", cp, "Hijo", argumentos[i]);
                procesos[i] = pb.start();
            } catch (Exception e) {
                System.out.println("Error al lanzar el hijo con arg: " + argumentos[i]);
            }
        }

        int sumaTotal = 0;

        // FR3 y FR4: Leemos la salida y esperamos a cada proceso
        for (int i = 0; i < procesos.length; i++) {
            int numHijo = i + 1;
            String arg = argumentos[i];
            int sumaHijo = 0;

            try {
                // Leer lo que escribe el hijo
                BufferedReader br = new BufferedReader(new InputStreamReader(procesos[i].getInputStream()));
                String linea;
                while ((linea = br.readLine()) != null) {
                    sumaHijo += Integer.parseInt(linea);
                }
                br.close();

                // Esperar a que acabe para pillar el código de salida
                int exitCode = procesos[i].waitFor();
                String explicacion = getDescripcionCodigo(exitCode);

                // Imprimir según el formato pedido
                if (exitCode == 0) {
                    System.out.println("Hijo " + numHijo + " (arg=" + arg + "): codigo " + exitCode + " -> " + explicacion + ", suma=" + sumaHijo);
                    sumaTotal += sumaHijo;
                } else {
                    System.out.println("Hijo " + numHijo + " (arg=" + arg + "): codigo " + exitCode + " -> " + explicacion);
                }

            } catch (Exception e) {
                System.out.println("Error procesando el hijo " + numHijo + ": " + e.getMessage());
            }
        }

        // FR3: Mostrar la suma total
        System.out.println("Suma total: " + sumaTotal);
    }
}
