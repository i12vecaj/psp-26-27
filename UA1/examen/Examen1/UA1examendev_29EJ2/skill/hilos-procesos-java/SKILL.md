---
name: hilos-procesos-java
description: Reglas para escribir programas Java 17 con hilos (Thread/Runnable, join) y procesos independientes (ProcessBuilder, streams, códigos de salida). Úsala siempre que se pida generar, revisar o corregir código Java que lance procesos hijos o ejecute tareas concurrentes en hilos.
---

# Hilos y procesos en Java

## Reglas de procesos

1. **Proceso hijo con ProcessBuilder.** Los procesos externos se lanzan con `ProcessBuilder` (no `Runtime.exec`). Para ejecutar otra clase Java se usa el `java` del propio entorno (`System.getProperty("java.home") + "/bin/java"`) y el classpath actual (`System.getProperty("java.class.path")`), no una ruta fija.
2. **Comunicación por streams.** Los datos al hijo se envían por su entrada estándar (`process.getOutputStream()`), escribiendo una línea por dato, con `flush()` y cerrando el stream al terminar. La respuesta se lee de `process.getInputStream()`. El canal de error del hijo no se mezcla con su salida estándar (no usar `redirectErrorStream(true)` si la salida mostrada debe ser exacta): se redirige con `redirectError(ProcessBuilder.Redirect.INHERIT)`, de modo que el hijo no se bloquea y la salida leída contiene solo lo que escribe el hijo por stdout.
3. **Esperar y leer el código de salida.** Tras leer la salida se llama a `waitFor()` y se usa `exitValue()` / el valor devuelto como código del proceso. El código de salida del hijo se asigna con `System.exit(n)`, con un código distinto por cada resultado posible.
4. **Entrada validada en el hijo.** El proceso hijo valida lo que lee por `System.in` (nulos, texto no numérico, negativos) y devuelve un código de error propio en lugar de lanzar una excepción sin controlar.

## Reglas de hilos y concurrencia

5. **Hilos con datos propios.** Cada hilo es un `Thread` (o `Runnable`) con sus propios campos; no se comparten variables modificables entre hilos. El resultado se guarda en un campo del propio hilo (privado, con getter).
6. **Lanzar todos antes de esperar.** Primero se hacen `start()` de todos los hilos y después `join()` de todos, en dos bucles separados. Un `start()` seguido de `join()` dentro del mismo bucle serializa el trabajo.
7. **`start()`, nunca `run()`.** Se arranca con `start()`; llamar a `run()` ejecuta en el hilo actual y no hay concurrencia.
8. **Leer el resultado solo tras `join()`.** El programa principal lee el resultado de un hilo únicamente cuando `join()` ha terminado; `join()` garantiza la visibilidad de los datos escritos por ese hilo.
9. **Pausas con `Thread.sleep` y `InterruptedException`.** La espera simulada se hace con `Thread.sleep(ms)` dentro de un `try/catch` de `InterruptedException`; al capturarla se restaura la interrupción (`Thread.currentThread().interrupt()`) y se termina el trabajo del hilo.
10. **Medición de tiempo correcta.** El tiempo se mide con `System.nanoTime()` justo antes de los `start()` y justo después del último `join()`, y se muestra en milisegundos.

## Reglas de calidad

11. **Código limpio y entrega exacta.** Java 17, sin librerías externas y sin paquete si así se pide; nombres claros, constantes en lugar de números mágicos y la salida con el formato exacto solicitado (ni líneas ni texto de más).
12. **Verificar ejecutando.** El código se compila y se ejecuta varias veces comprobando resultado y tiempo antes de darlo por bueno.

## Lista de comprobación final

- [ ] Compila con `javac` en Java 17 y no usa librerías externas ni `package`.
- [ ] Los procesos se lanzan con `ProcessBuilder` y se usa el `java` y el classpath del entorno.
- [ ] Se envían los datos por la entrada estándar del hijo, con `flush()` y cierre del stream.
- [ ] Se lee la salida del hijo, se hace `waitFor()` y se muestra su código de salida.
- [ ] El canal de error del hijo está redirigido aparte (`Redirect.INHERIT`) y no contamina su salida estándar.
- [ ] El hijo valida la entrada y usa un código de salida distinto por caso.
- [ ] Todos los hilos usan datos propios; no hay variables compartidas modificables.
- [ ] Todos los `start()` van antes de los `join()` (bucles separados).
- [ ] Nunca se llama a `run()` directamente.
- [ ] Los resultados se leen solo tras `join()`.
- [ ] `InterruptedException` está tratada (interrupción restaurada).
- [ ] El tiempo se mide desde antes del primer `start()` hasta después del último `join()`.
- [ ] La salida respeta el formato exacto del enunciado.
- [ ] Se ha ejecutado al menos 3 veces con resultados coherentes.
