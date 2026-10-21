package UA1.tareas.Tarea3;

import java.io.IOException;

public class Tarea3 {
    static void main(String[] args) {
        if (args.length<1){
            System.out.println("Da 1");
            System.exit(1);

        } else if (args.length<2) {
            System.exit(2);
            System.out.println("da 2");
        }else if (args.length>0){
            System.exit(3);
            System.out.println("Da 3");
        }else {
            System.exit(0);
            System.out.println("Da 0");
        }
        Tarea3Implementacion argumento1 = new Tarea3Implementacion(1,"da 1");
        Tarea3Implementacion argumento2 = new Tarea3Implementacion(2,"da 2 ");
        Tarea3Implementacion argumento0 = new Tarea3Implementacion(0,"da 0");
        Tarea3Implementacion argumento3 = new Tarea3Implementacion(3,"da 3");

        
    }
}
