/*
 * Reto 2 · Enviar datos al hijo (10 min)
 *
 * Enunciado: el programa `Eco` lee líneas de su entrada hasta recibir `fin`
 * y las devuelve en mayúsculas. Haz un padre que le envíe `hola`, `mundo` y
 * `fin`, y muestre lo que responde. Predice: ¿qué pasa si no envías `fin` ni
 * cierras el flujo?
 *
 * Pista 1: `getOutputStream()` envuelto en un `PrintWriter` con autoflush.
 * Pista 2: haz `close()` al terminar. Para lanzar `Eco`, usa
 * `"java", "-cp", System.getProperty("java.class.path"), "Eco"`.
 * Ampliación: espera con `waitFor(3, TimeUnit.SECONDS)` y, si no termina,
 * `destroy()`.
 */
package reto2;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Reto2 {
    public static void main(String[] args) throws Exception {
        // Sin enviar "fin" ni cerrar la entrada, Eco espera otra línea y el padre
        // también queda esperando a que el proceso hijo termine.
        System.out.println("Respuesta de Eco:");
        ProcessBuilder constructor = new ProcessBuilder(
                "java", "-cp", System.getProperty("java.class.path"), "reto1.Eco"
        );
        constructor.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        constructor.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process proceso = constructor.start();

        // El autoflush hace que cada println envíe la línea al hijo inmediatamente.
        try (PrintWriter entradaHijo = new PrintWriter(proceso.getOutputStream(), true)) {
            entradaHijo.println("hola");
            entradaHijo.println("mundo");
            entradaHijo.println("fin");
        } // Cerrar la entrada también le indica al hijo que no recibirá más datos.

        boolean terminado = proceso.waitFor(3, TimeUnit.SECONDS);
        if (!terminado) {
            proceso.destroy();
            proceso.waitFor();
            System.out.println("El hijo no terminó en 3 segundos; se ha detenido.");
        }

        System.out.println("Código de salida: " + proceso.exitValue());
    }
}
