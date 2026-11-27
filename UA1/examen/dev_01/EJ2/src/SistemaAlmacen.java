
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
//em este codigo lo mas importante son los dos bucles for que estan separados para que primero uno inicie y el otro espera a que acabe
public class SistemaAlmacen {

    public static void main(String[] args) {
        try {
            Operario[] operarios = {
                    new Operario(10),
                    new Operario(20),
                    new Operario(30),
                    new Operario(40),
                    new Operario(50)
            };

            int total = 0;

            // Guardamos el tiempo de inicio
            long inicio = System.currentTimeMillis();

            // Iniciamos todos los hilos
            for (Operario operario : operarios) {
                operario.start();
            }

            // Esperamos a que terminen todos los hilos
            for (Operario operario : operarios) {
                operario.join();
            }

            long fin = System.currentTimeMillis();

            // Mostramos los pedidos de cada operario
            for (int i = 0; i < operarios.length; i++) {
                int pedidos = operarios[i].getPedidosPreparados();
                total += pedidos;

                System.out.println("Operario " + (i + 1) + ": " + pedidos + " pedidos");
            }

            System.out.println("Total preparado: " + total + " de 150");
            System.out.println("Tiempo de preparacion: " + (fin - inicio) + " ms");

            // Creamos el proceso Inspector
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp",
                    System.getProperty("java.class.path"),
                    "Inspector"
            );

            Process proceso = pb.start();

            // Enviamos los datos al Inspector
            PrintWriter escritor = new PrintWriter(proceso.getOutputStream());
            escritor.println(150);
            escritor.println(total);
            escritor.close();

            // Leemos la respuesta del Inspector
            BufferedReader lector = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream())
            );

            System.out.println(lector.readLine());
            lector.close();

            // Esperamos al proceso y mostramos su codigo
            int codigo = proceso.waitFor();
            System.out.println("Codigo del inspector: " + codigo);

        } catch (IOException e) {
            System.err.println("Error al ejecutar el proceso");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Se ha interrumpido el programa");
        }
    }
}
