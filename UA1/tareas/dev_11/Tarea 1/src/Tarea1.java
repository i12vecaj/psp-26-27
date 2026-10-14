import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * FR1: crea un proceso hijo (ProcessBuilder). Existen dos: padre e hijo.
 * FR2: pide al usuario una variable entera.
 * FR3: el proceso padre resta 5 a la variable.
 * FR4: el proceso hijo suma 4 a la variable.
 * FR5: muestra todos los valores por pantalla.
 */
public class Tarea1 {
    static void main() {
        /** Indicador que esta ejecucion es la del proceso hijo. */
        private static final String MODO_HIJO = "hijo";
        if (args.length == 2 && MODO_HIJO.equals(args[0])) {
            ejecutarHijo(args[1]);
        } else {
            ejecutarPadre();
        }
    }
    /**
     * Codigo del proceso padre: pide la variable, lanza al hijo,
     * resta 5 a su copia y muestra los resultados.
     */
    private static void ejecutarPadre() {
        int variable;
        int original;

        try {
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Introduce un numero entero: ");
            String linea = teclado.readLine();

            if (linea == null || linea.trim().isEmpty()) {
                System.err.println("Error: no has introducido ningun valor.");
                System.exit(1);
                return;
            }
            variable = Integer.parseInt(linea.trim());
        } catch (NumberFormatException e) {
            System.err.println("Error: el valor no es un numero entero valido.");
            System.exit(1);
            return;
        } catch (IOException e) {
            System.err.println("Error al leer la entrada: " + e.getMessage());
            System.exit(1);
            return;
        }
        original = variable;

        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        ProcessBuilder constructor = new ProcessBuilder(
                java, "-cp", classpath, Procesos.class.getName(), MODO_HIJO, String.valueOf(original));
        constructor.inheritIO(); // el hijo escribe en la misma consola

        Process hijo;
        try {
            hijo = constructor.start();
        } catch (IOException e) {
            System.err.println("Error al crear el proceso hijo: " + e.getMessage());
            System.exit(1);
            return;
        }

        try {
            variable = Math.subtractExact(variable, 5);
        } catch (ArithmeticException e) {
            System.err.println("Error: desbordamiento al restar 5.");
            hijo.destroy();
            System.exit(1);
            return;
        }

        try {
            int codigo = hijo.waitFor();
            if (codigo != 0) {
                System.err.println("Error: el proceso hijo termino con codigo " + codigo + ".");
                System.exit(1);
                return;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error: se interrumpio la espera del proceso hijo.");
            hijo.destroy();
            System.exit(1);
            return;
        }

        System.out.println("[PADRE | PID " + ProcessHandle.current().pid() + ", hijo "
                + hijo.pid() + "] Valor tras restar 5: " + variable);
        System.out.println();
        System.out.println("Resumen -> valor original: " + original
                + " | padre (-5): " + variable
                + " | hijo (+4): " + (original + 4));
    }

    /**
     * Codigo del proceso hijo: recibe el valor por argumento y le suma 4.
     *
     * @param argumento valor inicial pasado por el padre
     */
    private static void ejecutarHijo(String argumento) {
        try {
            int variable = Integer.parseInt(argumento);

            // FR4: el hijo suma 4 a su copia de la variable
            variable = Math.addExact(variable, 4);

            // FR5: mostrar el valor del hijo
            System.out.println("[HIJO  | PID " + ProcessHandle.current().pid() + ", padre "
                    + ProcessHandle.current().parent().map(ProcessHandle::pid).orElse(-1L)
                    + "] Valor tras sumar 4: " + variable);
        } catch (NumberFormatException e) {
            System.err.println("Error (hijo): argumento no valido.");
            System.exit(1);
        } catch (ArithmeticException e) {
            System.err.println("Error (hijo): desbordamiento al sumar 4.");
            System.exit(1);
        }
    }
}