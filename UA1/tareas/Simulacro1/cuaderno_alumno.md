# UA1 · Cuaderno de repaso individual (Java)

**Cómo usarlo:** trabaja solo, en orden y **sin mirar las claves** hasta que te lo indiquen (o hasta acabar cada parte). Anota tus respuestas en un papel o en un fichero aparte. Cronometra cada parte.

| Parte | Tiempo | Qué haces |
|---|---|---|
| 1. Calentamiento | 10 min | 10 frases verdadero/falso |
| 2. Teoría | 30 min | 3 preguntas "con tus palabras" |
| 3. Laboratorio | 35 min | 4 retos en Java |
| 4. Simulacro | 25 + 50 min | `ua1ex1_teoria.txt` y `ua1ex1_practica.txt` |
| 5. Autocorrección | 20 min | Con `claves/` y la hoja de autoevaluación |

Si usas IntelliJ y te pide un SDK que no tienes, elige el JDK que tengas instalado en File > Project Structure > Project.

---

## Parte 1 · Calentamiento (10 min)

Escribe V o F:

1. `start()` ejecuta la tarea en el hilo `main`. -- FALSO
2. Programación concurrente y programación paralela son lo mismo. -- FALSO
3. Dos hilos del mismo proceso comparten memoria. -- VERDADERO
4. `getInputStream()` de un `Process` es lo que el hijo recibe. -- FALSO
5. `waitFor()` bloquea al padre hasta que el hijo termina. -- VERDADERO
6. Crear un hilo es más costoso que crear un proceso. -- FALSO
7. Tres hilos de 10, 4 y 5 segundos lanzados con `start()` tardan 19 segundos en total. -- FALSO
8. Internet es un ejemplo típico de programación distribuida. -- Verdadero
9. Por convenio, un código de salida distinto de 0 indica error. -- Verdadero
10. Un mismo objeto `Thread` se puede arrancar dos veces con `start()`. -- FALSO

---

## Parte 2 · Teoría (30 min)

Responde cada pregunta con tus palabras, en unas 5 líneas, **sin apuntes** (7 minutos por pregunta).

1. ¿Qué diferencias hay entre un **proceso** y un **hilo**, y cómo se relacionan?
    --Un proceso es como un programa completo, esto quiere decir que el sistema operativo tiene que asignar memoria para el mientras que los hilos de un proceso comparten el mismo espacio de memoria.
    --Al crear o destruir un proceso es mas lento ya que el sistema debe reasignar memoria y recursos mientras que los hilos son muy ligeros ya que no hay que reasignar memoria cada vez que se crea o destruye. Esto es porque comparten los recursos del proceso padre en el que estan.
    --Si un proceso falla, es aislado y no afecta a los demas procesos pero si un hilo falla hace como un efecto domino y se lleva todo el proceso para abajo junto a los demas hilos, es decir, que el proceso y los hilos mueren. 
    --Se relacionan entre si por que los hilos son "sub-procesos" del proceso padre. Un hilo no existe sin un proceso padre.
2. ¿Qué es la **programación concurrente** y qué problemas plantea (exclusión mutua y condición de sincronización)? Pon un ejemplo de cada uno.
    --La programacion concurrente es un paradigma de diseño de software en el que múltiples tareas avanzan en periodos de tiempo superpuestos. En lugar de ejecutar un programa de principio a fin de forma secuencial, el sistema estructura el código para lidiar con varias cosas a la vez.
    --Exclusion mutua:
        - Ocurre cuando varios procesos acceden a la misma variable a modificarla 
        - Un proceso puede estar leyendo mientras que otro proceso puede modificar lo que esta leyendo el primer proceso a la vez.
        - Ejemplos: Acceso a una BBDD.
    --Condicion de sincronizacion:
        - Necesidad de coordinar las actividades de dos procesos.

3. ¿Qué diferencias hay entre **programación paralela** y **programación distribuida**? Ventajas e inconvenientes de cada una.
    --La programacion paralela es dentro de un solo ordenador: 
        - Consiste en dividir una tarea masiva en partes más pequeñas y ejecutarlas simultáneamente en los múltiples núcleos o procesadores del ordenador.
    --La programacion distribuida es en varios ordenadores conectados entre si por la red:
        - Cada ordenador ejecuta una parte del programa, tiene su propia memoria aislada y se comunican entre sí enviándose mensajes para lograr un objetivo común
    --Diferencias entre si: 
        -La programacion pararela ocurre dentro de una sola maquina mientras que la distribuida es entre varias
        -Los recursos para la paralela son el de la propia maquina mientras que la distribuida son las que tiene cada maquina.

