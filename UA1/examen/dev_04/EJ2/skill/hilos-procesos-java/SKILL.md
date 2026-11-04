---
name: hilos-procesos-java
description: Reglas para programar en Java con hilos y procesos. Úsala cuando tengas que crear hilos  o lanzar otro programa como proceso y comunicarte con él.
---

# Hilos y procesos en Java

## Procesos

1. Para lanzar otra clase como proceso se usa `ProcessBuilder` con `"java", "-cp", System.getProperty("java.class.path"), "NombreClase"` y después `start()`.
2. Para mandarle datos al hijo se usa `getOutputStream()` con un `PrintWriter` con autoflush, y al terminar se cierra con `close()` para que el hijo no se quede esperando.
3. Lo que escribe el hijo se lee con `getInputStream()` y su código de salida se saca con `waitFor()`. El hijo termina con `System.exit(n)`: 0 si todo va bien y otro número si hay un error.

## Hilos y concurrencia

4. Los hilos se crean con una clase que implementa `Runnable` y se arrancan con `start()`, nunca llamando a `run()` directamente.
5. Primero se hacen todos los `start()` y luego, en otro bucle, todos los `join()`. Si el `join()` va justo después de cada `start()`, el programa deja de ser paralelo.
6. Cada hilo usa sus propias variables. No se comparten variables que se modifiquen entre hilos.
7. El `main` solo lee el resultado de un hilo después de hacer su `join()`.

## Calidad

8. Java 17, sin librerías externas y sin paquete. Comentarios cortos, nombres de variables claros y excepciones capturadas con un mensaje, sin `catch` vacíos.

## Comprobación final

- [ ] Compila y funciona en Java 17.
- [ ] El proceso hijo se lanza con `ProcessBuilder`, se cierra su entrada y se muestra su salida y su código.
- [ ] Los hilos se arrancan con `start()` y los `join()` van en un bucle aparte.
- [ ] Los hilos no comparten variables que se modifiquen y el `main` lee los resultados después de los `join()`.
- [ ] La salida tiene el formato que se pide.
