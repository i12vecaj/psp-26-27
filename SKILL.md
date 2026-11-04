---
name: hilos-procesos-java
description: Genera y revisa programas Java 17 (sin librerias externas) que usan hilos (Thread, start, join) y procesos independientes (ProcessBuilder, entrada/salida estandar, codigos de salida). Usala cuando se pida programar concurrencia con hilos que trabajan a la vez, lanzar otro programa Java como proceso hijo, comunicarse con el por stdin/stdout o medir tiempos de ejecucion concurrente.
---

# Hilos y procesos en Java

## Reglas de PROCESOS

1. **Lanzar con `ProcessBuilder`** y una lista de argumentos separados (nunca un unico String con espacios). El ejecutable se obtiene de `System.getProperty("java.home") + "/bin/java"` y el classpath de `System.getProperty("java.class.path")`, para que funcione desde cualquier carpeta.
2. **Comunicar por entrada estandar**: los datos para el proceso hijo se escriben en `proceso.getOutputStream()` (una linea por dato, terminada en `\n`), y se **cierra** el flujo (try-with-resources) para que el hijo vea el fin de entrada.
3. **Leer la salida del hijo** con `proceso.getInputStream()` hasta que devuelva `null`, y usar `redirectErrorStream(true)` para no perder ni bloquear por stderr.
4. **Esperar y comprobar el codigo de salida**: llamar a `waitFor()` y mostrar o usar su valor. El hijo debe terminar con `System.exit(codigo)` usando los codigos pedidos en el enunciado.
5. **El hijo valida su entrada**: controla `null` (faltan lineas), `NumberFormatException` y rangos, y devuelve el codigo de "datos no validos" en vez de lanzar una excepcion sin controlar.

## Reglas de HILOS y CONCURRENCIA

6. **Cada hilo trabaja con sus propios datos**: los campos que modifica un hilo son `private` y solo los escribe ese hilo. No hay variables compartidas modificables entre hilos (ni `static` mutables).
7. **Lanzar todos los hilos antes de esperar a ninguno**: primero un bucle con `start()` para todos y despues otro bucle con `join()`. Nunca `start()` seguido de `join()` en el mismo bucle (seria secuencial). Nunca llamar a `run()` directamente.
8. **Leer los resultados solo tras `join()`**: el programa principal consulta el resultado de un hilo unicamente cuando este ha terminado (`join()` garantiza la visibilidad del dato).
9. **No esperar con `sleep` ni con espera activa**: para esperar a un hilo se usa `join()`. `Thread.sleep` solo se usa para simular trabajo dentro del hilo.
10. **Tratar `InterruptedException`**: nunca dejar el `catch` vacio; restaurar con `Thread.currentThread().interrupt()` y salir del trabajo o del programa de forma controlada.
11. **Medir tiempos con `System.nanoTime()`**: tomar el inicio justo antes de los `start()` y el fin justo despues de los `join()`. El tiempo debe parecerse al del hilo mas lento, no a la suma de todos.

## Reglas de CALIDAD

12. **Cumplir el formato exacto del enunciado**: mismas lineas, mismo orden, mismo texto de salida; Java 17, sin paquete (`package`), sin librerias externas.
13. **Codigo limpio**: nombres con significado, constantes en vez de numeros magicos, recursos cerrados con try-with-resources, y la clase compila con `javac *.java` sin avisos de error.

## Lista de comprobacion final

- [ ] Compila con `javac *.java` y no hay `package` ni librerias externas.
- [ ] Existen las clases pedidas, cada una con su `main`.
- [ ] El proceso hijo se lanza con `ProcessBuilder` (java.home + classpath).
- [ ] Los datos se envian por stdin y el flujo se cierra.
- [ ] Se lee la salida del hijo y se muestra el codigo de `waitFor()`.
- [ ] El hijo controla datos invalidos y usa `System.exit` con los codigos del enunciado.
- [ ] Todos los hilos se lanzan (`start`) antes del primer `join`.
- [ ] Ningun dato modificable se comparte entre hilos.
- [ ] Los resultados se leen despues de `join()`.
- [ ] `InterruptedException` esta tratada sin `catch` vacio.
- [ ] El tiempo medido se parece al del hilo mas lento (no a la suma).
- [ ] La salida respeta exactamente el formato y el orden pedidos.
- [ ] Probado al menos 3 veces con el mismo resultado correcto.