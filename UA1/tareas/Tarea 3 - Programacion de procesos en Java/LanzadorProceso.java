package com.ceslopedevega.hilos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * T3 - Programación de procesos en Java (II)
 *
 * <p>Segundo programa: lanza como proceso hijo a {@link ValidadorArgumentos},
 * espera a que termine, recoge su código de salida y muestra por pantalla
 * qué ha sucedido según el valor devuelto.</p>
 *
 * <p>Uso:</p>
 * <ul>
 *   <li>Sin argumentos: ejecuta una batería de pruebas que cubre los 4 casos.</li>
 *   <li>Con argumentos: se los pasa tal cual al proceso hijo.</li>
 * </ul>
 *
 * @author Alvaro
 * @version 1.0
 */
public class LanzadorProceso {

    /**
     * Ejecuta {@link ValidadorArgumentos} en un proceso nuevo con los
     * argumentos indicados y devuelve su código de salida.
     *
     * @param argumentos argumentos que se pasarán al proceso hijo
     * @return código de salida del proceso hijo, o -1 si no pudo ejecutarse
     */
    public static int ejecutarHijo(String... argumentos) {
        // Ruta al ejecutable java de la JVM actual (portable entre SO)
        String java = System.getProperty("java.home")
                + File.separator + "bin" + File.separator + "java";

        // Mismo classpath que el proceso actual para encontrar la clase hija
        String classpath = System.getProperty("java.class.path");

        // Construcción del comando: java -cp <classpath> <clase> <args...>
        List<String> comando = new ArrayList<>();
        comando.add(java);
        comando.add("-cp");
        comando.add(classpath);
        comando.add(ValidadorArgumentos.class.getName());
        for (String a : argumentos) {
            comando.add(a);
        }

        try {
            ProcessBuilder pb = new ProcessBuilder(comando);
            // El hijo comparte la entrada/salida/error del proceso actual
            pb.inheritIO();

            Process proceso = pb.start();
            // Se espera a que termine y se obtiene su código de salida
            return proceso.waitFor();

        } catch (IOException e) {
            // No se pudo arrancar el proceso (ruta incorrecta, permisos...)
            System.err.println("Error de E/S al lanzar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            // Se interrumpió la espera: se restaura el estado de interrupción
            Thread.currentThread().interrupt();
            System.err.println("Se interrumpió la espera del proceso hijo.");
        } catch (SecurityException e) {
            // El gestor de seguridad no permite crear procesos
            System.err.println("Permisos insuficientes para crear el proceso: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Muestra por pantalla qué ha sucedido según el código devuelto.
     *
     * @param argumentos argumentos con los que se ejecutó el hijo (solo informativo)
     * @param codigo     código de salida devuelto por el proceso hijo
     */
    public static void interpretarCodigo(String[] argumentos, int codigo) {
        String entrada = argumentos.length == 0
                ? "(sin argumentos)"
                : String.join(" ", argumentos);

        System.out.println("Argumentos: " + entrada);
        System.out.println("Código devuelto: " + codigo);

        switch (codigo) {
            case ValidadorArgumentos.OK:
                System.out.println("  -> Todo correcto: entero mayor o igual que 0.");
                break;
            case ValidadorArgumentos.SIN_ARGUMENTOS:
                System.out.println("  -> Error: no se ha pasado ningún argumento.");
                break;
            case ValidadorArgumentos.ES_CADENA:
                System.out.println("  -> El argumento es una cadena, no un número entero.");
                break;
            case ValidadorArgumentos.ENTERO_NEGATIVO:
                System.out.println("  -> El argumento es un número entero menor que 0.");
                break;
            case -1:
                System.out.println("  -> No se pudo ejecutar el proceso hijo.");
                break;
            default:
                System.out.println("  -> Código desconocido (fallo inesperado en el hijo).");
        }
        System.out.println();
    }

    /**
     * Punto de entrada del programa.
     *
     * @param args si se indican, se pasan al proceso hijo; si no, se lanza
     *             una batería de pruebas con los 4 casos posibles
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            interpretarCodigo(args, ejecutarHijo(args));
            return;
        }

        // Batería de pruebas: sin argumentos, cadena, negativo, positivo y cero
        String[][] pruebas = {
                {},
                {"hola"},
                {"-5"},
                {"7"},
                {"0"}
        };

        for (String[] prueba : pruebas) {
            interpretarCodigo(prueba, ejecutarHijo(prueba));
        }
    }
}