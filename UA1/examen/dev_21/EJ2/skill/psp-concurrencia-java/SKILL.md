---
name: psp-concurrencia-java
description: Guía para resolver ejercicios de PSP (UA1) en Java 17 que combinan varios hilos trabajando en paralelo con un proceso hijo que valida el resultado. Actívala cuando el enunciado pida Runnable/Thread, join, medir tiempos, ProcessBuilder, pasar datos por la entrada estándar del hijo o interpretar su código de salida.
---

# PSP · Concurrencia en Java (hilos + proceso hijo)

Objetivo: código que un alumno de UA1 pueda defender línea a línea. Si algo no se ha visto en clase, no entra.

---

## 1. Límites de nivel

| Se usa | No se usa |
|---|---|
| Arrays (`int[]`, `Thread[]`) | `ArrayList`, `List`, `Map` |
| `Runnable` + `new Thread(tarea, nombre)` | `ExecutorService`, `CompletableFuture` |
| `Scanner`, `BufferedWriter` + `OutputStreamWriter` | `PrintWriter`, `try (...)` con recursos |
| `switch` clásico con `case` / `return` | `switch` con flechas, `var`, lambdas, streams |
| `System.nanoTime()` | `Instant`, `Duration` |

Java 17, paquete por defecto, sin librerías externas. Nombres de clases tal cual los da el enunciado.

---

## 2. Hilos

**H1 · Tarea separada del hilo.** La lógica va en una clase que `implements Runnable`. El hilo se crea aparte con `new Thread(tarea, "Nombre-" + n)` para que tenga nombre en los mensajes.

**H2 · Dos arrays paralelos.** `tareas[i]` guarda los datos y resultados; `hilos[i]` es el hilo que la ejecuta. El `main` consulta siempre la tarea, nunca el hilo.

**H3 · Arrancar todo, luego esperar todo.** Un método recorre el array haciendo `start()`; otro distinto hace `join()`. Jamás `run()` directo y jamás `start()` y `join()` en la misma vuelta.

**H4 · Nada compartido que se modifique.** Cada tarea escribe solo en sus atributos. El `main` lee los getters después del `join()`. Si de verdad hiciera falta un dato común, `synchronized`.

**H5 · Interrupciones.** `Thread.sleep()` y `join()` van en `try/catch (InterruptedException)`. En la tarea se hace `break` y se avisa por `System.err` con `Thread.currentThread().getName()`.

**H6 · El programa demuestra su propio paralelismo.** Cada tarea mide cuánto ha tardado ella sola. El `main` mide el tiempo real (antes del primer `start()`, después del último `join()`) y muestra las dos cifras: tiempo real ≈ la tarea más lenta, suma ≈ lo que tardaría en serie.

```java
long t0 = System.nanoTime();
abrir(hilos);           // todos los start()
esperarCierre(hilos);   // todos los join()
long msReales = (System.nanoTime() - t0) / 1000000;
```

---

## 3. Proceso hijo

**P1 · Lanzar.** `new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "Clase")` y `start()`.

**P2 · No perder los errores del hijo.** Si el hijo escribe por `System.err`, poner `pb.redirectErrorStream(true)` **antes** de `start()`. Sin eso el padre solo lee `System.out` y los avisos de error no se ven.

**P3 · Enviar datos.** `BufferedWriter` sobre `new OutputStreamWriter(p.getOutputStream())`: `write`, `newLine`, `flush` y `close`. Sin `close()` el hijo se queda esperando más datos.

**P4 · Leer la respuesta.** `Scanner` sobre `p.getInputStream()` con `hasNextLine()` hasta que no quede nada; después `waitFor()` devuelve el código.

**P5 · El hijo valida antes de leer.** `Scanner(System.in)` y `hasNextInt()` antes de cada `nextInt()`. Cada código de salida es una constante con nombre (`CORRECTO = 0`, …). Mensaje OK por `System.out`, mensajes de error por `System.err`, y siempre `System.exit(codigo)`.

**P6 · El padre traduce el código.** Un método con `switch` convierte el número en un texto que se entiende.

---

## 4. Forma del código

- `main` corto: prepara datos y llama a métodos `static` cuyo nombre dice lo que hacen (`abrir`, `esperarCierre`, `mostrarResumen`, `lanzarAuditor`).
- Cabecera en bloque `/* */` con clase, ejercicio y autor.
- Un comentario corto por paso, en español, sin tildes en el código para no romper la consola.
- Salida con `printf` cuando haya tabla, para que las columnas cuadren.

---

## 5. Verificación antes de entregar

| # | Prueba | Cómo | Debe salir |
|---|---|---|---|
| V1 | Compila limpio | `javac --release 17 -Xlint:all -d out src/*.java` | Sin avisos |
| V2 | Resultado estable | Ejecutar 3 veces | Mismo total y código 0 las 3 |
| V3 | Paralelismo real | Comparar las cifras de H6 | Real ≈ tarea más lenta, muy lejos de la suma |
| V4 | Contraprueba | En una copia, `start()` → `run()` | Real ≈ suma |
| V5 | Hijo aislado | `echo datos \| java -cp out Clase` por cada caso | Cada código con su mensaje |
| V6 | Errores visibles | Forzar un dato erróneo desde el padre | El mensaje de error del hijo aparece en el padre |
| V7 | Entrega limpia | Revisar la carpeta | Ni `.class` ni ficheros del IDE |
