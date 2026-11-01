public class Hijo {

    // Muestra el identificador del hijo, su PID y el PID de su padre
    static void mostrarDatos(int id) {
        long pid = ProcessHandle.current().pid();
        long pidPadre = ProcessHandle.current().parent().map(ProcessHandle::pid).orElse(-1L);
        System.out.println("  Hijo " + id + " -> PID: " + pid + " | PID del padre: " + pidPadre);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: java Hijo <identificador>");
            System.exit(1);
        }
        try {
            int id = Integer.parseInt(args[0]);
            mostrarDatos(id);
            System.exit(0);
        } catch (NumberFormatException e) {
            System.err.println("El identificador debe ser un numero entero: " + args[0]);
            System.exit(2);
        }
    }
}
