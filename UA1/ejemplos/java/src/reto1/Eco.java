package reto1;

import java.io.*;

public class Eco {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String linea;
		while ((linea = br.readLine()) != null && !linea.equals("fin")) {
			System.out.println(linea.toUpperCase());
		}
	}
}
