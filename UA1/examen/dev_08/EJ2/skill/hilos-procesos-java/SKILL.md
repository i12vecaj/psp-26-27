---
name: hilos-procesos-java
description: Guía para escribir programas Java 17 (sin librerías ni paquete) que lanzan procesos independientes con ProcessBuilder y reparten trabajo entre hilos Thread de forma correcta y concurrente. Úsala siempre que haya que crear hilos, esperar a que terminen, medir tiempos de ejecución concurrente o comunicarse con otro proceso por su entrada y salida estándar y su código de salida.
---

# Hilos y procesos en Java

## Reglas de procesos

1. **Lanzar con `ProcessBuilder` y argumentos separados.** Cada argumento va como un elemento distinto (`new ProcessBuilder(java, "-cp", classpath, "Auditor")`), nunca un único `String` con espacios. Verificable: no hay ninguna cadena con el comando entero.
2. **No fijar la ruta de Java ni el classpath a mano.** Usar `System.getProperty("java.home") + "/bin/java"` y `System.getProperty("java.class.path")` para que el hijo se ejecute con la misma JVM y las mismas clases. Verificable: no aparecen rutas absolutas en el código.
3. **Comunicarse por la entrada y salida estándar y cerrar la escritura.** Se escribe en `getOutputStream()` y se cierra (try-with-resources) para que el hijo reciba el fin de entrada; después se lee `getInputStream()` hasta `null`. Verificable: el flujo de escritura se cierra antes de leer.
4. **Unir la salida de error con la estándar** (`redirectErrorStream(true)`) y leer toda la salida antes de esperar, para que el hijo no se bloquee con un búfer lleno. Verificable: la lectura va antes de `waitFor()`.
5. **Esperar al hijo y leer su código de salida** con `waitFor()`, que devuelve el código; el hijo termina con `System.exit(codigo)` y cada código tiene un significado documentado en una constante. Verificable: el padre muestra el valor devuelto por `waitFor()`.

## Reglas de hilos y concurrencia

6. **Arrancar con `start()`, nunca con `run()`.** Llamar a `run()` ejecuta el código en el hilo actual y no hay concurrencia. Verificable: no se llama a `run()` directamente.
7. **Lanzar todos los hilos antes de esperar a ninguno.** Primero un bucle con todos los `start()` y después otro con todos los `join()`. Si se hace `start()` + `join()` dentro del mismo bucle, el trabajo queda en serie. Verificable: el tiempo total se parece al del hilo más lento, no a la suma.
8. **Cada hilo trabaja con sus propios datos.** Los campos modificables son `private` y pertenecen solo a ese hilo; no hay variables estáticas ni objetos mutables compartidos entre hilos. Verificable: ningún campo `static` modificable.
9. **Leer el resultado solo después de `join()`.** `join()` garantiza que los cambios del hilo ya son visibles para quien lo espera, por lo que no hacen falta `synchronized` ni `volatile`. Verificable: los getters se llaman después del bucle de `join()`.
10. **Tratar `InterruptedException` correctamente.** En `sleep()` y `join()` se captura la excepción, se restaura la interrupción con `Thread.currentThread().interrupt()` y se sale del trabajo; no se deja el `catch` vacío. Verificable: ningún `catch` vacío.
11. **Medir el tiempo con `System.nanoTime()`** justo antes del primer `start()` y justo después del último `join()`. Verificable: la medida abarca todos los hilos y solo ellos.

## Reglas de calidad

12. **Salida exacta y código limpio.** Respetar al pie de la letra el formato de salida pedido, sin tildes en los textos que viajan entre procesos para evitar problemas de codificación, sin números mágicos (aforo, cupos, milisegundos y códigos de salida en constantes), sin paquete y sin ficheros `.class` en la entrega. Verificable: comparar la salida con el enunciado línea a línea.

13. **Validar la entrada del proceso hijo sin lanzar excepciones.** Si faltan líneas (`readLine()` devuelve `null`), el texto no es un número o el valor está fuera de rango, el hijo escribe un veredicto y termina con el código de datos no válidos; no se atrapa `NullPointerException`. Verificable: probar el hijo con entrada vacía, una sola línea, `abc` y valores negativos y comprobar el código.

## Lista de comprobación final

- [ ] Compila con `javac --release 17` sin avisos y sin librerías externas.
- [ ] Las clases están en el paquete por defecto y cada clase con `main` tiene el nombre pedido.
- [ ] Todos los hilos se arrancan con `start()` antes del primer `join()`.
- [ ] No hay variables modificables compartidas entre hilos y los resultados se leen tras `join()`.
- [ ] `InterruptedException` está tratada sin `catch` vacíos.
- [ ] El proceso hijo se lanza con `ProcessBuilder`, se le escribe por la entrada estándar y se cierra el flujo.
- [ ] Se lee la salida del hijo completa y se muestra su código de salida con `waitFor()`.
- [ ] Probados todos los códigos de salida del hijo, incluidos entrada vacía, texto y negativos.
- [ ] Ejecutado al menos 3 veces: el total es el esperado y el tiempo se parece al del hilo más lento.
- [ ] La salida coincide exactamente con el formato del enunciado.
- [ ] No se suben `.class`, ficheros del IDE ni carpetas del agente.
