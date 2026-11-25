
 // FR3 - Programa principal

 //Crea una cuenta con 1000 euros y cuatro hilos que la comparten. Cada hilo
 // tiene un nombre y una cantidad. Se lanzan todos con start(), se espera a que
// terminen con join() y se muestra el saldo final.
 // Saldo final esperado: 1000 + 100(Ana) + 200(Luis) + 300(Marta) + 400(Pedro) = 2000 euros.

 // FR4 - Diferencias entre el apartado 3 y el 4

 // Apartado 3 (con synchronized): los ingresos se hacen de uno en uno. Cada
 // hilo parte del saldo que dejó el anterior y el saldo final es  2000.
 // El programa tarda más aprox 11 segundos porque cada hilo espera su turno por el syncronized.

 // Apartado 4 (sin synchronized): los hilos entran a la vez en ingresarSaldo().
 // En mis pruebas todos leyeron el saldo cuando todavía valía 1000, cada uno le
 // sumó su cantidad y el último en guardarlo pisó a los demás. El saldo final
 // salió 1100, 1200 y 1200 en vez de 2000: se pierden ingresos y el resultado
 // cambia de una ejecución a otra. El programa tarda menos.
 // ¿Por qué difiere? Ingresar son tres pasos: leer el saldo, sumar y
 // guardar como lo explicado hoy en clase en los apuntes con la tabla.
 // Sin synchronized dos hilos pueden leer el mismo saldo antes de que el otro
 // lo guarde (condición de carrera, como pasa con valor++). Con synchronized el
 // hilo toma el monitor de la cuenta y nadie más entra hasta que termina; esa
  // parte del código es la sección crítica.

public class Main {

    public static void main(String[] args) {
        try {
            CuentaCorriente cuenta = new CuentaCorriente(1000);
            System.out.println("Saldo inicial: " + cuenta.getSaldo() + " euros");

            HiloIngreso[] hilos = {
                    new HiloIngreso("Ana", cuenta, 100),
                    new HiloIngreso("Luis", cuenta, 200),
                    new HiloIngreso("Marta", cuenta, 300),
                    new HiloIngreso("Pedro", cuenta, 400)
            };

            // Primero se lanzan todos los hilos
            for (HiloIngreso hilo : hilos) {
                hilo.start();
            }
            // Después se espera a que terminen todos
            for (HiloIngreso hilo : hilos) {
                hilo.join();
            }

            System.out.println("Saldo final: " + cuenta.getSaldo() + " euros");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El programa se ha interrumpido mientras esperaba a los hilos");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
