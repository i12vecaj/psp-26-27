### Plantilla orientativa

```java
// Taquilla.java
public class Taquilla implements Runnable {
    private static final int MILISEGUNDOS_POR_VENTA = 5;
    private final int cupo;
    private int vendidas;

    public Taquilla(int cupo) { this.cupo = cupo; }

    @Override
    public void run() {
        for (int i = 0; i < cupo; i++) {
            try {
                Thread.sleep(MILISEGUNDOS_POR_VENTA);
                vendidas++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Venta interrumpida");
                return;
            }
        }
    }

    public int getVendidas() { return vendidas; }
}
```

```java
// Teatro.java
import java.io.*;

public class Teatro {
    private static final int AFORO = 200;

    public static void main(String[] args) {
        int[] cupos = {20, 30, 40, 50, 60};
        Taquilla[] taquillas = new Taquilla[cupos.length];
        Thread[] hilos = new Thread[cupos.length];
        for (int i = 0; i < cupos.length; i++) {
            taquillas[i] = new Taquilla(cupos[i]);
            hilos[i] = new Thread(taquillas[i], "Taquilla-" + (i + 1));
        }

        long inicio = System.nanoTime();
        for (Thread hilo : hilos) hilo.start();
        try {
            for (Thread hilo : hilos) hilo.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Espera interrumpida");
            return;
        }
        long fin = System.nanoTime();

        int total = 0;
        for (int i = 0; i < taquillas.length; i++) {
            System.out.println("Taquilla " + (i + 1) + ": " + taquillas[i].getVendidas());
            total += taquillas[i].getVendidas();
        }
        System.out.println("Total vendido: " + total);
        System.out.println("Tiempo: " + (fin - inicio) / 1_000_000 + " ms");

        try {
            ProcessBuilder pb = new ProcessBuilder("java", "Auditor");
            pb.redirectErrorStream(true);
            Process auditor = pb.start();
            try (BufferedWriter escritor = new BufferedWriter(
                    new OutputStreamWriter(auditor.getOutputStream()))) {
                escritor.write(String.valueOf(AFORO));
                escritor.newLine();
                escritor.write(String.valueOf(total));
                escritor.newLine();
                escritor.flush();
            }
            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(auditor.getInputStream()))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    System.out.println("Auditor: " + linea);
                }
            }
            int codigo = auditor.waitFor();
            System.out.println("Código de salida del Auditor: " + codigo);
        } catch (IOException e) {
            System.err.println("Error al ejecutar el Auditor: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Espera del Auditor interrumpida");
        }
    }
}
```

```java
// Auditor.java
import java.io.*;

public class Auditor {
    public static void main(String[] args) {
        int codigo;
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(System.in))) {
            String lineaAforo = lector.readLine();
            String lineaTotal = lector.readLine();
            if (lineaAforo == null || lineaTotal == null) {
                codigo = 3;
            } else {
                int aforo = Integer.parseInt(lineaAforo.trim());
                int total = Integer.parseInt(lineaTotal.trim());
                if (aforo < 0 || total < 0) codigo = 3;
                else if (total == aforo) codigo = 0;
                else if (total > aforo) codigo = 1;
                else codigo = 2;
            }
        } catch (NumberFormatException | IOException e) {
            codigo = 3;
        }
        System.out.println("Resultado de la auditoría: código " + codigo);
        System.exit(codigo);
    }
}
```

Compilar y ejecutar (desde la misma carpeta, para que `java Auditor` encuentre `Auditor.class`):

```
javac Taquilla.java Teatro.java Auditor.java
java Teatro
```