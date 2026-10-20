package com.ceslopedevega.procesos;
import java.io.IOException;

public class Ejemplo1v2 {
   public static void main(String[] args) throws IOException  {	   
	   ProcessBuilder pb = new ProcessBuilder("gnome-terminal");
      for(int i=0; i<1;i++) 
      {
         Process p = pb.start();
         System.out.println("El PID del nuevo proceso es: " + p.pid());
      }
   }
}//Ejemplo1

