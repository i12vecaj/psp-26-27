---
name: hilos-procesos-java
description: Reglas verificables para programar en Java 17 aplicaciones que combinan hilos concurrentes (Thread/Runnable, start, join, sleep, aislamiento de datos) y procesos independientes lanzados con ProcessBuilder que se comunican por stdin, stdout y código de salida. Úsala siempre que haya que crear, revisar o corregir un programa Java con varios hilos, con un proceso hijo Java, o con ambos (p. ej. ejercicios de PSP de procesos e hilos), antes de escribir el código y otra vez antes de darlo por terminado.
---

# hilos-procesos-java

Skill para desarrollar y revisar programas Java 17 (sin librerías externas) que usan
**hilos** para trabajo concurrente dentro de un proceso y **procesos** independientes
para delegar trabajo en otro programa. Cada regla tiene un identificador y una forma
concreta de comprobarla en el código o en la ejecución.

Las reglas parten de lo trabajado en clase (apuntes de `ProcessBuilder` y de la clase
`Thread`, ejemplos de procesos de la UA1 y `HilosBasicos` de la UA2). Cada una indica en
**Visto en clase** de dónde sale y, cuando la regla es más estricta que el ejemplo, por qué.

Modo de uso:
1. Leer todas las reglas antes de escribir código.
2. Escribir el código aplicándolas.
3. Compilar, ejecutar y recorrer la **Lista de comprobación final**. Una casilla que no se
   pueda verificar se deja sin marcar y se indica el motivo.

---

## A. Reglas de procesos

### P1. El proceso hijo se lanza con `ProcessBuilder` y sin rutas fijas
- El comando se construye como una **lista de argumentos** (no como una cadena que haya que
  trocear):
  `Path.of(System.getProperty("java.home"), "bin", "java")`, `"-cp"`,
  `System.getProperty("java.class.path")`, `"NombreClase"`.
- Prohibido: `Runtime.exec`, rutas absolutas de un equipo concreto (`C:\...`, `/home/...`),
  depender de que `java` esté en el `PATH`.
- Prohibido llamar a `OtraClase.main(...)` desde el padre: eso ejecuta el código en el
  **mismo** proceso y la misma JVM, no en un proceso independiente.
- **Cómo verificar:** `grep -n "ProcessBuilder\|java.home\|java.class.path"` aparece en el padre;
  `grep -n "\.main(\|Runtime.getRuntime"` no aparece.
- **Visto en clase:** el Ejemplo 4 (UA1) lanza otro programa Java con
  `new ProcessBuilder("java", ...)` y `pb.directory(new File("bin"))`. Eso depende del `PATH`
  y de la carpeta desde la que se ejecuta; por eso aquí se usan `java.home` y `java.class.path`.

### P2. Los datos se envían por la entrada estándar del hijo y luego se cierra
- El padre escribe en `process.getOutputStream()` (que es la `stdin` del hijo), **una línea
  por dato**, en el orden acordado.
- El flujo se cierra (try-with-resources) **antes** de leer la respuesta. Cerrar envía EOF y
  vacía el buffer; sin `close()`/`flush()` el hijo puede quedarse esperando en `readLine()`
  y el padre esperando su salida: **interbloqueo**.
- Los datos no se pasan como argumentos de línea de comandos si el enunciado pide `stdin`.
- **Cómo verificar:** existe `getOutputStream()` dentro de un `try (...)` y el bloque termina
  antes de la llamada a `getInputStream()`.
- **Visto en clase:** el Ejemplo 4 escribe en `p.getOutputStream()` y hace `flush()` para que
  `EjemploLectura` reciba la línea. Si el hijo lee hasta fin de datos, además hay que cerrar.

### P3. La salida del hijo se consume entera antes de `waitFor()` y `stderr` no se ignora
- El padre lee `process.getInputStream()` (que es la `stdout` del hijo) hasta EOF y **después**
  llama a `waitFor()`. Si se espera primero y el hijo llena el buffer de la tubería, ambos
  se bloquean.
- `stderr` se redirige (`redirectError(ProcessBuilder.Redirect.INHERIT)`) o se lee; nunca se
  deja una tubería sin consumir.
- Al leer y escribir en las tuberías se usa un **charset explícito** e idéntico en ambos
  extremos (o texto ASCII), porque desde Java 18 el charset por defecto (UTF-8) puede no
  coincidir con el de la consola de Windows.
- **Cómo verificar:** orden en el código: escribir → cerrar stdin → leer stdout → `waitFor()`.
- **Visto en clase:** los apuntes de `ProcessBuilder` explican que stdin, stdout y stderr del
  hijo son tuberías (`getOutputStream`, `getInputStream`, `getErrorStream`) y que se pueden
  redirigir con `redirectError`/`Redirect.INHERIT` (Ejemplos 5 y 8). Los Ejemplos 2Error y 4
  leen `getErrorStream()`.

### P4. El resultado se comunica con el código de salida y el padre lo recoge
- El hijo termina con `System.exit(codigo)` usando **constantes con nombre** que documentan
  cada código (p. ej. `CODIGO_CORRECTO = 0`).
