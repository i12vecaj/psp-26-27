/*
 * Reto 4 · Hilos con Runnable y join() (7 min)
 *
 * Enunciado: crea 3 hilos que duerman 1, 2 y 3 segundos e impriman su nombre
 * al acabar. El main debe imprimir cuánto ha tardado en total cuando terminen
 * los tres. Predice: ¿cuánto tarda? ¿Y si pones el join() justo después de
 * cada start()?
 *
 * Pista 1: una clase que implemente Runnable y reciba los segundos.
 * Pista 2: guarda los hilos en un array y haz los join() en un segundo bucle.
 * Ampliación: quita los join(). ¿Qué imprime ahora el main y cuándo?
 */
package reto4;

public class Reto4 {
    private static class Tarea implements Runnable {
        private final int segundos;

        private Tarea(int segundos) {
            this.segundos = segundos;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(segundos * 1000L);
                System.out.println(Thread.currentThread().getName() + " termina tras " + segundos + " s");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " fue interrumpido");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        boolean esperarAQueTerminen = args.length == 0 || !args[0].equalsIgnoreCase("sin-join");
        int[] duraciones = {1, 2, 3};
        Thread[] hilos = new Thread[duraciones.length];
        long inicio = System.currentTimeMillis();

        for (int i = 0; i < duraciones.length; i++) {
            hilos[i] = new Thread(new Tarea(duraciones[i]), "Hilo-" + (i + 1));
            hilos[i].start();
        }

        if (esperarAQueTerminen) {
            for (Thread hilo : hilos) {
                hilo.join();
            }
        }

        // Con join, main termina cuando acaban los tres (unos 3 s).
        // Con "sin-join", main imprime enseguida; los hilos siguen ejecutándose
        // y la JVM espera a que terminen porque no son hilos daemon.
        System.out.println("main termina tras "
                + (System.currentTimeMillis() - inicio) / 1000.0 + " s");
    }
}