---

## Parte 3 · Laboratorio Java (35 min)

Para cada reto: **1)** escribe tu predicción, **2)** programa, **3)** ejecuta y anota qué ha pasado, **4)** si no coincide, explica por qué. Si te atascas, usa las pistas por orden. Los programas `Eco` y `Espera` están en `material/`.

### Reto 1 · Leer la salida de un proceso (8 min)
**Enunciado:** lanza `java -version` desde Java y muestra lo que imprime, junto con su código de salida.
**Predice:** ¿lo leerás con `getInputStream()`?
- Pista 1: `new ProcessBuilder("java", "-version").start()`.
- Pista 2: mira también `getErrorStream()`; el código lo da `waitFor()`.
- **Ampliación:** lanza un comando que no exista y captura la excepción. ¿Qué mensaje da?

### Reto 2 · Enviar datos al hijo (10 min)
**Enunciado:** el programa `Eco` lee líneas de su entrada hasta recibir `fin` y las devuelve en mayúsculas. Haz un padre que le envíe `hola`, `mundo` y `fin`, y muestre lo que responde.
**Predice:** ¿qué pasa si no envías `fin` ni cierras el flujo?
- Pista 1: `getOutputStream()` envuelto en un `PrintWriter` con *autoflush*.
- Pista 2: haz `close()` al terminar. Para lanzar `Eco`, usa `"java", "-cp", System.getProperty("java.class.path"), "Eco"`.
- **Ampliación:** espera con `waitFor(3, TimeUnit.SECONDS)` y, si no termina, `destroy()`.

### Reto 3 · Secuencial frente a paralelo (10 min)
**Enunciado:** `Espera` duerme los segundos que le indiques. Lanza dos hijos de 2 s: primero uno tras otro, y luego los dos a la vez. Mide el tiempo de cada versión.
**Predice:** ¿cuánto tardará cada una?
- Pista 1: `System.currentTimeMillis()` antes y después.
- Pista 2: en paralelo, haz `start()` de **los dos** antes del primer `waitFor()`.
- **Ampliación:** ¿cuánto tardan 3 hijos de 2 s en paralelo?

### Reto 4 · Hilos con `Runnable` y `join()` (7 min)
**Enunciado:** crea 3 hilos que duerman 1, 2 y 3 segundos e impriman su nombre al acabar. El `main` debe imprimir cuánto ha tardado **en total** cuando terminen los tres.
**Predice:** ¿cuánto tarda? ¿Y si pones el `join()` justo después de cada `start()`?
- Pista 1: una clase que implemente `Runnable` y reciba los segundos.
- Pista 2: guarda los hilos en un array y haz los `join()` en un segundo bucle.
- **Ampliación:** quita los `join()`. ¿Qué imprime ahora el `main` y cuándo?

---

## Parte 4 · Simulacro (75 min)

1. **Teoría (25 min, sin internet):** `ua1ex1_teoria.txt`.
2. **Práctica (50 min):** `ua1ex1_practica.txt`. Crea `Hijo.java` y `Padre.java`.

Respeta el tiempo: cuando se acabe, para y guarda lo que tengas.

---

## Parte 5 · Autoevaluación

Con las claves de `claves/` rellena esta hoja:

| Parte | Mi nota | Qué fallé |
|---|---|---|
| V/F (10 frases) | /10 | |
| Teoría: proceso frente a hilo | /3 | |
| Teoría: concurrencia y problemas | /3 | |
| Teoría: paralela frente a distribuida | /3 | |
| Reto 1 | OK / No | |
| Reto 2 | OK / No | |
| Reto 3 | OK / No | |
| Reto 4 | OK / No | |
| Simulacro teoría | /10 | |
| Simulacro práctica | /10 | |

**Lo que voy a repasar antes del examen:**
1. 
2. 
3. 

---

## Chuleta de Java

| Quiero... | Procesos | Hilos |
|---|---|---|
| Arrancar | `new ProcessBuilder(...).start()` | `new Thread(tarea).start()` |
| Esperar a que termine | `waitFor()` | `join()` |
| Terminar con un código | `System.exit(n)` (en el hijo) | no aplica |
| Comunicarse | `getInputStream()`, `getOutputStream()`, `getErrorStream()` | datos compartidos y `synchronized` |
| Identificador | `pid()` | `getName()` |
| Cortarlo | `destroy()` | `interrupt()` (solo avisa) |
