/**
 * @file      Espera.java
 * @brief     Duerme los segundos que se le pasan como argumento
 * @date      29/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */
package UA1.tareas.dev_14.simulacro.laboratorio;

public class Espera {

    public static void main(String[] args) throws InterruptedException {
        int segundos = Integer.parseInt(args[0]);
        Thread.sleep(segundos * 1000L);
        System.out.println("He esperado " + segundos + " s");
    }
}
