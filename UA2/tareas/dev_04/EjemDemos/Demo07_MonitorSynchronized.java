/**
 * synchronized bloquea el monitor de UN objeto.
 * A entra en un método sincronizado y se queda 1 segundo.
 * B intenta entrar en otro método sincronizado. ¿Espera?
 */
public class Demo07_MonitorSynchronized {

    synchronized void tareaLarga() throws InterruptedException {
        Thread.sleep(1000);
    }

    synchronized void tareaCorta() { }

    // Mide cuánto espera B. 'objA' lo usa el hilo A, 'objB' lo usa el hilo B.
    static void probar(String titulo, Demo07_MonitorSynchronized objA,
                       Demo07_MonitorSynchronized objB) throws InterruptedException {
        Thread a = new Thread(() -> {
            try {
                objA.tareaLarga();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread b = new Thread(() -> {
            long inicio = System.currentTimeMillis();
            objB.tareaCorta();
            System.out.println("   B esperó " + (System.currentTimeMillis() - inicio) + " ms");
        });

        System.out.println(titulo);
        a.start();
        Thread.sleep(200);              // A ya está dentro
        b.start();
        Thread.sleep(100);
        System.out.println("   Estado de B: " + b.getState());
        a.join();
        b.join();
    }

    public static void main(String[] args) throws InterruptedException {
        Demo07_MonitorSynchronized uno = new Demo07_MonitorSynchronized();
        Demo07_MonitorSynchronized otro = new Demo07_MonitorSynchronized();

        probar("1) MISMO objeto:", uno, uno);      // B se bloquea
        probar("2) Objetos DISTINTOS:", uno, otro); // B no espera
    }
}