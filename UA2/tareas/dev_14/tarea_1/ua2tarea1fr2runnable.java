/**
 * @file      ua2tarea1fr2runnable.java
 * @brief     Cinco hilos con Runnable suman 1000 cada uno a un contador sincronizado
 * @date      25/11/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 * @note      Funcionamiento. Lo mismo que ua2tarea1fr2, pero la tarea implementa Runnable y cada hilo
 *            se crea con new Thread(tarea, nombre).
 * @note      Diferencia con la version Thread. El resultado es el mismo, 5000 siempre, porque lo que
 *            protege es el synchronized del contador y no la forma de crear el hilo. Con Runnable la
 *            clase no hereda de Thread, asi que podria heredar de otra clase si hiciera falta.
 */
package UA2.tareas.dev_14.tarea_1;

// el contador que comparten las tareas, protegido
class ContadorRunnable {
    private int valor = 0;

    // solo entra un hilo a la vez
    public synchronized void incrementa() {
        valor++;
    }

    public synchronized int getValor() {
        return valor;
    }
}

// tarea que suma 1000 al contador
class TareaSumadora implements Runnable {
    private ContadorRunnable contador;

    public TareaSumadora(ContadorRunnable contador) {
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < ua2tarea1fr2runnable.VUELTAS; i++) {
            contador.incrementa();
        }
    }
}

public class ua2tarea1fr2runnable {

    static final int HILOS = 5;
    static final int VUELTAS = 1000;

    public static void main(String[] args) {
        ContadorRunnable contador = new ContadorRunnable();
        Thread[] hilos = new Thread[HILOS];

        // creo los hilos con la misma tarea y los lanzo
        for (int i = 0; i < HILOS; i++) {
            hilos[i] = new Thread(new TareaSumadora(contador), "Hilo " + (i + 1));
            hilos[i].start();
        }

        // espero a que acaben todos
        for (int i = 0; i < HILOS; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Se interrumpio la espera de " + hilos[i].getName());
                System.exit(1);
            }
        }

        // muestro el resultado
        System.out.println("Con Runnable y synchronized");
        System.out.println("Valor esperado " + (HILOS * VUELTAS));
        System.out.println("Valor final    " + contador.getValor());
    }
}
