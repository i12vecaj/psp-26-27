import java.io.IOException;
import java.util.Scanner;

public class Planificador {

    static void main(String[] args) throws InterruptedException {
        String[] cosa5 = new String[]{"D", "-1"};
        String[] cosa1 = new String[]{"A", "2"};
        String[] cosa2 = new String[]{"B", "1"};
        String[] cosa3 = new String[]{"C", "8"};
        String[] cosa4 = new String[]{"falla", "1"};

        Tarea tarea5 = new Tarea(cosa5);
        Tarea tarea1 = new Tarea(cosa1);
        Tarea tarea2 = new Tarea(cosa2);
        Tarea tarea3 = new Tarea(cosa3);
        Tarea tarea4 = new Tarea(cosa4);


        tarea1.start();

        tarea2.start();

        tarea3.start();

        tarea4.start();

        tarea5.start();


    }
}
