/**
 * @file      ua2tarea1fr2.java
 * @brief     Cinco hilos con Thread suman 1000 cada uno a un contador sincronizado
 * @date      25/11/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 * @note      Funcionamiento. Igual que el FR1, pero el metodo que suma es synchronized y los hilos
 *            heredan de Thread. Cada hilo coge el monitor del contador antes de sumar, asi que solo
 *            suma uno a la vez.
 * @note      Diferencia con el FR1. Ahora sale siempre 5000. En el FR1 se podian perder sumas porque
 *            dos hilos leian el mismo valor a la vez. Con synchronized la suma entera (leer, sumar y
 *            escribir) la hace un hilo cada vez. Tarda un poco mas porque los hilos esperan su turno.
 */
package UA2.tareas.dev_14.tarea_1;

// el contador que comparten los hilos, protegido
class ContadorFr2 {
    private int valor = 0;

    // solo entra un hilo a la vez
    public synchronized void incrementa() {
        valor++;
    }

    public synchronized int getValor() {
        return valor;
    }
}

// hilo que suma 1000 al contador
class HiloSumadorFr2 extends Thread {
    private ContadorFr2 contador;

    public HiloSumadorFr2(String nombre, ContadorFr2 contador) {
        super(nombre);
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < ua2tarea1fr2.VUELTAS; i++) {
            contador.incrementa();
        }
    }
}

public class ua2tarea1fr2 {

    static final int HILOS = 5;
    static final int VUELTAS = 1000;

    public static void main(String[] args) {
        ContadorFr2 contador = new ContadorFr2();
        HiloSumadorFr2[] hilos = new HiloSumadorFr2[HILOS];

        // creo y lanzo los hilos con Thread
        for (int i = 0; i < HILOS; i++) {
            hilos[i] = new HiloSumadorFr2("Hilo " + (i + 1), contador);
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

        // muestro el resultdo
        System.out.println("Con Thread y synchronized");
        System.out.println("Valor esperado " + (HILOS * VUELTAS));
        System.out.println("Valor final    " + contador.getValor());
    }
}
