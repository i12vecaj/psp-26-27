/**
 * @file      ua2tarea3.java
 * @brief     Cuenta los caracteres de varios ficheros de texto, primero uno tras otro y luego con un hilo por fichero
 * @date      25/11/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 * @note      Uso. java UA2.tareas.dev_14.tarea_3.ua2tarea3 fichero1.txt fichero2.txt ...
 * @note      Funcionamiento. FR1 lee cada fichero caracter a caracter, uno detras de otro, y dice cuantos
 *            tiene. FR2 hace lo mismo pero lanza un HiloContador por fichero, todos a la vez, y espera
 *            con join. FR3 mide los dos con System.currentTimeMillis y compara el tiempo.
 * @note      Diferencia entre FR1 y FR2. Los dos dan el mismo numero de caracteres. En secuencial el
 *            tiempo es la suma de lo que tarda cada fichero. Con hilos los ficheros se leen a la vez y
 *            el tiempo se parece al del fichero mas lento, asi que con varios ficheros grandes tarda
 *            menos. Con ficheros pequenos casi no se nota, porque crear los hilos tambien cuesta.
 */
package UA2.tareas.dev_14.tarea_3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

// hilo que cuenta los caracteres de un fichero
class HiloContador extends Thread {
    private String fichero;
    private long caracteres;

    public HiloContador(String fichero) {
        super("Hilo " + fichero);
        this.fichero = fichero;
    }

    @Override
    public void run() {
        caracteres = ua2tarea3.contarCaracteres(fichero);
    }

    public long getCaracteres() {
        return caracteres;
    }

    public String getFichero() {
        return fichero;
    }
}

public class ua2tarea3 {

    static final int SIN_FICHEROS = 1;
    static final int ESPERA_INTERRUMPIDA = 2;

    // cuenta los caracteres de un fichero, da menos uno si no se puede leer
    static long contarCaracteres(String fichero) {
        BufferedReader br = null;
        long total = 0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fichero), "UTF-8"));
            // leo caracter a caracter hasta el final
            while (br.read() != -1) {
                total++;
            }
        } catch (IOException e) {
            System.err.println("No se puede leer " + fichero);
            return -1;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println("No se pudo cerrar " + fichero);
                }
            }
        }
        return total;
    }

    // enseña el resultado de un fichero
    static void mostrar(String fichero, long caracteres) {
        if (caracteres >= 0) {
            System.out.println(fichero + " tiene " + caracteres + " caracteres");
        } else {
            System.out.println(fichero + " no se ha podido contar");
        }
    }

    public static void main(String[] args) {
        // sin ficheros no hay nada que hacer
        if (args.length == 0) {
            System.err.println("Faltan los ficheros. Uso ua2tarea3 fichero1.txt fichero2.txt ...");
            System.exit(SIN_FICHEROS);
        }

        // FR1 cuento uno detras de otro
        System.out.println("Ejecucion secuencial");
        long inicioSecuencial = System.currentTimeMillis();
        for (int i = 0; i < args.length; i++) {
            mostrar(args[i], contarCaracteres(args[i]));
        }
        long tiempoSecuencial = System.currentTimeMillis() - inicioSecuencial;

        // FR2 un hilo por fichero, todos a la vez
        System.out.println("Ejecucion concurrente");
        long inicioHilos = System.currentTimeMillis();
        HiloContador[] hilos = new HiloContador[args.length];
        for (int i = 0; i < args.length; i++) {
            hilos[i] = new HiloContador(args[i]);
            hilos[i].start();
        }
        // espero a todos antes de mirar los resultados
        for (int i = 0; i < hilos.length; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Se interrumpio la espera de " + hilos[i].getName());
                System.exit(ESPERA_INTERRUMPIDA);
            }
        }
        long tiempoHilos = System.currentTimeMillis() - inicioHilos;
        for (int i = 0; i < hilos.length; i++) {
            mostrar(hilos[i].getFichero(), hilos[i].getCaracteres());
        }

        // FR3 comparo los tiempos
        System.out.println("Tiempo secuencial  " + tiempoSecuencial + " ms");
        System.out.println("Tiempo con hilos   " + tiempoHilos + " ms");
        if (tiempoHilos < tiempoSecuencial) {
            System.out.println("Con hilos ha tardado " + (tiempoSecuencial - tiempoHilos) + " ms menos");
        } else {
            System.out.println("Con hilos no ha ido mas rapido, los ficheros son pequenos o hay pocos nucleos");
        }
    }
}
