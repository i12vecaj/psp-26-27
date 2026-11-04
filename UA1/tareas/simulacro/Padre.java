import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Padre {

    public static void main(String[] args) {

        String[] argumentos = {"4", "6", "-2", "abc"};

        List<Process> procesos = new ArrayList<>();

        int sumaTotal = 0;

        try {

            // FR2: Lanzar los cuatro procesos a la vez
            for (String argumento : argumentos) {

                ProcessBuilder pb = new ProcessBuilder(
                        "java",
                        "-cp",
                        System.getProperty("java.class.path"),
                        "Hijo",
                        argumento
                );

                procesos.add(pb.start());
            }

            // FR3 y FR4: Leer resultados y comprobar códigos
            for (int i = 0; i < procesos.size(); i++) {

                Process proceso = procesos.get(i);

                int sumaHijo = 0;

                BufferedReader lector = new BufferedReader(
                        new InputStreamReader(proceso.getInputStream())
                );

                String linea;

                // Leer los números que escribe el hijo
                while ((linea = lector.readLine()) != null) {
                    sumaHijo += Integer.parseInt(linea);
                }

                // Esperar a que termine el proceso
                int codigo = proceso.waitFor();

                String mensaje;

                switch (codigo) {
                    case 0:
                        mensaje = "OK";
                        break;
                    case 1:
                        mensaje = "falta argumento";
                        break;
                    case 2:
                        mensaje = "no es un numero";
                        break;
                    case 3:
                        mensaje = "numero negativo";
                        break;
                    default:
                        mensaje = "error desconocido";
                        break;
                }

                System.out.println(
                        "Hijo " + (i + 1) +
                        " (arg=" + argumentos[i] + "): codigo " +
                        codigo + " -> " + mensaje +
                        ", suma=" + sumaHijo
                );

                sumaTotal += sumaHijo;
            }

            // Mostrar suma total
            System.out.println("Suma total: " + sumaTotal);

        } catch (Exception e) {
            System.out.println("Error al ejecutar los procesos: " + e.getMessage());
        }
    }
}