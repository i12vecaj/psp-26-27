/**
 * @file      Reto4.java
 * @brief     Tres hilos que duermen 1, 2 y 3 segundos y el main que mide cuanto tardan con join
 * @date      29/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.simulacro.laboratorio;

public class Reto4 {

    // la tarea de cada hilo: dormir y decir su nombre al acabar
    static class Dormilon implements Runnable {
        private final int segundos;

        Dormilon(int segundos) {
            this.segundos = segundos;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(segundos * 1000L);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrumpido");
            }
            System.out.println(Thread.currentThread().getName() + " ha terminado (" + segundos + " s)");
        }
    }

    static Thread[] crear() {
        Thread[] hilos = new Thread[3];
        for (int i = 0; i < 3; i++) {
            hilos[i] = new Thread(new Dormilon(i + 1), "Hilo-" + (i + 1));
        }
        return hilos;
    }

    public static void main(String[] args) throws InterruptedException {
        // 1. todos los start y luego todos los join
        Thread[] hilos = crear();
        long inicio = System.currentTimeMillis();
        for (Thread h : hilos) {
            h.start();
        }
        for (Thread h : hilos) {
            h.join();
        }
        System.out.println("Join en un segundo bucle: " + (System.currentTimeMillis() - inicio) + " ms");

        // 2. el join justo despues de cada start
        hilos = crear();
        inicio = System.currentTimeMillis();
        for (Thread h : hilos) {
            h.start();
            h.join();
        }
        System.out.println("Join despues de cada start: " + (System.currentTimeMillis() - inicio) + " ms");

        // 3. ampliacion: sin join el main no espera
        hilos = crear();
        inicio = System.currentTimeMillis();
        for (Thread h : hilos) {
            h.start();
        }
        System.out.println("Sin join, el main acaba a los " + (System.currentTimeMillis() - inicio) + " ms");
    }
}
