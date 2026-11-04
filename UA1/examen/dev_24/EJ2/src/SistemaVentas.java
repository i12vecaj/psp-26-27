import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * SistemaVentas - Clase orquestadora principal del sistema de ventas concurrente.
 * 
 * Cumple estrictamente con las directrices de la skill 'hilos-procesos-java':
 * - Regla 1: Invocacion limpia mediante ProcessBuilder.
 * - Regla 2: Gestion estricta de streams IPC con flush() y close().
 * - Regla 3: Sincronizacion y lectura de exit code mediante process.waitFor().
 * - Regla 4: Aislamiento total de estado sin variables mutables compartidas.
 * - Regla 5: Lanzamiento no bloqueante con start() previo a cualquier join().
 * - Regla 6: Barrera de sincronizacion con join() para consolidacion de resultados.
 * - Regla 7: Captura explicita y preservacion del estado de interrupcion.
 * - Regla 8: Java 17 estandar, paquete por defecto y sin dependencias externas.
 */
public class SistemaVentas {

    private static final int AFORO_TOTAL = 200;
    private static final int[] CUPOS_TAQUILLAS = {20, 30, 40, 50, 60};

    public static void main(String[] args) {
        List<Taquilla> taquillas = new ArrayList<>();

        for (int i = 0; i < CUPOS_TAQUILLAS.length; i++) {
            taquillas.add(new Taquilla(i + 1, CUPOS_TAQUILLAS[i]));
        }

        long tiempoInicio = System.currentTimeMillis();

        // Regla 5: Arrancar el lote completo de hilos antes de cualquier espera bloqueante
        for (Taquilla taquilla : taquillas) {
            taquilla.start();
        }

        // Regla 6: Sincronizacion por barrera (join) para garantizar la finalizacion de todos los hilos
        for (Taquilla taquilla : taquillas) {
            try {
                taquilla.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Hilo principal interrumpido durante la espera: " + e.getMessage());
            }
        }

        long tiempoFin = System.currentTimeMillis();
        long tiempoTotalMs = tiempoFin - tiempoInicio;

        // Regla 4: Recoleccion de metricas desde el estado privado de cada instancia
        int totalVendido = 0;
        for (Taquilla taquilla : taquillas) {
            int vendidas = taquilla.getEntradasVendidas();
            totalVendido += vendidas;
            System.out.println("Taquilla " + taquilla.getIdTaquilla() + ": " + vendidas + " entradas");
        }

        // Formato exacto requerido
        System.out.println("Total vendido: " + totalVendido + " de " + AFORO_TOTAL);
        System.out.println("Tiempo de ventas: " + tiempoTotalMs + " ms");

        // Reglas 1, 2 y 3: Invocacion IPC del proceso Auditor mediante ProcessBuilder
        ejecutarAuditorIPC(AFORO_TOTAL, totalVendido);
    }

    private static void ejecutarAuditorIPC(int aforo, int totalVendido) {
        String classpath = System.getProperty("java.class.path");
        
        // Determinar ejecutable java manteniendo portabilidad con el entorno
        String javaCmd = "java";
        String javaHome = System.getProperty("java.home");
        if (javaHome != null && !javaHome.isEmpty()) {
            File javaExe = new File(javaHome, "bin" + File.separator + "java.exe");
            File javaBin = new File(javaHome, "bin" + File.separator + "java");
            if (javaExe.exists()) {
                javaCmd = javaExe.getAbsolutePath();
            } else if (javaBin.exists()) {
                javaCmd = javaBin.getAbsolutePath();
            }
        }

        ProcessBuilder pb = new ProcessBuilder(javaCmd, "-cp", classpath, "Auditor");

        try {
            Process procesoAuditor = pb.start();

            // Regla 2: Escritura en OutputStream, vaciado con flush() y cierre para senalizar EOF
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(procesoAuditor.getOutputStream(), StandardCharsets.UTF_8))) {
                writer.write(String.valueOf(aforo));
                writer.newLine();
                writer.write(String.valueOf(totalVendido));
                writer.newLine();
                writer.flush();
            }

            // Regla 2: Lectura completa del InputStream del proceso subordinado
            String salidaAuditor = "";
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(procesoAuditor.getInputStream(), StandardCharsets.UTF_8))) {
                String linea;
                StringBuilder sb = new StringBuilder();
                while ((linea = reader.readLine()) != null) {
                    if (sb.length() > 0) {
                        sb.append(System.lineSeparator());
                    }
                    sb.append(linea);
                }
                salidaAuditor = sb.toString();
            }

            // Regla 3: Sincronizacion y extraccion del codigo de retorno mediante waitFor()
            int exitCode = procesoAuditor.waitFor();

            System.out.println(salidaAuditor);
            System.out.println("Codigo del auditor: " + exitCode);

        } catch (IOException e) {
            System.err.println("Error de comunicacion IPC con el proceso Auditor: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupcion durante la ejecucion del proceso Auditor: " + e.getMessage());
        }
    }
}

