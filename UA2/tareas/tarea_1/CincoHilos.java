package actividad1UA2;

import java.util.Objects;

/** FR1: El resultado logico o racional indica que deberian de ser 1000 * 5, pero como
     directamente saca por pantalla 5 veces 1000
     Resultado:
     Contador: 1000
     Contador: 1000
     Contador: 1000
     Contador: 1000
     Contador: 1000***/

public class CincoHilos implements Runnable{
    int contador_global = 0;
    Object llave = new Object();

    @Override
    public void run() {
        contador_global += 1000;
        System.out.println(Thread.currentThread().getName()+ " | Contador: "+contador_global);
    }

    public static void main() throws InterruptedException {

        CincoHilos tarea = new CincoHilos();

        Thread h1 = new Thread(tarea,"Hilo 1");
        Thread h2 = new Thread(tarea,"Hilo 2");
        Thread h3 = new Thread(tarea,"Hilo 3");
        Thread h4 = new Thread(tarea,"Hilo 4");
        Thread h5 = new Thread(tarea,"Hilo 5");

        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();
    }

}
