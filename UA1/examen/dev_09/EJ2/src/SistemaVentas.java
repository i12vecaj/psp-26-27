
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

class Taquilla extends Thread {

    private final int entradas;
    private int vendidas;

    public Taquilla(String nombre, int entradas) {
        super(nombre);
        this.entradas = entradas;
        this.vendidas = 0;
    }

    @Override
    public void run() {

        int contador = 0;

        for (int i = 0; i < entradas; i++) {

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            contador++;
        }

        vendidas = contador;
    }

    public int getVendidas() {
        return vendidas;
    }
}

public class SistemaVentas {

    public static void main(String[] args) throws Exception {

        int aforo = 200;

        int[] cupos = {20, 30, 40, 50, 60};

        Taquilla[] taquillas = new Taquilla[5];

        // Crear las cinco taquillas.
        for (int i = 0; i < taquillas.length; i++) {

            taquillas[i] = new Taquilla(
                    "Taquilla " + (i + 1),
                    cupos[i]
            );
        }

        // Medir el tiempo de las ventas.
        long inicio = System.nanoTime();

        // Iniciar todos los hilos.
        for (int i = 0; i < taquillas.length; i++) {
            taquillas[i].start();
        }

        // Esperar a que terminen todos los hilos.
        for (int i = 0; i < taquillas.length; i++) {
            taquillas[i].join();
        }

        long fin = System.nanoTime();

        long tiempoVentas = TimeUnit.NANOSECONDS.toMillis(
                fin - inicio
        );

        // Recoger los resultados.
        int totalVendido = 0;

        for (int i = 0; i < taquillas.length; i++) {

            int vendidas = taquillas[i].getVendidas();

            System.out.println(
                    "Taquilla " + (i + 1) + ": " + vendidas + " entradas"
            );

            totalVendido += vendidas;
        }

        System.out.println(
                "Total vendido: " + totalVendido + " de " + aforo
        );

        System.out.println(
                "Tiempo de ventas: " + tiempoVentas + " ms"
        );

        // Crear el proceso independiente Auditor.
        String java = Path.of(
                System.getProperty("java.home"),
                "bin",
                System.getProperty("os.name").toLowerCase().contains("win")
                        ? "java.exe"
                        : "java"
        ).toString();

        ProcessBuilder constructor = new ProcessBuilder(
                java,
                "-cp",
                System.getProperty("java.class.path"),
                "Auditor"
        );

        constructor.redirectErrorStream(true);

        Process proceso = constructor.start();

        // Enviar el aforo y el total vendido al Auditor.
        BufferedWriter entrada = new BufferedWriter(
                new OutputStreamWriter(
                        proceso.getOutputStream(),
                        StandardCharsets.UTF_8
                )
        );

        entrada.write(String.valueOf(aforo));
        entrada.newLine();

        entrada.write(String.valueOf(totalVendido));
        entrada.newLine();

        entrada.close();

        // Leer la respuesta del Auditor.
        BufferedReader salida = new BufferedReader(
                new InputStreamReader(
                        proceso.getInputStream(),
                        StandardCharsets.UTF_8
                )
        );

        String respuesta;

        while ((respuesta = salida.readLine()) != null) {
            System.out.println(respuesta);
        }

        salida.close();

        // Esperar a que termine el proceso y recoger su código.
        int codigo = proceso.waitFor();

        System.out.println("Codigo del auditor: " + codigo);
    }
}
