Tarea 2: Control de Concurrencia con CuentaCorriente



1. Estructura de Archivos

- CuentaCorriente.java: Clase del recurso compartido con saldo, sleep() aleatorio y metodos sincronizados (FR1).

- HiloIngreso.java: Hilo que extiende Thread y realiza los ingresos (FR2).

- Main.java: Programa principal que instancia la cuenta, lanza los hilos y espera con join() (FR3).



2. Funcionamiento General del Programa (FR1, FR2, FR3)

- CuentaCorriente (FR1): Mantiene el atributo saldo protegido. Sus metodos (getSaldo, setSaldo e ingresar) estan marcados como synchronized, bloqueando el monitor del objeto para garantizar exclusion mutua. Incluyen retardos aleatorios (Thread.sleep()) de entre 250 y 2000 ms para simular latencia real.

- HiloIngreso (FR2): Extiende la clase Thread y sobrescribe el metodo run() para invocar el ingreso sobre la cuenta compartida.

- Ejecucion en Main (FR3): Se instancia una unica cuenta con saldo inicial (1000.0), se lanzan tres hilos concurrentes (Hilo-1, Hilo-2, Hilo-3) y se emplea .join() en cada uno para asegurar que el hilo principal espere a que todos finalicen antes de mostrar el saldo total (1850.0).



3. Diferencias al quitar synchronized (FR4)

Si se elimina el modificador synchronized de los metodos de CuentaCorriente:

1. Condiciones de carrera (Race Conditions): Varios hilos acceden y modifican la variable saldo de forma simultanea.

2. Perdida de actualizaciones (Lost Updates): Como existe un sleep() entre la lectura del saldo previo y la escritura del nuevo saldo, todos los hilos leen el mismo saldo inicial (1000.0) antes de que ninguno lo actualice. Al despertar, cada hilo sobrescribe el resultado de los demas, provocando que el saldo final sea incorrecto (por ejemplo, 1500.0 en lugar de 1850.0) y que el dinero de los otros ingresos se pierda.



4. Control de Errores Basico

- Se implementan bloques try-catch para gestionar InterruptedException tanto en las pausas Thread.sleep() como en join(), restaurando el estado de interrupcion mediante Thread.currentThread().interrupt().
