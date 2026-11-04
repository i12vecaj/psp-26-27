
import java.io.IOException;

public class SistemaVentas {

    public static void main(String[] args) {

        // Creamos las cinco taquillas con sus respectivos cupos
        Taquilla[] taquillas = {
                new Taquilla(1, 20),
                new Taquilla(2, 30),
                new Taquilla(3, 40),
                new Taquilla(4, 50),
                new Taquilla(5, 60)
        };

        // Medimos el tiempo desde que empiezan las ventas
        long inicio = System.currentTimeMillis();

        // Iniciamos todas las taquillas
        for (Taquilla taquilla : taquillas) {
            taquilla.start();
        }

        // Esperamos a que terminen todas las taquillas
        try {
            for (Taquilla taquilla : taquillas) {
                taquilla.join();
            }
        } catch (InterruptedException e) {
            System.err.println("El sistema de ventas ha sido interrumpido");
            Thread.currentThread().interrupt();
            return;
        }

        // Calculamos el tiempo total de las ventas
        long tiempo = System.currentTimeMillis() - inicio;

        // Sumamos las entradas vendidas
        int totalVendido = 0;

        for (Taquilla taquilla : taquillas) {
            System.out.println("Taquilla " + taquilla.getNumero()
                    + ": " + taquilla.getVendidas() + " entradas");

            totalVendido += taquilla.getVendidas();
        }

        System.out.println("Total vendido: " + totalVendido + " de 200");
        System.out.println("Tiempo de ventas: " + tiempo + " ms");

        // Creamos el proceso independiente Auditor
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "-cp",
                    System.getProperty("java.class.path"),
                    "Auditor"
            );

            Process proceso = pb.start();

            // Enviamos el aforo y el total vendido al Auditor
            java.io.OutputStreamWriter escritor =
                    new java.io.OutputStreamWriter(proceso.getOutputStream());

            escritor.write("200\n");
            escritor.write(totalVendido + "\n");
            escritor.close();

            // Leemos la respuesta del Auditor
            java.io.BufferedReader lector =
                    new java.io.BufferedReader(
                            new java.io.InputStreamReader(proceso.getInputStream()));

            String respuesta = lector.readLine();

            if (respuesta != null) {
                System.out.println(respuesta);
            }

            lector.close();

            // Esperamos a que termine el proceso y obtenemos su código
            int codigo = proceso.waitFor();

            System.out.println("Codigo del auditor: " + codigo);

        } catch (IOException e) {
            System.err.println("Error al ejecutar el Auditor");
        } catch (InterruptedException e) {
            System.err.println("Se ha interrumpido el proceso Auditor");
            Thread.currentThread().interrupt();
        }
    }
}

// Cada taquilla trabaja en su propio hilo
class Taquilla extends Thread {

    private final int numero;
    private final int cupo;
    private int vendidas;

    public Taquilla(int numero, int cupo) {
        this.numero = numero;
        this.cupo = cupo;
        this.vendidas = 0;
    }

    @Override
    public void run() {

        // Vendemos las entradas de una en una
        for (int i = 0; i < cupo; i++) {
            try {
                Thread.sleep(5);
                vendidas++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public int getNumero() {
        return numero;
    }

    public int getVendidas() {
        return vendidas;
    }
}
