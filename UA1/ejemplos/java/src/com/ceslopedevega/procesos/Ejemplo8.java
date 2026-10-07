package com.ceslopedevega.procesos;
import java.io.IOException;

public class Ejemplo8 {
	public static void main(String args[]) throws IOException {
		ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "DIR");
		pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

		// Iniciamos el proceso
		Process p = pb.start();
	}
}// Ejemplo9
