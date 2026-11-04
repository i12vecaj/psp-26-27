# UA1 · Cuaderno de repaso: respuestas

Estructura de esta carpeta:

- `respuestas.md`: este documento (partes 1, 2, 3, 4 y 5).
- `src/`: código Java (`Eco`, `Espera`, `Reto1`…`Reto4`).
- `out/`: clases compiladas.
- `salidas/`: salida real de cada reto al ejecutarlo (JDK 21).

Para compilar y ejecutar desde `ENTREGAR`:

```
javac -encoding UTF-8 -d out src/*.java
java -cp out Reto1
```

---

## Parte 1 · Calentamiento (V/F)

| # | Frase | V/F | Por qué |
|---|---|---|---|
| 1 | `start()` ejecuta la tarea en el hilo `main`. | **F** | `start()` crea un hilo nuevo y este ejecuta `run()`. Si llamas a `run()` directamente, entonces sí se ejecuta en `main`. |
| 2 | Concurrente y paralela son lo mismo. | **F** | Concurrente: varias tareas avanzan a la vez, aunque sea intercalándose en un solo núcleo. Paralela: se ejecutan literalmente al mismo tiempo en varios núcleos. Toda programación paralela es concurrente, pero no al revés. |
| 3 | Dos hilos del mismo proceso comparten memoria. | **V** | Comparten el espacio de direcciones (heap, variables estáticas, ficheros abiertos). Cada uno tiene su propia pila y su contador de programa. |
| 4 | `getInputStream()` de un `Process` es lo que el hijo recibe. | **F** | Es lo que el padre **lee** de la salida estándar del hijo. Lo que recibe el hijo se le envía con `getOutputStream()`. |
| 5 | `waitFor()` bloquea al padre hasta que el hijo termina. | **V** | Y devuelve su código de salida. |
| 6 | Crear un hilo es más costoso que crear un proceso. | **F** | Es al revés: un proceso necesita su propio espacio de memoria y sus estructuras en el SO. El hilo reutiliza los del proceso. |
| 7 | Hilos de 10, 4 y 5 s con `start()` tardan 19 s. | **F** | Se ejecutan a la vez, así que el total es el del más largo: unos **10 s**. 19 s sería el caso secuencial. |
| 8 | Internet es un ejemplo de programación distribuida. | **V** | Muchas máquinas independientes colaboran comunicándose por la red, sin memoria compartida. |
| 9 | Un código de salida distinto de 0 indica error. | **V** | `0` significa que todo fue bien y cualquier otro valor indica un error. |
| 10 | Un mismo `Thread` se puede arrancar dos veces. | **F** | El segundo `start()` lanza `IllegalThreadStateException`. Hay que crear un objeto `Thread` nuevo. |

---

## Parte 2 · Teoría

### 1. Proceso frente a hilo

Un **proceso** es un programa en ejecución: tiene su propio espacio de memoria (código, datos, heap, pila), sus recursos (ficheros abiertos, etc.) y un identificador (PID). Lo gestiona el sistema operativo y está aislado de los demás procesos. Un **hilo** es una línea de ejecución *dentro* de un proceso: todos los hilos de un proceso comparten su memoria y sus recursos, pero cada uno tiene su propia pila, sus registros y su contador de programa. **Relación:** todo proceso tiene al menos un hilo (en Java, `main`) y puede crear más. Crear un hilo y cambiar de uno a otro es mucho más ligero que hacerlo con procesos, y los hilos se comunican fácilmente a través de la memoria compartida. A cambio, un fallo en un hilo puede afectar a todo el proceso, y el acceso compartido a los datos obliga a sincronizar.

### 2. Programación concurrente y sus problemas

La **programación concurrente** consiste en escribir programas con varias tareas (procesos o hilos) que existen a la vez y avanzan simultáneamente o intercalándose, y que normalmente cooperan o compiten por recursos. Plantea dos problemas:

- **Exclusión mutua:** cuando varias tareas acceden a un recurso compartido (la **sección crítica**), solo una puede estar dentro a la vez, porque si no se producen condiciones de carrera. *Ejemplo:* dos hilos hacen `saldo = saldo + 100` sobre la misma cuenta. Si se intercalan, una suma se pierde. Se resuelve con `synchronized`, cerrojos, etc.
- **Condición de sincronización:** una tarea no puede continuar hasta que otra haya hecho algo o se cumpla una condición. *Ejemplo:* en productor-consumidor, el consumidor tiene que esperar a que el buffer tenga datos, y el productor a que haya hueco. Se resuelve con `wait()`/`notify()`, semáforos, `join()`, etc.

### 3. Programación paralela frente a distribuida

- **Paralela:** varias tareas se ejecutan a la vez en **varios procesadores o núcleos de una misma máquina**, normalmente con **memoria compartida**.
  - *Ventajas:* la comunicación es muy rápida (por memoria), el rendimiento es alto y los cálculos intensivos tardan menos.
  - *Inconvenientes:* escala solo hasta el número de núcleos o el hardware de la máquina, hay que sincronizar el acceso a la memoria compartida y, si la máquina falla, cae todo.
