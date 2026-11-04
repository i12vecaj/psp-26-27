---
name: hilos-procesos-java
description: Ayuda a desarrollar, revisar y corregir programas Java 17 que usan procesos independientes con ProcessBuilder (entrada/salida estándar, waitFor, exitValue) e hilos con Thread y Runnable (datos propios por hilo, join, medición de tiempo concurrente, control de InterruptedException e IOException). Úsala siempre que el usuario pida o revise código Java con hilos, procesos, concurrencia, sincronización, comunicación entre procesos por stdin/stdout, códigos de salida, o ejercicios tipo "taquillas y auditor", aunque no mencione explícitamente ProcessBuilder. No usa librerías externas ni mecanismos avanzados (ExecutorService, synchronized, locks, etc.) salvo que se pidan.
---

# Skill: hilos-procesos-java

Reglas prácticas para escribir y revisar programas Java 17 con procesos e hilos, al nivel de lo trabajado en clase.

## Restricciones técnicas obligatorias

- Java 17, sin librerías externas (solo la biblioteca estándar).
- Paquete por defecto: **sin** declaración `package`.
- Cada clase ejecutable tiene su propio `main` (p. ej. `Teatro` y `Auditor`).
- Usar solo: `ProcessBuilder`, `Process`, `Thread`, `Runnable`, `join()`, `Thread.sleep()` y flujos de E/S estándar.
- No usar `ExecutorService`, `CompletableFuture`, `synchronized`, `Lock`, variables `atomic`, streams paralelos ni `Runtime.exec()`, salvo petición expresa. Si un diseño los necesitaría, rediseñarlo para que cada hilo use datos propios.
- Controlar siempre `InterruptedException` e `IOException`.

## Reglas verificables

### A. Procesos

**Regla 1. Lanzar procesos independientes con `ProcessBuilder`.**
- Hacer: `new ProcessBuilder("java", "Auditor")`, configurar y llamar a `start()`. Un argumento por elemento de la lista.
- Evitar: `Runtime.exec()`, concatenar el comando en un único `String` o ejecutar la lógica del proceso hijo como un hilo.

**Regla 2. Gestionar la entrada estándar del hijo con `getOutputStream()`.**
- Hacer: escribir los datos con un `BufferedWriter`/`PrintWriter` sobre `proceso.getOutputStream()`, una línea por dato (`newLine()`/`println`), hacer `flush()` y **cerrar** el flujo para que el hijo detecte el fin de entrada.
- Evitar: olvidar `flush()` o `close()` (el hijo se quedaría esperando) y confundir `getOutputStream()` (lo que el padre *escribe* al hijo) con `getInputStream()`.
- En el hijo: leer con `BufferedReader` sobre `System.in`, comprobar `null` y validar el formato.

**Regla 3. Recoger la salida y el código de finalización.**
- Hacer: leer toda la salida con `proceso.getInputStream()` (usar `redirectErrorStream(true)` si se quiere incluir los errores), después `int codigo = proceso.waitFor();` y, si procede, `proceso.exitValue()`. Mostrar el código.
- Evitar: llamar a `exitValue()` antes de que el proceso termine (lanza `IllegalThreadStateException`) y llamar a `waitFor()` sin haber consumido la salida cuando esta pueda ser grande (riesgo de bloqueo).
- El hijo termina con `System.exit(codigo)`; los códigos deben estar documentados.

### B. Hilos y concurrencia

**Regla 4. Crear los hilos con `Thread` y `Runnable`.**
- Hacer: una clase que `implements Runnable` y `new Thread(tarea, "Taquilla-1")`; lanzar con `start()`.
- Evitar: llamar a `run()` directamente (no crea un hilo nuevo) y extender `Thread` sin motivo.

**Regla 5. Cada hilo trabaja con sus propios datos.**
- Hacer: pasar al constructor del `Runnable` su cupo y guardar el resultado en un campo propio (p. ej. `private int vendidas`), con un getter que se consulta solo tras `join()`.
- Evitar: contadores `static` o variables compartidas modificables entre hilos. El programa principal suma los resultados individuales al final.

**Regla 6. Esperar con `join()` antes de leer resultados.**
- Hacer: guardar los hilos en un array, lanzar todos con `start()` en un primer bucle y hacer `join()` a todos en un segundo bucle, dentro de `try/catch (InterruptedException)`.
- Evitar: leer resultados antes del `join()`, hacer `start()` y `join()` en el mismo bucle (convierte la ejecución en secuencial) y usar `sleep()` en el principal para "esperar" a los hilos.

