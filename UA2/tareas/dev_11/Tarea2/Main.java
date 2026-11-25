package Tarea2;

import org.w3c.dom.ls.LSOutput;

public class Main {
    static void main() {
        Cosa cuentaCorriente = new Cosa("Maricarmen",1000);
        System.out.println("Dinero actual en la cuenta: "+cuentaCorriente.getDinero());
        Cosa a = new Cosa("Carlos", 2000);
        Cosa b = new Cosa("Jose", 3000);
        Cosa c = new Cosa("Maria", 5000);
        Cosa d = new Cosa("Carmen", 1000);
        a.start();
        b.start();
        c.start();
        d.start();
        System.out.println("Dinero actual en la cuenta: "+cuentaCorriente.getDinero());

    }
}
