import java.io.File;

public class Planificador {
    static void main(String[] args) {
        String[] nombresArgs = {"A", "B", "falla", "C" ,"D"};
        String [] segundosArgs = {"1","2","3","-4", "5"};

        Process [] procesos = new Process[5];

        File archivoErrores = new File("errores.log");
        for (int i = 0; i < 5; i++) {
            try{
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Tarea", nombresArgs[i], segundosArgs[i]);
                pb.redirectError(ProcessBuilder.Redirect.appendTo(archivoErrores));
            }catch (Exception e){
                System.err.println("Error al intentar lanzar " + nombresArgs[i]);
            }
        }

    }

}
