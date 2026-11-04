---
name: hilos-procesos-java
description: Reglas para programar en Java 17 con hilos (Thread y Runnable) y procesos (ProcessBuilder y Process), según lo visto en la UA1. Usar cuando haya que lanzar varios hilos a la vez y esperar a que terminen, medir cuánto tardan, o ejecutar otro programa Java como proceso hijo, enviarle datos, leer su salida y comprobar su código de salida.
---

# Hilos y procesos en Java (UA1)

## Procesos

1. **P1.** El proceso hijo se crea con `ProcessBuilder` y se lanza con `start()`, pasando cada parte del comando por separado: `new ProcessBuilder(java, "-cp", classpath, "Clase")`. La ruta de `java` se saca de `System.getProperty("java.home")` y el classpath de `System.getProperty("java.class.path")`, para no depender de rutas fijas.
2. **P2.** Hay que acordarse de que **`getOutputStream()` es escribir al hijo** (lo que él lee por su entrada estándar). Después de enviarle los datos, se cierra para que el hijo sepa que no le van a llegar más.
3. **P3.** **`getInputStream()` es leer lo que escribe el hijo.** Se lee toda su salida antes de `waitFor()`, porque si no el hijo se puede quedar bloqueado con el buffer lleno. Los errores del hijo no se pierden: se leen con `getErrorStream()` o se redirigen con `redirectError()`.
4. **P4.** El hijo comprueba que los datos que recibe son correctos y termina con `System.exit(codigo)`, usando 0 cuando todo va bien y un código distinto para cada error. El padre obtiene ese código con `waitFor()` (o `exitValue()` cuando ya ha terminado) y lo muestra.

## Hilos y concurrencia

5. **H1.** Los hilos se crean con `extends Thread` o con `implements Runnable` + `new Thread(objeto)`, y se arrancan con `start()`. Nunca se llama a `run()` directamente, porque entonces se ejecuta en el hilo `main` y no a la vez. Cada objeto `Thread` solo se puede arrancar una vez.
6. **H2.** Primero se hace `start()` de todos los hilos y después `join()` de todos, en bucles separados. Si se hace `start()` y `join()` en el mismo bucle, los hilos van uno detrás de otro: tres hilos de 10, 4 y 5 segundos tardarían 19 s en vez de 10 s. El tiempo se mide con `System.nanoTime()` antes del primer `start()` y después del último `join()`.
7. **H3.** El programa principal solo lee el resultado de un hilo después de su `join()`, que es lo que asegura que ese hilo ya ha terminado. No se espera con `sleep()`.
8. **H4.** Para evitar problemas de **exclusión mutua**, cada hilo trabaja con sus propios datos (sus propios atributos) y no se comparten variables que se modifiquen, ni variables `static` que cambien. Así no hace falta `synchronized`.
9. **H5.** En el `catch (InterruptedException e)` no se deja vacío ni solo con `printStackTrace()`: se llama a `Thread.currentThread().interrupt()` y el hilo deja de trabajar.

## Calidad

10. **C1.** Java 17 sin librerías externas y sin paquete, una clase por fichero, constantes en vez de números sueltos, código comentado y control de errores. La salida tiene que ser exactamente la que pide el enunciado.

## Lista de comprobación final

- [ ] Compila con Java 17 sin errores.
- [ ] Los hilos se lanzan con `start()` en un bucle y se espera con `join()` en otro.
- [ ] No hay variables modificables compartidas entre hilos.
- [ ] Los resultados se leen después de `join()`.
- [ ] El tiempo total se parece al del hilo más lento y no a la suma de todos.
- [ ] El proceso se lanza con `ProcessBuilder` sin rutas fijas.
- [ ] Se escribe al hijo con `getOutputStream()` y se cierra; se lee su salida con `getInputStream()` antes de `waitFor()`.
- [ ] El hijo devuelve el código correcto en todos los casos, incluidos datos no válidos.
- [ ] La salida es igual que la del enunciado.
- [ ] Ejecutado al menos 3 veces con el resultado correcto.
