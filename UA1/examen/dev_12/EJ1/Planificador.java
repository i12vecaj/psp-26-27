import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Planificador {

    //almacenar datos de cada proceso
    static class InfoTarea{
        String nombre;
        Process proceso;
        StringBuilder salida = new StringBuilder();

        public InfoTarea(String nombre, Process proceso) {
        }
    }

    static void main(String[] args) {
        int ok = 0;
        int error = 0;
        int timeout = 0;

        List<InfoTarea> tareas = new ArrayList<>();

        try {
            File logErrores = new File("errores.log");

            //datos solicitados
            String[][] configuracion = {
                    {"A", "2"},
                    {"B", "1"},
                    {"C", "8"},
                    {"falla", "1"},
                    {"D", "-1"}
            };

            //lanzar los procesos
            for (String[] datos : configuracion ){
                String nombre = datos[0];
                String segundos = datos[1];

                ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Tarea", "-cp", System.getProperty("java.class.path"));

                //añadir los errores al fichero.log
                pb.redirectError(ProcessBuilder.Redirect.appendTo(logErrores));

                Process proceso = pb.start();

                InfoTarea info = new InfoTarea(nombre, proceso);
                tareas.add(info);

                //enviar mensaje al hijo
                try (OutputStreamWriter writer = new OutputStreamWriter(proceso.getOutputStream())){
                    writer.write("Hola desde el padre a "+nombre);
                }



            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

//    private static void leerSalida(InputStream is, StringBuilder salida){
//        try {
//
//        }catch (){
//
//        }
//    }
}
