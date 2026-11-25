/**
 * @file      ua2tarea1fr1.java
 * @brief     Cinco hilos suman 1000 cada uno a un contador compartido sin sincronizar
 * @date      25/11/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 * @note      Funcionamiento. El main crea un contador, lanza 5 hilos que le suman 1000 cada uno,
 *            espera a que acaben con join y muestra el valor final, que deberia ser 5000.
 * @note      Reflexion. No siempre sale 5000. contador++ no es una sola operacion, son tres pasos
 *            (leer, sumar y escribir). Si dos hilos leen el mismo valor a la vez, los dos escriben
 *            el mismo resultado y se pierde una suma. Es una condicion de carrera. Con pocas
 *            vueltas a veces sale bien, pero eso no quiere decir que el programa sea correcto.
 */
package UA2.tareas.dev_14.tarea_1;

// el contador que comparten los hilos, sin proteger
class ContadorFr1 {
    private int valor = 0;

    // suma uno sin sincronizar
    public void incrementa() {
        valor++;
    }

    public int getValor() {
        return valor;
    }
}

// hilo que suma 1000 al contador
class HiloSumadorFr1 extends Thread {
    private ContadorFr1 contador;

    public HiloSumadorFr1(String nombre, ContadorFr1 contador) {
        super(nombre);
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < ua2tarea1fr1.VUELTAS; i++) {
            contador.incrementa();
        }
    }
}

public class ua2tarea1fr1 {

    static final int HILOS = 5;
    static final int VUELTAS = 1000;

    public static void main(String[] args) {
        ContadorFr1 contador = new ContadorFr1();
        HiloSumadorFr1[] hilos = new HiloSumadorFr1[HILOS];

        // creo y lanzo los 5 hilos
        for (int i = 0; i < HILOS; i++) {
            hilos[i] = new HiloSumadorFr1("Hilo " + (i + 1), contador);
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

        // muestro lo que ha salido y lo que tendria que salir
        int esperado = HILOS * VUELTAS;
        System.out.println("Valor esperado " + esperado);
        System.out.println("Valor final    " + contador.getValor());
        if (contador.getValor() != esperado) {
            System.out.println("Se han perdido " + (esperado - contador.getValor()) + " sumas por no sincronizar");
        } else {
            System.out.println("Esta vez ha salido bien, pero sin sincronizar no esta garantizado");
        }
    }
}
