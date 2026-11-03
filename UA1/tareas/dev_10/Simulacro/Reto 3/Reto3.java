/*
 * Reto 3 · Secuencial frente a paralelo (10 min)
 *
 * Enunciado: `Espera` duerme los segundos que le indiques. Lanza dos hijos de
 * 2 s: primero uno tras otro, y luego los dos a la vez. Mide el tiempo de cada
 * versión. Predice: ¿cuánto tardará cada una?
 *
 * Pista 1: `System.currentTimeMillis()` antes y después.
 * Pista 2: en paralelo, haz `start()` de los dos antes del primer `waitFor()`.
 * Ampliación: ¿cuánto tardan 3 hijos de 2 s en paralelo?
 */
package reto3;

public class Reto3 {
    private static final String CLASSPATH = System.getProperty("java.class.path");

    public static void main(String[] args) throws Exception {
        System.out.println("Dos hijos secuenciales:");
        long inicioSecuencial = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            Process hijo = lanzarEspera(2);
            hijo.waitFor();
        }
        // Predicción: secuencial tarda unos 4 s; paralelo y tres en paralelo, unos 2 s.
        mostrarDuracion(inicioSecuencial);

        System.out.println("\nDos hijos en paralelo:");
        long inicioParalelo = System.currentTimeMillis();
        Process hijo1 = lanzarEspera(2);
        Process hijo2 = lanzarEspera(2);
        hijo1.waitFor();
        hijo2.waitFor();
        mostrarDuracion(inicioParalelo);

        System.out.println("\nAmpliación: tres hijos en paralelo:");
        long inicioTres = System.currentTimeMillis();
        Process hijo3 = lanzarEspera(2);
        Process hijo4 = lanzarEspera(2);
        Process hijo5 = lanzarEspera(2);
        hijo3.waitFor();
        hijo4.waitFor();
        hijo5.waitFor();
        mostrarDuracion(inicioTres);
    }

    private static Process lanzarEspera(int segundos) throws Exception {
        return new ProcessBuilder(
                "java", "-cp", CLASSPATH, "reto1.Espera", Integer.toString(segundos)
        ).inheritIO().start();
    }

    private static void mostrarDuracion(long inicio) {
        long duracion = System.currentTimeMillis() - inicio;
        System.out.println("Tiempo total: " + duracion / 1000.0 + " s");
    }
}
