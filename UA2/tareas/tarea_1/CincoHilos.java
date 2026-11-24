package actividad1UA2;

import java.util.Objects;

/** FR1: El resultado logico o racional indica que deberian de ser 1000 * 5.
     Resultado:
     Contador: 1000
     Contador: 2000
     Contador: 3000
     Contador: 4000
     Contador: 5000***/

public class CincoHilos implements Runnable{
    private int contador_global = 0;
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

        h1.join();
        h2.join();
        h3.join();
        h4.join();
        h5.join();

        System.out.println();
        System.out.println("RESULTADO FINAL: "+tarea.contador_global);
    }

}