- **Distribuida:** las tareas se ejecutan en **varias máquinas independientes conectadas por red**, cada una con su propia memoria, y se comunican **mediante mensajes** (sockets, RMI, HTTP…). *Ejemplo:* Internet, servicios web, BitTorrent.
  - *Ventajas:* escala añadiendo máquinas, tolera fallos (si cae un nodo el resto sigue), permite compartir recursos geográficamente dispersos y puede usar hardware barato.
  - *Inconvenientes:* la comunicación es lenta y poco fiable (latencia, pérdidas), es más difícil de programar, depurar y sincronizar, no hay un reloj global y hay más problemas de seguridad.

---

## Parte 3 · Laboratorio Java

`Eco` y `Espera` no estaban en `material/`, así que los he escrito a partir de la descripción del cuaderno (`src/Eco.java`, `src/Espera.java`).

### Reto 1 · Leer la salida de un proceso (`src/Reto1.java`)

- **Predicción:** "Lo leeré con `getInputStream()`."
- **Resultado:** `getInputStream()` sale **vacío**. El texto de `java -version` sale por `getErrorStream()`. Código de salida: `0`.
- **Por qué:** `java -version` escribe la versión en la **salida de error** (stderr), no en la estándar. Por eso conviene leer siempre los dos flujos (o usar `redirectErrorStream(true)`).
- **Ampliación:** con un comando inexistente, `start()` lanza `IOException`:
  `Cannot run program "comandoQueNoExiste": CreateProcess error=2, El sistema no puede encontrar el archivo especificado`.

### Reto 2 · Enviar datos al hijo (`src/Reto2.java`)

- **Predicción:** "Si no envío `fin` ni cierro el flujo, el hijo se queda esperando y el padre también (bloqueo)."
- **Resultado:** caso normal → `HOLA`, `MUNDO`, código 0. Sin `fin` ni `close()` → el hijo no termina, y a los 3 s `waitFor(3, SECONDS)` devuelve `false`, se hace `destroy()` y el código de salida es `1` (proceso matado). Las líneas que ya había procesado sí se leen.
- **Por qué:** `Eco` está bloqueado en `readLine()` esperando la siguiente línea o el fin de flujo (EOF). Sin `fin` ni `close()` nunca llega ninguno de los dos, y un `waitFor()` sin tiempo límite se quedaría colgado para siempre.

### Reto 3 · Secuencial frente a paralelo (`src/Reto3.java`)

- **Predicción:** secuencial ≈ 4 s, paralelo ≈ 2 s, 3 en paralelo ≈ 2 s.
- **Resultado:** secuencial **4132 ms**, paralelo **2069 ms**, 3 en paralelo **2094 ms**.
- **Por qué:** en secuencial el `waitFor()` del primero bloquea al padre antes de lanzar el segundo. En paralelo se hacen los dos `start()` antes de esperar, así que duermen a la vez. Los milisegundos de más son el arranque de cada JVM hija.

### Reto 4 · Hilos con `Runnable` y `join()` (`src/Reto4.java`)

- **Predicción:** total ≈ 3 s (el hilo más largo). Con `join()` justo tras cada `start()`: 1+2+3 = 6 s.
- **Resultado:** A) **3018 ms**; B) **6026 ms**; C) sin `join()`: el main imprime **1 ms** *antes* de que acabe ningún hilo, y luego van saliendo Hilo-1, Hilo-2 y Hilo-3.
- **Por qué:** `join()` bloquea al main hasta que ese hilo acaba. Si se hace dentro del mismo bucle que el `start()`, no se lanza el siguiente hasta que acaba el anterior, y el programa se vuelve secuencial. Sin `join()`, el main no espera a nadie: mide solo lo que tarda en lanzarlos. Aun así, la JVM no termina hasta que acaban los hilos (no son *daemon*).

---

## Parte 4 · Simulacro

Los ficheros `ua1ex1_teoria.txt` y `ua1ex1_practica.txt` **no estaban** en la carpeta `PROCESOS` (ni `material/` ni `claves/`), así que no he podido hacer el simulacro. Cuando los tengas, se resuelve con el mismo patrón que los retos 1 a 3 (un `Hijo` que lee de `System.in` y termina con `System.exit(n)`, y un `Padre` con `ProcessBuilder`, `getOutputStream`/`getInputStream` y `waitFor`).

---

## Parte 5 · Autoevaluación

Corregida con las justificaciones de arriba, porque no tengo las claves oficiales de `claves/`.

| Parte | Nota | Qué fallé |
|---|---|---|
| V/F (10 frases) | 10/10 | |
| Teoría: proceso frente a hilo | 3/3 | |
| Teoría: concurrencia y problemas | 3/3 | |
| Teoría: paralela frente a distribuida | 3/3 | |
| Reto 1 | OK | La predicción falló: `java -version` escribe en stderr, no en stdout. |
| Reto 2 | OK | |
| Reto 3 | OK | |
| Reto 4 | OK | |
| Simulacro teoría | pendiente | Faltan los enunciados. |
| Simulacro práctica | pendiente | Faltan los enunciados. |

**Lo que voy a repasar antes del examen:**
1. Qué flujo es cuál: `getInputStream` (leo lo que el hijo escribe), `getOutputStream` (escribo lo que el hijo lee) y `getErrorStream`.
2. Dónde se pone el `waitFor()`/`join()` para que la ejecución sea paralela y no secuencial.
3. Exclusión mutua frente a condición de sincronización, con un ejemplo de cada una.
