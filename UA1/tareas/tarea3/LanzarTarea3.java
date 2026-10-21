public class LanzarTarea3 {
    public static void main(String[] args) {

        try {
            Process proceso = Runtime.getRuntime().exec(
                    "java Principal " + (args.length > 0 ? args[0] : "")
            );

            int codigo = proceso.waitFor();

            switch (codigo) {
                case 1:
                    System.out.println("Código 1: No se proporcionaron argumentos.");
                    break;
                case 2:
                    System.out.println("Código 2: El argumento no es un número entero.");
                    break;
                case 3:
                    System.out.println("Código 3: El número entero es menor que 0.");
                    break;
                case 0:
                    System.out.println("Código 0: Ejecución correcta.");
                    break;
                default:
                    System.out.println("Código desconocido: " + codigo);
            }

        } catch (Exception e) {
            System.out.println("Error al ejecutar el programa principal: " + e.getMessage());
        }
    }

}