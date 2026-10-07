package com.ceslopedevega.procesos;
import java.io.*;

public class Ejemplo2v2 {
	public static void main(String[] args) throws IOException {

		Process p = new ProcessBuilder("bash", "-c", "ls").start();
		try {
			InputStream is = p.getInputStream();
			// Envolvemos el flujo en un BufferedReader para poder extraer líneas completas
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String linea;
			int numeroLinea = 1;

			// Leemos línea a línea hasta que devuelva null (fin de los datos)
			while ((linea = br.readLine()) != null) {
				System.out.println(numeroLinea + " - " + linea);
				numeroLinea++;
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// COMPROBACION DE ERROR - 0 bien - 1 mal
		int exitVal;
		try {
			exitVal = p.waitFor();
			System.out.println("Valor de Salida: " + exitVal);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}