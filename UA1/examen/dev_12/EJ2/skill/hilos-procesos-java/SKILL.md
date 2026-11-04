---
name: hilos-procesos-java
description: Reglas para escribir programas Java 17 que combinan hilos (Thread, Runnable, join) y procesos independientes (ProcessBuilder, entrada/salida estándar, códigos de salida). Úsala siempre que haya que crear, revisar o corregir código Java con concurrencia en hilos o que lance otros procesos, sobre todo en ejercicios de clase de Programación de Servicios y Procesos.
---

# Hilos y procesos en Java

## Contexto
Java 17, sin librerías externas, paquete por defecto, salvo que el enunciado diga otra cosa. Cada clase ejecutable tiene su propio `main`.

## Reglas sobre procesos (P)

- **P1. Lanzar procesos con `ProcessBuilder`, nunca con `Runtime.exec`.** Verificable: no aparece `Runtime.getRuntime().exec` en el código.
- **P2. No escribir la ruta de `java` a mano.** Se obtiene de `System.getProperty("java.home") + File.separator + "bin" + File.separator + "java"`, y el classpath de `System.getProperty("java.class.path")`. Verificable: no hay rutas fijas del tipo `/usr/bin/java` o `C:\...`.
- **P3. Comunicar con el proceso hijo por su entrada y salida estándar.** Los datos se escriben en `process.getOutputStream()` y se cierra el flujo (o se hace `flush`) para que el hijo vea el final de la entrada. La respuesta se lee de `process.getInputStream()`. Verificable: el hijo lee con `Scanner`/`BufferedReader` sobre `System.in` y el padre cierra el flujo de escritura.
- **P4. Esperar al hijo con `waitFor()` y usar su código de salida con `exitValue()`.** El código de salida se muestra o se usa; nunca se ignora. Verificable: aparece `waitFor()` y el valor devuelto se imprime o se evalúa.
- **P5. El hijo termina con `System.exit(código)` y cada código tiene un significado documentado.** Verificable: hay una tabla o comentarios con el significado de cada código y el programa solo usa esos códigos.
- **P6. Leer la salida del hijo antes de (o a la vez que) `waitFor()`.** Si no se lee, el buffer puede llenarse y el hijo bloquearse. Verificable: el bucle de lectura de `getInputStream()` va antes de `waitFor()`, o se redirige con `redirectErrorStream(true)` y se lee igualmente.

## Reglas sobre hilos y concurrencia (H)

- **H1. Cada hilo trabaja con sus propios datos.** Los campos que escribe el hilo son suyos; no se comparten variables modificables entre hilos. Verificable: ningún campo `static` modificable ni ningún objeto mutable accedido por dos hilos a la vez.
- **H2. Lanzar todos los hilos antes de esperar a ninguno.** Primero todos los `start()`, después todos los `join()`. Si se hace `start()` y `join()` hilo a hilo, el programa es secuencial. Verificable: en el código hay un bucle de `start()` completo y después un bucle de `join()`.
- **H3. Usar `start()`, nunca `run()`, para arrancar un hilo.** Llamar a `run()` ejecuta el código en el hilo actual. Verificable: no hay llamadas `.run()` sobre un `Thread`.
- **H4. Esperar con `join()` y solo entonces leer el resultado.** El hilo principal no lee los datos de un hilo hasta que este ha terminado con `join()`; `join()` establece la relación *happens-before* que hace visibles los datos. Verificable: toda lectura de resultados está después del `join()` correspondiente.
- **H5. Tratar `InterruptedException` correctamente.** En `sleep` y `join` se captura la excepción, se restaura el estado con `Thread.currentThread().interrupt()` y el hilo termina o propaga. Verificable: no hay `catch` vacíos.
- **H6. Medir el tiempo con `System.nanoTime()`.** Se toma justo antes del primer `start()` y justo después del último `join()`, y se convierte a milisegundos. Verificable: se usa `nanoTime` y no `currentTimeMillis` para intervalos.
- **H7. No usar `synchronized`, `volatile` ni estructuras concurrentes si el diseño no las necesita.** Si se cumple H1, sobran. Verificable: solo aparecen si hay un dato realmente compartido y se justifica en un comentario.

## Reglas de calidad (Q)

- **Q1. Código claro y comentado lo justo.** Nombres en español descriptivos, constantes con nombre (`AFORO`, `TIEMPO_VENTA_MS`) en lugar de números sueltos, y un comentario breve por clase y por bloque no obvio. Verificable: no hay números mágicos repetidos y cada clase tiene su comentario.
- **Q2. Respetar el formato de salida del enunciado al carácter.** Mismos textos, mismo orden, sin líneas extra. Verificable: se compara la salida real con la pedida línea a línea.
- **Q3. Validar los datos de entrada y devolver un código de error, sin lanzar excepciones sin controlar.** Verificable: se prueba con entrada vacía, texto no numérico y números negativos.

## Lista de comprobación final

- [ ] Compila con `javac` sin avisos en Java 17 y sin paquete.
- [ ] Los procesos se lanzan con `ProcessBuilder` y sin rutas fijas (P1, P2).
- [ ] El padre escribe en el hijo, cierra el flujo, lee su salida y llama a `waitFor()` (P3, P4, P6).
- [ ] Los códigos de salida están documentados y se prueban todos (P5).
- [ ] Todos los `start()` van antes de todos los `join()` (H2) y no se usa `run()` (H3).
- [ ] No hay variables modificables compartidas entre hilos (H1, H7).
- [ ] Los resultados se leen solo después del `join()` (H4).
- [ ] `InterruptedException` tratada, sin `catch` vacíos (H5).
- [ ] Tiempo medido con `nanoTime` y coherente con el paralelismo esperado (H6).
- [ ] Salida idéntica a la del enunciado (Q2).
- [ ] Entradas inválidas probadas (Q3).
- [ ] Ejecutado al menos 3 veces con resultados consistentes.
