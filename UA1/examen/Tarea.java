public class Tarea {
    static void main(String[] args) {
        if (args.length == 0) {
            System.exit(1);
        }
        for (String arg : args) {}
        try {

        }catch (Exception e) {
            System.exit(2);
        }

        try {}
        catch (Exception e) {
            System.exit(3);
        }
        System.exit(0);

    }
}
// Aqui iria cada condición para cada argumento con su respectivo error si no es lo deseado
// o directamente el 0 que seria el que esta todo correcto