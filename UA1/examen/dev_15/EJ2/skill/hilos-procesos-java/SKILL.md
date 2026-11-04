---
name: hilos-procesos-java
description: Reglas para programar en Java 17 con hilos y procesos, sin librerias externas. Usar cuando haya que crear o revisar programas con Thread, Runnable, join, ProcessBuilder o Process, por ejemplo varios hilos trabajando a la vez o un proceso padre que se comunica con un proceso hijo.
---

# Hilos y procesos en Java

Sigue estas reglas al escribir o revisar el codigo. Si un requisito de la tarea choca con una regla, manda el requisito y se avisa de ello.

## Reglas de procesos

1. **P1. Lanzar sin shell.** El proceso se lanza con `ProcessBuilder`, con cada argumento en un elemento distinto y sin `cmd /c` ni `sh -c`. Para otra clase Java se usa `"java", "-cp", System.getProperty("java.class.path"), "NombreClase"`.
2. **P2. Enviar y cerrar.** Los datos para el hijo se escriben en `getOutputStream()` y despues se cierra el flujo. Se comprueba que el `close()` se hace siempre, porque sin el un hijo que lee hasta el final se queda esperando.
3. **P3. Leer y despues esperar.** Sin limite de tiempo, primero se lee `getInputStream()` hasta que `readLine()` devuelva `null` y despues se llama a `waitFor()`. Al reves el padre se puede bloquear si el hijo llena la tuberia.
4. **P4. No olvidar la salida de error.** La salida de error no se deja sin destino: se redirige a un fichero o con `Redirect.INHERIT`, o se lee.
5. **P5. Codigo de salida real.** El resultado del hijo se saca del valor de `waitFor()`, nunca se supone. El hijo termina con `System.exit(n)` usando un codigo distinto para cada caso y valida los datos que recibe.

## Reglas de hilos y concurrencia

6. **H1. Lanzar con start.** Los hilos se lanzan con `start()`. Llamar a `run()` directamente ejecuta el codigo en el hilo actual y ya no van a la vez.
7. **H2. Primero todos los start, luego todos los join.** Se usan dos bucles separados. Un `join()` dentro del bucle de los `start()` hace que los hilos vayan uno detras de otro.
8. **H3. Cada hilo con sus datos.** Cada hilo es un objeto con sus propios campos. No hay variables `static` modificables ni objetos modificables compartidos entre hilos.
9. **H4. Resultados despues de join.** El programa principal solo lee el resultado de un hilo cuando su `join()` ha terminado.
10. **H5. Exclusion mutua solo si hace falta.** Si un dato modificable tuviera que compartirse, la seccion critica se protege con `synchronized`. Si cada hilo trabaja con sus datos, no se añade ninguna sincronizacion que no haga falta.
11. **H6. No ignorar la interrupcion.** `InterruptedException` no se deja en un `catch` vacio: se avisa o se restaura con `Thread.currentThread().interrupt()` y no se dan los resultados por buenos.
12. **H7. Medir bien el tiempo.** El tiempo se toma antes del primer `start()` y despues del ultimo `join()`. Si los hilos van a la vez debe parecerse al del hilo mas lento, no a la suma de todos.

## Reglas de calidad

13. **C1. Nombres y comentarios.** Variables con nombres que se entiendan y comentarios cortos que expliquen cada bloque.
14. **C2. Excepciones controladas.** Las excepciones se capturan y se muestra un mensaje por la salida de error. Los mensajes de error no se mezclan con la salida normal.
15. **C3. Requisitos exactos.** Los textos de salida, los nombres de clase y los codigos se copian tal cual del enunciado. Se compila con `javac --release 17`, sin paquete y sin librerias externas si la tarea lo pide.

## Lista de comprobacion final

- [ ] Compila con `javac --release 17` sin errores.
- [ ] Se usa `start()` en todos los hilos y hay un bucle de `start()` y otro de `join()`.
- [ ] No hay `static` modificables ni datos modificables compartidos entre hilos.
- [ ] Los resultados se leen despues de `join()`.
- [ ] El tiempo se parece al del hilo mas lento y no a la suma, en al menos 3 ejecuciones.
- [ ] Los datos se envian al hijo y su entrada se cierra.
- [ ] La salida del hijo se lee antes de `waitFor()` y su salida de error tiene destino.
- [ ] Se ha probado cada codigo de salida del hijo, incluidos los casos de error.
- [ ] La salida coincide exactamente con el formato pedido.
- [ ] No hay `.class`, configuracion del IDE ni carpetas del agente en la entrega.
