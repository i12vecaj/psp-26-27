---
name: hilos-procesos-java
description: Reglas para escribir programas en Java 17 que usan hilos (Thread) y procesos independientes (ProcessBuilder) tal como se han trabajado en PSP UA1. Úsala siempre que haya que lanzar tareas concurrentes, esperar a que terminen, medir su tiempo o comunicarse con un proceso hijo mediante entrada/salida estándar y códigos de salida.
---

# hilos-procesos-java

Convenciones para generar código de procesos e hilos en Java sin dependencias externas. Todo el código va en el paquete por defecto (sin `package`) y debe compilar con `javac *.java` y ejecutarse con `java NombreClase`.

## Reglas de procesos

1. **P1. Lanzar el proceso hijo con `ProcessBuilder` y el mismo classpath.** Se usa `new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "NombreClase")` y `start()`. No se escribe `java NombreClase` sin `-cp`, porque falla si el programa se ejecuta desde otra carpeta.
2. **P2. Comunicación por flujos vistos desde el padre.** Para enviar datos al hijo se usa `getOutputStream()` (con `PrintWriter` o `BufferedWriter`) y para leer lo que escribe el hijo se usa `getInputStream()` (con `BufferedReader`). Después de escribir todo lo que el hijo debe leer se hace `flush()` y `close()` del flujo de salida, para que el hijo reciba el fin de entrada y no se quede bloqueado en `readLine()`.
3. **P3. Leer la salida antes de `waitFor()` y recoger el código de salida.** Se lee el `getInputStream()` hasta que `readLine()` devuelva `null` y después se llama a `waitFor()`, que devuelve el código de salida. Así no hay bloqueo por buffer lleno. El hijo termina siempre con `System.exit(codigo)` usando los códigos que pide el enunciado, y el padre muestra el código recibido.

## Reglas de hilos y concurrencia

4. **H1. Arrancar con `start()`, nunca con `run()`.** Llamar a `run()` ejecuta el código en el hilo actual y no crea ningún hilo nuevo.
5. **H2. Lanzar todos los hilos antes de esperar a ninguno.** Primero un bucle que hace `start()` de todos y después otro bucle que hace `join()` de cada uno. Si el `join()` está dentro del bucle del `start()`, los hilos van uno detrás de otro y el tiempo total es la suma, no el del más lento.
6. **H3. Sin variables modificables compartidas entre hilos.** Cada hilo guarda su resultado en un atributo `private` que solo escribe él mismo. No se usan contadores compartidos, ni `static` modificables, ni `synchronized` para evitar el problema. Así no hay condiciones de carrera ni exclusión mutua que resolver.
7. **H4. Leer el resultado de un hilo solo después de su `join()`.** El `join()` garantiza que el hilo ha terminado y que su resultado es visible para el hilo principal. No se lee un resultado mientras el hilo sigue vivo.
8. **H5. Tratar `InterruptedException` en `sleep()` y `join()`.** No se deja el `catch` vacío: se vuelve a marcar la interrupción con `Thread.currentThread().interrupt()` y se sale del trabajo del hilo o del programa.
9. **H6. Medir el tiempo con `System.nanoTime()`.** Se toma justo antes del primer `start()` y justo después del último `join()`, y se convierte a milisegundos. En hilos que trabajan a la vez, el resultado se parece al del hilo más lento, no a la suma de todos.

## Reglas de calidad

10. **C1. Salida exacta y sin ruido.** El programa escribe solo las líneas que pide el enunciado, con el texto, el orden y los espacios exactos. No se añaden trazas de depuración ni líneas en blanco. Las clases que pide el enunciado se llaman exactamente como indica y cada proceso independiente tiene su propio `main`.
11. **C2. Validar datos de entrada.** Todo `Integer.parseInt` va dentro de un `try/catch` de `NumberFormatException`, y un `readLine()` que devuelve `null` se trata como dato no válido.

## Lista de comprobación final

Antes de dar el trabajo por terminado, comprobar cada punto:

- [ ] Los `.java` no tienen `package` y compilan con `javac *.java` sin avisos de error.
- [ ] El proceso hijo se lanza con `ProcessBuilder` y `-cp System.getProperty("java.class.path")` (P1).
- [ ] El padre cierra el flujo de salida hacia el hijo tras escribirle los datos (P2).
- [ ] El padre lee la salida del hijo hasta `null` antes de `waitFor()` y muestra el código de salida (P3).
- [ ] Todos los hilos se arrancan con `start()` (H1) y todos los `start()` van antes que cualquier `join()` (H2).
- [ ] Cada hilo escribe solo en sus propios atributos y no hay `static` modificables ni `synchronized` (H3).
- [ ] El hilo principal lee los resultados solo tras el `join()` correspondiente (H4).
- [ ] No hay `catch (InterruptedException e) {}` vacíos (H5).
- [ ] El tiempo se mide con `System.nanoTime()` alrededor de todos los hilos (H6) y, ejecutando 3 veces, se parece al hilo más lento.
- [ ] La salida coincide línea por línea con el formato pedido (C1).
- [ ] Los códigos de salida del hijo cubren todos los casos del enunciado, incluidos los datos no válidos (C2).
- [ ] No se suben ficheros `.class`, ni configuración del IDE, ni carpetas del agente de IA.
