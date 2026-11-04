---
name: hilos-procesos-java
description: Reglas para escribir programas Java 17 (sin librerías externas y sin paquete) que usan hilos (Thread, join, datos propios por hilo) y procesos independientes (ProcessBuilder, entrada/salida estándar, códigos de salida). Úsala siempre que haya que generar, revisar o corregir código Java que lance hilos concurrentes o que ejecute otro programa Java como proceso hijo.
---

# Skill: hilos y procesos en Java

Aplica todas las reglas. Cada una se puede comprobar leyendo el código o ejecutándolo.

## Reglas de procesos

1. **P1 · Lanzar el hijo con ProcessBuilder y el mismo Java.** El proceso hijo se crea con `ProcessBuilder`, nunca con `Runtime.exec(String)`. El ejecutable es `System.getProperty("java.home") + File.separator + "bin" + File.separator + "java"` y se pasa `-cp System.getProperty("java.class.path")` para que el hijo encuentre sus clases. Cada argumento va en un elemento separado de la lista.
2. **P2 · Comunicar por la entrada estándar y cerrarla.** Los datos se envían al hijo escribiendo en `process.getOutputStream()` (con un `BufferedWriter`, que lanza `IOException` si falla, en vez de un `PrintWriter`, que oculta los errores; un dato por línea) y se **cierra** ese flujo tras escribir, para que el hijo reciba fin de entrada y no se quede bloqueado.
3. **P3 · Leer toda la salida antes de `waitFor()`.** Se lee completa la salida del hijo (`getInputStream()`) y después se llama a `waitFor()` para obtener el código de salida con su valor real. Si hay un error en la comunicación, se llama a `destroy()` para no dejar el hijo vivo. Los errores del hijo no se pierden: se redirigen (`redirectError(ProcessBuilder.Redirect.INHERIT)` o `redirectErrorStream(true)`).
4. **P4 · Códigos de salida con significado.** El proceso hijo termina con `System.exit(codigo)` usando códigos documentados (0 = correcto, distinto de 0 = cada tipo de fallo). Los datos de entrada que no se puedan interpretar (vacíos, ausentes, no numéricos, negativos) producen un código propio, nunca una excepción sin capturar.

## Reglas de hilos y concurrencia

5. **H1 · Datos propios por hilo.** Cada hilo trabaja con su propio objeto (`Runnable` o subclase de `Thread`) y sus propios campos. No hay campos `static` modificables ni objetos mutables compartidos entre hilos. Ojo: `final` solo fija la referencia, no el contenido (un array `static final` se puede modificar), así que a cada hilo se le pasan valores primitivos o copias, por constructor y en campos `final`.
6. **H2 · Arrancar todos y luego esperar.** Primero se llama a `start()` sobre **todos** los hilos en un bucle y después, en otro bucle, a `join()` sobre cada uno. Nunca `start()` + `join()` dentro del mismo bucle (eso los ejecuta en serie) y nunca `run()` directamente.
7. **H3 · Leer resultados solo tras `join()`.** El hilo principal consulta el resultado de cada hilo únicamente después de que su `join()` haya terminado; `join()` garantiza que los cambios del hilo son visibles.
8. **H4 · Gestionar `InterruptedException`.** No se ignora con un `catch` vacío: se restaura la marca con `Thread.currentThread().interrupt()` y se detiene el trabajo (salir del bucle o terminar el programa con un mensaje de error).
9. **H5 · Medir el tiempo con `System.nanoTime()`.** Se toma el instante justo antes del primer `start()` y justo después del último `join()`; con hilos realmente concurrentes el tiempo se parece al del hilo más lento, no a la suma.

## Reglas de calidad

10. **C1 · Salida exacta y recursos cerrados.** La salida normal contiene solo las líneas pedidas, en el orden pedido y sin mensajes extra (los errores van a `System.err`). Los flujos se abren con `try-with-resources`. Código sencillo: clases sin paquete, nombres en español claros, sin bibliotecas externas y compatible con `javac --release 17 -encoding UTF-8`.

## Lista de comprobación final

- [ ] El hijo se lanza con `ProcessBuilder`, ruta de `java.home` y `-cp` con `java.class.path` (P1).
- [ ] Se escriben los datos en la entrada del hijo y se cierra el flujo (P2).
- [ ] Se lee toda la salida del hijo antes de `waitFor()`, se muestra el código real y se hace `destroy()` si hay error (P3).
- [ ] El hijo devuelve todos los códigos documentados, incluido el de datos no válidos (P4).
- [ ] Ningún campo mutable es compartido entre hilos; no hay `static` modificables (H1).
- [ ] Hay un bucle de `start()` y otro distinto de `join()`; nunca se llama a `run()` (H2).
- [ ] Los resultados se leen solo después de `join()` (H3).
- [ ] Ningún `catch (InterruptedException)` está vacío; se llama a `interrupt()` (H4).
- [ ] El tiempo medido se acerca al del hilo más lento en varias ejecuciones (H5).
- [ ] La salida coincide exactamente con el formato pedido y compila con `--release 17 -encoding UTF-8` (C1).
