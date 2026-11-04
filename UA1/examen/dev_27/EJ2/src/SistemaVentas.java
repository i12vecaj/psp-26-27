public class SistemaVentas {

    public static void main(String[] args) {
        int[] cupos = {20, 30, 40, 50, 60};
        Taquilla[] taquillas = new Taquilla[cupos.length];

        for (int i = 0; i < cupos.length; i++) {
            taquillas[i] = new Taquilla(i + 1, cupos[i]);
        }

        long inicio = System.currentTimeMillis();

        for (Taquilla taquilla : taquillas) {
            taquilla.start();
        }

        try {
            for (Taquilla taquilla : taquillas) {
                taquilla.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Se interrumpio la espera de las taquillas.");
            return;
        }

        long tiempo = System.currentTimeMillis() - inicio;
        int total = 0;

        for (Taquilla taquilla : taquillas) {
            System.out.println("Taquilla " + taquilla.getNumero()
                    + ": " + taquilla.getVendidas() + " entradas");
            total += taquilla.getVendidas();
        }

        System.out.println("Total vendido: " + total + " de 200");
        System.out.println("Tiempo de ventas: " + tiempo + " ms");

        ejecutarAuditor(200, total);
    }

    private static void ejecutarAuditor(int aforo, int total) {
        try {
            String javaExec = System.getProperty("java.home")
                    + java.io.File.separator + "bin"
                    + java.io.File.separator + "java";

            ProcessBuilder pb = new ProcessBuilder(
                    javaExec, "-cp", System.getProperty("java.class.path"), "Auditor");
            Process proceso = pb.start();

            try (java.io.BufferedWriter entrada = new java.io.BufferedWriter(
                    new java.io.OutputStreamWriter(proceso.getOutputStream()))) {
                entrada.write(String.valueOf(aforo));
                entrada.newLine();
                entrada.write(String.valueOf(total));
                entrada.newLine();
            }

            try (java.io.BufferedReader salida = new java.io.BufferedReader(
                    new java.io.InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = salida.readLine()) != null) {
                    System.out.println(linea);
                }
            }

            int codigo = proceso.waitFor();
            System.out.println("Codigo del auditor: " + codigo);

        } catch (Exception e) {
            System.out.println("Error al ejecutar el Auditor: " + e.getMessage());
            System.out.println("Codigo del auditor: 3");
        }
    }
}

class Taquilla extends Thread {
    private final int numero;
    private final int cupo;
    private int vendidas;

    public Taquilla(int numero, int cupo) {
        this.numero = numero;
        this.cupo = cupo;
    }

    @Override
    public void run() {
        int contador = 0;

        for (int i = 0; i < cupo; i++) {
            try {
                Thread.sleep(5);
                contador++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        vendidas = contador;
    }

    public int getNumero() {
        return numero;
    }

    public int getVendidas() {
        return vendidas;
    }
}
