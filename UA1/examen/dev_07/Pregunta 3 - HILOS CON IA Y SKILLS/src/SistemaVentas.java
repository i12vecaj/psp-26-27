import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SistemaVentas {

    static class Taquilla extends Thread {

        private final int entradas;
        private int vendidas;

        public Taquilla(int entradas) {
            this.entradas = entradas;
        }

        @Override
        public void run() {
            for (int i = 0; i < entradas; i++) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    interrupt();
                    return;
                }
                vendidas++;
            }
        }

        public int getVendidas() {
            return vendidas;
        }
    }

    public static void main(String[] args) throws Exception {

        Taquilla[] taquillas = {
                new Taquilla(20),
                new Taquilla(30),
                new Taquilla(40),
                new Taquilla(50),
                new Taquilla(60)
        };

        long inicio = System.currentTimeMillis();

        for (Taquilla t : taquillas) {
            t.start();
        }

        for (Taquilla t : taquillas) {
            t.join();
        }

        long fin = System.currentTimeMillis();

        int total = 0;

        for (int i = 0; i < taquillas.length; i++) {
            int vendidas = taquillas[i].getVendidas();
            total += vendidas;
            System.out.println("Taquilla " + (i + 1) + ": " + vendidas + " entradas");
        }

        System.out.println("Total vendido: " + total + " de 200");
        System.out.println("Tiempo de ventas: " + (fin - inicio) + " ms");

        Process proceso = new ProcessBuilder("java", "Auditor").start();

        PrintWriter escritor = new PrintWriter(proceso.getOutputStream(), true);
        escritor.println(200);
        escritor.println(total);
        escritor.close();

        BufferedReader lector =
                new BufferedReader(new InputStreamReader(proceso.getInputStream()));

        String respuesta = lector.readLine();

        int codigo = proceso.waitFor();

        System.out.println(respuesta);
        System.out.println("Codigo del auditor: " + codigo);
    }
}
