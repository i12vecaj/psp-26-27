---
name: hilos-procesos-java
description: Reglas para hacer programas en Java 17 con procesos (ProcessBuilder) y con hilos (Thread) como los de clase de PSP UA1. Usala siempre que haya que lanzar un proceso hijo, mandarle datos, leer su salida o su codigo, o repartir un trabajo entre varios hilos que van a la vez.
---

# Hilos y procesos en Java

Codigo sencillo, como los ejemplos de clase de UA1. Java 17, sin librerias externas y en el paquete por defecto si el enunciado lo pide.

## Reglas de procesos

1. El hijo se lanza con `new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Clase", ...)` y `start()`. Sin paquete el nombre es el de la clase tal cual.
2. Para mandar datos al hijo se usa `getOutputStream()` con `write`, `flush` y `close`. Cada dato acaba en `\n`. Si no se cierra, el hijo se queda esperando.
3. La salida del hijo se lee con `getInputStream()` y un `BufferedReader` hasta que da `null`, y despues se llama a `waitFor()` para tener el codigo de salida.
4. El hijo acaba con `System.exit(codigo)`. 0 es que todo fue bien y cada error tiene su numero. Los errores del hijo van a `System.err`.

## Reglas de hilos y concurrencia

5. Un hilo se arranca con `start()`, nunca con `run()`. `run()` lo hace en el main y van uno detras de otro.
6. Primero se lanzan todos los hilos y despues se hace `join()` de cada uno. Nunca `start()` y `join()` en el mismo bucle.
7. Los hilos no comparten variables que se modifiquen. Cada hilo guarda sus datos en sus propios atributos y el main los lee solo despues del `join()`. Si hubiera que compartir algo, con `synchronized`.
8. `Thread.sleep` y `join` lanzan `InterruptedException` y se controlan con `try` y `catch`.
9. El tiempo se mide con `System.currentTimeMillis()` justo antes del primer `start()` y justo despues del ultimo `join()`.

## Regla de calidad

10. Nombres de clases y salida exactamente como el enunciado. Comentarios cortos en español, uno por paso, sin cosas que no se hayan dado en clase.

## Lista de comprobacion final

- [ ] Compila con Java 17 sin avisos y sin paquete.
- [ ] Los hilos se lanzan con `start()` y se espera con `join()` despues de lanzarlos todos.
- [ ] Ningun hilo modifica una variable de otro ni una comun.
- [ ] El main solo lee los resultados despues del `join()`.
- [ ] Al hijo se le manda todo y se le cierra la entrada.
- [ ] Se lee la salida del hijo y se muestra su codigo de salida.
- [ ] Se ha ejecutado al menos 3 veces y el resultado es siempre el mismo.
- [ ] El tiempo con hilos se parece al del hilo mas lento y no a la suma de todos.
- [ ] Se han probado los casos de error del hijo con su codigo.
- [ ] No hay `.class` ni ficheros del IDE en la entrega.