- El padre obtiene el código con `int codigo = process.waitFor();` y lo muestra o actúa en
  función de él; **no** deduce el resultado analizando el texto de la salida.
- **Cómo verificar:** `waitFor()` asigna su valor a una variable que se usa después.
- **Visto en clase:** `exitVal = p.waitFor()` en los Ejemplos 2, 3 y 4 ("0 bien, 1 mal").
  `LeerNombre` termina a propósito con `System.exit(1)`.

### P5. El hijo valida su entrada y nunca termina por una excepción no capturada
- Debe tratar como dato inválido: línea ausente (`readLine()` devuelve `null`), línea vacía,
  texto no convertible (`NumberFormatException`) y valores negativos.
- Una excepción no capturada hace que la JVM termine con código **1**, que podría coincidir
  con un código de negocio y engañar al padre. Todo error de entrada se traduce al código
  de "datos inválidos" previsto.
- **Cómo verificar:** ejecutar el hijo a mano con entradas `abc`, vacía, negativa y con una sola
  línea; en todos los casos debe devolver el código de datos inválidos.
- **Visto en clase:** el Ejemplo 2Error ejecuta un comando erróneo (`DIRR`) y comprueba que el
  código de salida es distinto de 0. Aquí cada tipo de error tiene su código propio.

---

## B. Reglas de hilos y concurrencia

### H1. Cada hilo trabaja con su propio objeto y sus propios datos
- Se define una clase de tarea (`implements Runnable` o `extends Thread`) con **campos de
  instancia** propios: identificador, cupo (`final`) y contador (`private`).
- Se crea **un objeto de tarea distinto por hilo**. Ninguna variable modificable es
  compartida entre hilos; está prohibido acumular en campos `static` (`static int total`).
- **Cómo verificar:** `grep -n "static int\|static long"` no encuentra contadores mutables; cada
  `new Thread(...)` recibe un `new Tarea(...)` diferente.
- **Visto en clase:** en `BloquesNoSincronizados` dos hilos comparten un único `Contador` y el
  valor final cambia entre ejecuciones (condición de carrera). Esta regla elimina el problema
  de raíz: si no hay objeto compartido, no hay carrera.

### H2. Primero se arrancan todos los hilos con `start()`, luego se espera a todos con `join()`
- Un bucle hace `start()` de todos los hilos; **otro bucle posterior** hace `join()`.
- Prohibido `hilo.start(); hilo.join();` dentro del mismo bucle: convierte la ejecución en
  secuencial (el tiempo total pasaría a ser la suma).
- Prohibido llamar a `run()` directamente: se ejecuta en el hilo actual, no en uno nuevo.
- **Cómo verificar:** no hay `join()` en el bucle de `start()`; `grep -n "\.run()"` no aparece.
- **Visto en clase:** `EjemploHilosRunnable` usa exactamente esta estructura (un bucle de
  `start()` y otro de `join()`) y propone como ejercicio "meted el `join()` en el mismo bucle
  que el `start()`… ver qué pasa": los hilos pasan a ejecutarse uno detrás de otro.

### H3. Los resultados de un hilo se consultan solo después de su `join()`
- El padre llama a `getVendidas()` (o equivalente) únicamente después de que `join()` de ese
  hilo haya retornado. `join()` garantiza en el modelo de memoria de Java una relación
  *happens-before*: todo lo que escribió el hilo es visible para el que espera, sin
  `volatile` ni `synchronized`.
- **Cómo verificar:** el bucle de lectura de resultados aparece después del bucle de `join()`.
- **Visto en clase:** los apuntes de la clase `Thread` explican que `join()` hace esperar al hilo
  actual hasta que otro termina, y que `isAlive()` sirve para comprobarlo después. En el ejemplo
  `EjemploJoin`, `main` espera con `h1.join(); h2.join(); h3.join();` antes de continuar.

### H4. No se añade sincronización innecesaria
- Si los hilos no comparten datos modificables (H1), **no hay condición de carrera** y no se
  usa `synchronized`, `volatile`, `AtomicInteger`, `Lock` ni colecciones concurrentes.
  La corrección viene del aislamiento, no de los cerrojos.
- Si en algún momento hiciera falta compartir datos, entonces sí se sincroniza, y se
  justifica en un comentario.
- **Cómo verificar:** `grep -n "synchronized\|volatile\|Atomic\|Lock"` no encuentra nada en un
  diseño con datos aislados.
- **Visto en clase:** `BloquesSincronizados` y `MetodosSincronizados` usan `synchronized`
  porque **sí** comparten un `Contador`. Sin objeto compartido, ese cerrojo solo añade coste.

### H5. `Thread.sleep()` simula el trabajo y la interrupción se trata correctamente
- La pausa se hace **dentro del bucle**, una vez por cada unidad de trabajo
  (`Thread.sleep(5)` por cada venta), no una sola vez por hilo.
