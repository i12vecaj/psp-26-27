import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class Planificador {

	public static void main(String[] args) throws Exception {
		String[][] tareas = { { "A", "2" }, { "B", "1" }, { "C", "8" }, { "falla", "1" }, { "D", "-1" } };
		Process[] procesos = new Process[tareas.length];
		// Lanzo todas antes de esperar a ninguna, el error saldrá en errores.log
		for (int i = 0; i < tareas.length; i++) {
			ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Tarea",
					tareas[i][0], tareas[i][1]);
			pb.redirectError(ProcessBuilder.Redirect.appendTo(new File("errores.log")));
			procesos[i] = pb.start();
			try (OutputStream entrada = procesos[i].getOutputStream()) {
				entrada.write(("Hola desde el Padre " + tareas[i][0] + "\n").getBytes());
			} catch (IOException e) {
				// La tarea ya habia terminado por errores en argumentos incorrectos
			}
		}
		int ok = 0, error = 0, timeout = 0;
		for (int i = 0; i < tareas.length; i++) {
			String nombre = "Tarea " + tareas[i][0] + ": ";
			if (!procesos[i].waitFor(3, TimeUnit.SECONDS)) {
				procesos[i].destroyForcibly();
				System.out.println(nombre + "TIMEOUT (cancelada)");
				timeout++;
				continue;
			}
			int codigo = procesos[i].exitValue();
			System.out.println(nombre + (codigo == 0 ? "OK" : "ERROR") + " (codigo " + codigo + ")");
			if (codigo == 0) ok++; else error++;
			procesos[i].inputReader().lines().forEach(linea -> System.out.println("   " + linea));
		}
		System.out.println("Resumen: " + ok + " OK, " + error + " ERROR, " + timeout + " TIMEOUT");
	}
}