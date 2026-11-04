/*
 * SistemaVentas - PSP UA1, ejercicio 2
 * Juan Raigón Mínguez
 *
 * Abre 5 taquillas en paralelo, junta lo vendido y pide al proceso
 * Auditor que compruebe si cuadra con el aforo.
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class SistemaVentas {

    static final int AFORO = 200;
    static final int[] CUPOS = {20, 30, 40, 50, 60};

    public static void main(String[] args) {
        Taquilla[] taquillas = new Taquilla[CUPOS.length];
        Thread[] hilos = new Thread[CUPOS.length];

        // prepara cada taquilla con su hilo, todavia sin arrancar
        for (int i = 0; i < CUPOS.length; i++) {
            taquillas[i] = new Taquilla(i + 1, CUPOS[i]);
            hilos[i] = new Thread(taquillas[i], "Taquilla-" + (i + 1));
        }

        System.out.println("=========== VENTA DE ENTRADAS ===========");
        System.out.println("Abriendo " + hilos.length + " taquillas a la vez...");

        long t0 = System.nanoTime();
        abrir(hilos);
        esperarCierre(hilos);
        long msReales = (System.nanoTime() - t0) / 1000000;

        int total = mostrarResumen(taquillas, msReales);

        System.out.println("=============== AUDITORIA ===============");
        int codigo = lanzarAuditor(AFORO, total);
        System.out.println("Resultado: codigo " + codigo + " (" + significado(codigo) + ")");
    }

    // arranca todos los hilos seguidos
    static void abrir(Thread[] hilos) {
        for (Thread h : hilos) {
            h.start();
        }
    }

    // cuando ya estan todos en marcha, espera a que cierren
    static void esperarCierre(Thread[] hilos) {
        for (Thread h : hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
                System.err.println("Espera interrumpida en " + h.getName());
            }
        }
    }

    // tabla por taquilla y totales; devuelve lo vendido entre todas
    static int mostrarResumen(Taquilla[] taquillas, long msReales) {
        int total = 0;
        long sumaMs = 0;

        System.out.println();
        System.out.println(" Taquilla   Cupo   Vendidas    Tiempo");
        System.out.println(" --------   ----   --------   -------");
        for (Taquilla t : taquillas) {
            System.out.printf("    %d       %4d      %4d    %4d ms%n",
                    t.getNumero(), t.getCupo(), t.getVendidas(), t.getMsAbierta());
            total += t.getVendidas();
            sumaMs += t.getMsAbierta();
        }
        System.out.println("-----------------------------------------");
        System.out.println(" Entradas vendidas : " + total + " / " + AFORO);
        System.out.println(" Tiempo real       : " + msReales + " ms");
        System.out.println(" Suma de tiempos   : " + sumaMs + " ms (lo que tardaria en serie)");
        System.out.println();
        return total;
    }

    // proceso hijo: le pasa "aforo total" por su entrada y lee su veredicto
    static int lanzarAuditor(int aforo, int total) {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Auditor");
        pb.redirectErrorStream(true);

        try {
            Process auditor = pb.start();

            // envia los dos datos en una linea y cierra para que el hijo no espere mas
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(auditor.getOutputStream()));
            bw.write(aforo + " " + total);
            bw.newLine();
            bw.flush();
            bw.close();

            // muestra todo lo que escribe el auditor
            Scanner respuesta = new Scanner(auditor.getInputStream());
            while (respuesta.hasNextLine()) {
                System.out.println("  > " + respuesta.nextLine());
            }
            respuesta.close();

            return auditor.waitFor();
        } catch (IOException e) {
            System.err.println("No se pudo arrancar el Auditor: " + e.getMessage());
            return -1;
        } catch (InterruptedException e) {
            System.err.println("Se interrumpio la espera del Auditor");
            return -1;
        }
    }

    // traduce el codigo de salida del Auditor
    static String significado(int codigo) {
        switch (codigo) {
            case 0:
                return "aforo completo";
            case 1:
                return "sobreventa";
            case 2:
                return "quedan entradas sin vender";
            case 3:
                return "datos no validos";
            default:
                return "el Auditor no ha respondido";
        }
    }
}
