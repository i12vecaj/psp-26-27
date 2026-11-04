---
name: hilos-procesos-java
description: Guia para escribir y revisar programas Java 17 que combinan hilos (Thread) y procesos externos (ProcessBuilder). Usar cuando se pida lanzar tareas concurrentes, esperar a que terminen, medir tiempos, o ejecutar y comunicarse con un proceso independiente mediante su entrada/salida estandar y su codigo de salida.
---

# Skill: hilos y procesos en Java

Aplica estas reglas al generar o revisar codigo. Cada regla es verificable
leyendo el codigo o ejecutandolo.

## Reglas de procesos

- **P1. Lanzar con `ProcessBuilder` y argumentos separados.** Nada de
  `Runtime.exec(String)` ni de componer un comando en una sola cadena. Cada
  argumento es un elemento distinto de la lista.
- **P2. Ejecutable y classpath portables.** Para lanzar otra clase Java, usar
  `System.getProperty("java.home")` (carpeta `bin`) y
  `System.getProperty("java.class.path")`; no escribir rutas absolutas ni
  asumir Windows o Linux.
- **P3. Comunicacion por flujos estandar.** Los datos se envian por la entrada
  estandar del proceso hijo (`getOutputStream()`), **cerrando el flujo** al
  terminar de escribir, y se lee su salida (`getInputStream()`) con codificacion
  explicita (UTF-8). La salida de error del hijo no se mezcla con su salida
  estandar: se redirige con `Redirect.INHERIT` para que no bloquee ni ensucie
  el veredicto que se muestra.
- **P4. Esperar y leer el codigo de salida con control.** Usar
  `waitFor(timeout, unidad)`; si vence el plazo, `destroyForcibly()`. El codigo
  de salida se obtiene con `exitValue()` y se muestra tal cual, sin
  reinterpretarlo.
- **P5. El hijo valida sus entradas.** Un proceso que lee datos comprueba
  `null`, formato numerico y rangos, y devuelve un codigo de salida distinto
  para "datos no validos" en lugar de lanzar una excepcion sin controlar.

## Reglas de hilos y concurrencia

- **H1. Cada hilo trabaja con sus propios datos.** Los campos que modifica son
  `private` y solo los escribe ese hilo. Prohibido compartir variables
  modificables entre hilos (ni contadores estaticos ni `synchronized` para
  arreglarlo si el diseno puede evitar compartir).
- **H2. Arrancar todos y despues esperar.** Primero `start()` en todos los hilos
  (bucle propio) y despues `join()` en todos (otro bucle). Nunca `join()`
  dentro del bucle de `start()`: eso serializa el trabajo.
- **H3. Usar `start()`, nunca `run()`.** Llamar a `run()` ejecuta en el hilo
  actual y no hay concurrencia.
- **H4. Leer resultados solo tras `join()`.** El hilo principal consulta el
  resultado de un hilo unicamente cuando este ha terminado; `join()` garantiza
  la visibilidad de sus datos.
- **H5. Tratar `InterruptedException` correctamente.** En `sleep` y `join`, no
  dejar el `catch` vacio: restaurar la interrupcion con
  `Thread.currentThread().interrupt()` y terminar la tarea de forma ordenada.
- **H6. Medir con `System.nanoTime()`** inmediatamente antes del primer
  `start()` y despues del ultimo `join()`, y convertir a milisegundos. Con
  tareas concurrentes el tiempo debe parecerse al de la tarea mas lenta, no a
  la suma de todas.

## Reglas de calidad

- **Q1. Cumplir el formato de salida exacto del enunciado** (textos, orden,
  espacios) y los requisitos tecnicos: Java 17, sin librerias externas, paquete
  por defecto, nombres de clase pedidos.
- **Q2. Sin numeros magicos.** Aforo, cupos y duracion de cada venta son
  constantes con nombre; los nombres de clases, metodos y variables son claros.
- **Q3. Cerrar recursos** con `try-with-resources` y comentar solo lo que no es
  evidente.

## Lista de comprobacion final

- [ ] Compila con `javac` en Java 17, sin paquete y sin librerias externas.
- [ ] Las clases se llaman exactamente como pide el enunciado y cada
      proceso independiente tiene su propio `main`.
- [ ] Los hilos usan `start()`; hay un bucle de `start()` y otro de `join()`.
- [ ] Ningun dato modificable se comparte entre hilos.
- [ ] El hilo principal lee los resultados despues del `join()`.
- [ ] El proceso hijo se lanza con `ProcessBuilder`, argumentos separados,
      `java.home` y `java.class.path`.
- [ ] Se escribe en la entrada del hijo y se cierra; se lee su salida; se usa
      `waitFor` con tiempo limite.
- [ ] Se muestra el codigo de salida y el hijo cubre todos los codigos pedidos.
- [ ] `InterruptedException` tratada con `interrupt()`; recursos cerrados.
- [ ] Se ejecuto al menos 3 veces: total correcto y tiempo cercano al de la
      tarea mas lenta, no a la suma.
- [ ] Salida con el formato y orden exactos del enunciado.
- [ ] No hay `.class`, ficheros de IDE ni carpetas del agente en la entrega.