**Regla 7. Medir bien el tiempo concurrente.**
- Hacer: `long inicio = System.nanoTime();` justo antes del primer `start()` y `long fin = System.nanoTime();` justo después del último `join()`. Mostrar el tiempo en milisegundos: `(fin - inicio) / 1_000_000`.
- Evitar: medir dentro de un solo hilo, sumar los tiempos de cada hilo o incluir en la medición el lanzamiento del proceso `Auditor` y las impresiones.
- Esperable en el ejercicio: ~300 ms (el hilo más largo, 60 × 5 ms), no ~1000 ms (secuencial).

### C. Calidad

**Regla 8. Nombres descriptivos, excepciones controladas y código legible.**
- Hacer: nombres como `Taquilla`, `entradasVendidas`, `aforoTotal`; constantes (`static final`) en lugar de números mágicos; métodos cortos; mensajes de error claros.
- Hacer: capturar `InterruptedException` (restaurar con `Thread.currentThread().interrupt()` y avisar) e `IOException` (mensaje claro y terminar con código distinto de 0 si procede). Usar `try-with-resources` para cerrar flujos cuando sea posible.
- Evitar: `catch (Exception e) {}` vacíos, nombres de una letra (`t`, `x`), un único método gigante y código muerto.

## Ejercicio de referencia: Teatro y Auditor

Un teatro tiene 200 entradas repartidas en cinco taquillas con cupos 20, 30, 40, 50 y 60. Cada venta tarda `Thread.sleep(5)`.

Flujo del programa principal `Teatro`:
1. Crear 5 objetos `Taquilla` (cada uno con su cupo) y 5 `Thread`.
2. Anotar `inicio`, hacer `start()` a todos, hacer `join()` a todos y anotar `fin`.
3. Mostrar entradas vendidas por taquilla, total vendido y tiempo en ms.
4. Lanzar `Auditor` con `ProcessBuilder`, enviarle por stdin dos líneas (aforo y total vendido), cerrar la entrada.
5. Leer su salida, `waitFor()` y mostrar el código de salida.

Códigos de salida de `Auditor`:

| Código | Significado |
|--------|-------------|
| 0 | Total correcto (coincide con el aforo) |
| 1 | Total superior al aforo |
| 2 | Total inferior al aforo |
| 3 | Datos no válidos (faltan líneas, no son enteros o son negativos) |

### PLantilla orientativa

En `plantillaOrientativa.md` se muestra un ejemplo de implementación que cumple estas reglas, con la clase `Teatro` y la clase `Auditor`, incluyendo la gestión de hilos y procesos, la medición del tiempo y el manejo de excepciones.


## Lista de comprobación final

**Restricciones**
- [ ] Java 17, sin librerías externas y sin declaración `package`.
- [ ] `Teatro` y `Auditor` tienen su propio `main`; todo compila con `javac` sin avisos.
- [ ] No se usan mecanismos avanzados no trabajados en clase.

**Procesos**
- [ ] (R1) El `Auditor` se lanza con `ProcessBuilder` como proceso independiente.
- [ ] (R2) Se escriben aforo y total (dos líneas) por `getOutputStream()`, con `flush()` y cierre del flujo.
- [ ] (R3) Se lee la salida del hijo y se obtiene y muestra el código con `waitFor()`/`exitValue()`.
- [ ] El `Auditor` devuelve 0, 1, 2 o 3 según la tabla y valida `null`, formato y negativos.

**Hilos y concurrencia**
- [ ] (R4) Cada taquilla es un `Runnable` ejecutado en su propio `Thread` con `start()` (nunca `run()`).
- [ ] (R5) No hay variables `static` ni objetos modificables compartidos entre hilos.
- [ ] (R6) Todos los hilos se lanzan antes de hacer el primer `join()`, y los resultados se leen solo después de los `join()`.
- [ ] (R7) El tiempo se mide desde antes del primer `start()` hasta después del último `join()` (≈300 ms, no ≈1000 ms).
- [ ] Cada venta usa `Thread.sleep(5)` y la suma de cupos es 200.

**Calidad**
- [ ] (R8) Nombres descriptivos, constantes en lugar de números mágicos y código ordenado.
- [ ] `InterruptedException` e `IOException` están capturadas con mensajes claros, sin `catch` vacíos.
- [ ] Se muestran: vendidas por taquilla, total vendido, tiempo empleado y código de salida del Auditor.
