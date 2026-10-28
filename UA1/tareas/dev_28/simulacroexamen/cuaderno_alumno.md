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

1. `start()` ejecuta la tarea en el hilo `main`. V /F
2. Programación concurrente y programación paralela son lo mismo. F 
3. Dos hilos del mismo proceso comparten memoria. F /V
4. `getInputStream()` de un `Process` es lo que el hijo recibe. V / F
5. `waitFor()` bloquea al padre hasta que el hijo termina. V
6. Crear un hilo es más costoso que crear un proceso. F
7. Tres hilos de 10, 4 y 5 segundos lanzados con `start()` tardan 19 segundos en total. V / F
8. Internet es un ejemplo típico de programación distribuida. F / V
9. Por convenio, un código de salida distinto de 0 indica error. V
10. Un mismo objeto `Thread` se puede arrancar dos veces con `start()`. F

---

## Parte 2 · Teoría (30 min)

Responde cada pregunta con tus palabras, en unas 5 líneas, **sin apuntes** (7 minutos por pregunta).

1. ¿Qué diferencias hay entre un **proceso** y un **hilo**, y cómo se relacionan?
Un proceso es un programa en ejecucion como un proyecto en Intellij que puedes ejecutar varios a la vez. Los Hilos son  los que usan a los procesos simultaneamente una cantidad de ellos  grande para que asimilen y usen mas rapidimante o procesen sus datos mas rapido.
Se relacionan los procesos y los hilos para asi tener una mayor fluidez a la hora de transferir o mover datos y al ejecutarlos.

2. ¿Qué es la **programación concurrente** y qué problemas plantea (exclusión mutua y condición de sincronización)? Pon un ejemplo de cada uno.
La programacion concurrente es en la cual interfieren dos procesos que se intercalan o se cambian entre ellos para asi ir de uno en uno asimilando la informacion y no dar de golpe toda.
Los problemas que plantea esta programacion concurrente sobre exclusion mutua se podria dar el caso cuando un monton de usuarios se meten en una pagina web y la colapsan y el sistema se bloquea por ejemplo: Una pagina cuando se hacen la venta de entradas de algo exclusivo.
La condicion de sincronizacion es cuando necesitas que un proceso se ejecuta o una parte antes que otra para asi que la parte siguiente puede funcionar por ejemplo: En Acceso a datos necesitas antes ejecutar el GuardarDom antes que el CargarDom porque si no no te carga bien el archivo CargarDom.

3. ¿Qué diferencias hay entre **programación paralela** y **programación distribuida**? Ventajas e inconvenientes de cada una.
 Las diferencias entre programacion paralela y programacion distribuida la principal diferencia es que  la paralela usa Cpu todo en un mismo dispositivo para ejecutarse en cambio la distribuida se conectan a traves de internet  a traves de diferentes dispositivos fisicos de hardware o sotware.
Ventajas de programacion paralela:
Ejecucion simultananea debido a que trabajen a la vez los distintos Cpus
Su ejecucion es mas rapida que la distribruida.
Inconvenientes de programacion paralela:
Mayor consumo energetico debido a la cantidad de Cpus que hay que usar.
Mayor dificultad de comunicacion debido a que no estan interconectadas a traves de internet que en la programcion distrbuida si.
Ventajas de programacion distribuida:
Su comunicacion es mejor debido a que es a traves de internet y tienes acceso donde quieras en cambio la pararela solo en su dispositivo de la cpu individual.

Inconvenientes de programacion distribuida:
Tienes mas problemas de seguridad debido a que son muchos los dispositivos que trabajan.
Tambien puedes tener perdidas de mensajes por saturacion debido a la inmensa cantidad que habra al trabajar con tantos dispositivos a traves de la red.

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