- `InterruptedException` no se ignora con un `catch` vacío: se restaura el estado con
  `Thread.currentThread().interrupt()` y se termina la tarea de forma ordenada.
- **Cómo verificar:** `Thread.sleep(` está dentro del `for`/`while`; el `catch` contiene
  `interrupt()`.
- **Visto en clase:** apuntes de `interrupt()` (si el hilo está en `sleep` salta
  `InterruptedException`) y `HiloEjemploInterrup`. Los ejemplos simples dejan el `catch`
  vacío ("Nothing to do here"); en código entregable eso no se admite.

### H6. El tiempo concurrente se mide con `System.nanoTime()` alrededor de start/join
- `inicio = System.nanoTime()` justo **antes** del bucle de `start()`; `fin = System.nanoTime()`
  justo **después** del último `join()`. Se convierte a milisegundos
  (`(fin - inicio) / 1_000_000`).
- No se usa `System.currentTimeMillis()` (reloj de pared, puede saltar) para medir duraciones.
- El tiempo obtenido debe parecerse al de la **tarea más larga**, no a la suma de todas. Si se
  acerca a la suma, los hilos no se están ejecutando en paralelo (revisar H2).
- **Cómo verificar:** ejecutar varias veces y comparar el tiempo con el máximo y con la suma
  teóricos.
- **Visto en clase:** la clase `ExecutionTimer` de `BloquesNoSincronizados` mide con
  `System.nanoTime()` antes del `start()` y después del `join()`, y divide entre 1000000.

---

## C. Reglas de calidad

### C1. Responsabilidad única por clase
- La tarea del hilo solo hace su trabajo y guarda su resultado; la clase principal
  coordina (crea hilos, espera, suma, imprime, lanza el proceso); el proceso hijo solo
  valida y emite su veredicto.
- Los métodos son cortos y con nombre descriptivo (`lanzarAuditor`, `leerEntero`...).

### C2. La salida estándar contiene exactamente lo que pide el enunciado
- Sin mensajes de depuración, sin líneas extra, sin cambiar textos (incluidos acentos y
  mayúsculas). Los mensajes de error van a `System.err`.
- Los resultados se muestran en el orden fijado (p. ej. tarea 1..N), independientemente del
  orden en que terminen los hilos.
- **Cómo verificar:** comparar la salida real línea a línea con la del enunciado.

### C3. Java 17 estándar, sin dependencias y sin números mágicos
- Sin `package` si se pide paquete por defecto, sin librerías externas, sin API posterior a
  Java 17: debe compilar con `javac --release 17` sin errores ni avisos.
- Los datos de configuración (aforo, cupos) son constantes con nombre; los totales se
  **calculan** sumando resultados reales, nunca se escriben a mano.
- Excepción: si el enunciado exige una instrucción literal (p. ej. `Thread.sleep(5)`), se
  escribe tal cual, para que se pueda comprobar a simple vista, con un comentario que lo explique.
- Los errores no se silencian: un `catch` informa por `System.err` y termina con un código
  distinto de 0, o restaura la interrupción (H5).

---

## D. Lista de comprobación final

Procesos
- [ ] P1 El hijo se lanza con `ProcessBuilder`, usando `java.home` y `java.class.path`.
- [ ] P1 No se llama a `Hijo.main()` ni se usan rutas absolutas o `Runtime.exec`.
- [ ] P2 Los datos van por `stdin`, una línea por dato, y el flujo se cierra antes de leer.
- [ ] P3 Se lee `stdout` completo antes de `waitFor()`; `stderr` está redirigido o consumido.
- [ ] P3 Charset explícito, o texto ASCII, en la comunicación por tuberías.
- [ ] P4 El código de salida se obtiene con `waitFor()` y se muestra o usa.
- [ ] P5 El hijo devuelve el código de "datos inválidos" para entrada ausente, vacía, no
      numérica o negativa (probado a mano).

Hilos
- [ ] H1 Un objeto de tarea por hilo, con cupo y contador propios; sin `static` mutable.
- [ ] H2 Bucle de `start()` separado del bucle de `join()`; ningún `run()` directo.
- [ ] H3 Los resultados se leen después de `join()`.
- [ ] H4 Sin `synchronized`/`volatile`/`Atomic*`/`Lock` innecesarios.
- [ ] H5 `Thread.sleep()` dentro del bucle de trabajo; `InterruptedException` restaurada.
- [ ] H6 `System.nanoTime()` antes del primer `start()` y después del último `join()`;
      tiempo ≈ máximo, no la suma.

Calidad
- [ ] C1 Cada clase tiene una única responsabilidad.
- [ ] C2 La salida coincide exactamente con el enunciado, en el orden pedido.
- [ ] C3 Compila con `javac --release 17` sin avisos; sin `package` ni dependencias; sin
      números mágicos (salvo los literales que exige el enunciado); ningún `catch` vacío.

Ejecución
- [ ] Compilado y ejecutado realmente, al menos tres veces, guardando la salida.
- [ ] Resultados constantes en todas las ejecuciones (total correcto, código esperado).
