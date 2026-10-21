package com.ceslopedevega.hilos;

/**
 * T3 - Programación de procesos en Java (II)
 *
 * <p>Programa principal (proceso hijo). Recibe argumentos desde {@code main()}
 * y finaliza con {@link System#exit(int)} devolviendo un código de salida
 * que indica el tipo de argumento recibido:</p>
 *
 * <ul>
 *   <li>{@value #SIN_ARGUMENTOS}: no se ha pasado ningún argumento.</li>
 *   <li>{@value #ES_CADENA}: el argumento es una cadena (no es un entero).</li>
 *   <li>{@value #ENTERO_NEGATIVO}: el argumento es un entero menor que 0.</li>
 *   <li>{@value #OK}: cualquier otro caso (entero mayor o igual que 0).</li>
 * </ul>
 *
 * <p>Si se reciben varios argumentos, solo se evalúa el primero.</p>
 *
 * @author Alvaro
 * @version 1.0
 */
public class ValidadorArgumentos {

    /** Código de salida: el número de argumentos es menor que 1. */
    public static final int SIN_ARGUMENTOS = 1;

    /** Código de salida: el argumento es una cadena de texto. */
    public static final int ES_CADENA = 2;

    /** Código de salida: el argumento es un entero menor que 0. */
    public static final int ENTERO_NEGATIVO = 3;

    /** Código de salida: cualquier otro caso (entero >= 0). */
    public static final int OK = 0;

    /**
     * Evalúa los argumentos recibidos y calcula el código de salida.
     * Se separa de {@code main()} para que la lógica sea clara y reutilizable.
     *
     * @param args argumentos recibidos desde la línea de comandos
     * @return el código de salida correspondiente (0, 1, 2 o 3)
     */
    public static int evaluar(String[] args) {
        // Control de errores: args nulo o vacío => menos de 1 argumento
        if (args == null || args.length < 1) {
            return SIN_ARGUMENTOS;
        }

        // Solo se evalúa el primer argumento
        String argumento = args[0];

        // Control de errores: argumento nulo (no debería ocurrir desde consola)
        if (argumento == null) {
            return ES_CADENA;
        }

        try {
            // Se intenta interpretar el argumento como un número entero
            int numero = Integer.parseInt(argumento.trim());

            if (numero < 0) {
                return ENTERO_NEGATIVO;
            }
            return OK;

        } catch (NumberFormatException e) {
            // No es un entero válido (texto, decimal, vacío, fuera de rango...)
            return ES_CADENA;
        }
    }

    /**
     * Punto de entrada del programa.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        int codigo;
        try {
            codigo = evaluar(args);
        } catch (Exception e) {
            // Control de errores: cualquier fallo inesperado se informa por la
            // salida de error y se devuelve un código distinto a los definidos.
            System.err.println("Error inesperado: " + e.getMessage());
            codigo = 99;
        }
        System.exit(codigo);
    }
}