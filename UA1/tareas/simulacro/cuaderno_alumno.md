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

1. `start()` ejecuta la tarea en el hilo `main`. V X
2. Programación concurrente y programación paralela son lo mismo. F
3. Dos hilos del mismo proceso comparten memoria. V
4. `getInputStream()` de un `Process` es lo que el hijo recibe. V X
5. `waitFor()` bloquea al padre hasta que el hijo termina. V
6. Crear un hilo es más costoso que crear un proceso. F
7. Tres hilos de 10, 4 y 5 segundos lanzados con `start()` tardan 19 segundos en total. F
8. Internet es un ejemplo típico de programación distribuida. V
9. Por convenio, un código de salida distinto de 0 indica error. F X
10. Un mismo objeto `Thread` se puede arrancar dos veces con `start()`. V X



---

## Parte 2 · Teoría (30 min)

Responde cada pregunta con tus palabras, en unas 5 líneas, **sin apuntes** (7 minutos por pregunta).

1. ¿Qué diferencias hay entre un **proceso** y un **hilo**, y cómo se relacionan?
Un proceso es un programa el cual ejecuta los hilos, los cuales estan dentro del proceso y comparten memoria y recursos.

2. ¿Qué es la **programación concurrente** y qué problemas plantea (exclusión mutua y condición de sincronización)? Pon un ejemplo de cada uno.
Cuando varios sucesos ocurren al mismo tiempo. La exclusión mutua ocurre cuando varios procesos acceden a la misma variable a modificarla y respecto a la condición de sincronización es la necesidad de coordinar las actividades de dos procesos ya que a lo mejor uno no puede empezar hasta que no acabe otro

3. ¿Qué diferencias hay entre **programación paralela** y **programación distribuida**? Ventajas e inconvenientes de cada una.
Esta divido en partes que corren en distintas CPUs con lo que lleva más consumo y una sincronización más compleja. Y la distribuida son distintos equipos conectados en red, comparten recursos, más disponibilidad y más incremental pero puede ser más complejo y llevar problemas de seguridad como de DDoS

---

## Parte 3 · Laboratorio Java (35 min)

Para cada reto: **1)** escribe tu predicción, **2)** programa, **3)** ejecuta y anota qué ha pasado, **4)** si no coincide, explica por qué. Si te atascas, usa las pistas por orden. Los programas `Eco` y `Espera` están en `material/`.

### Reto 1 · Leer la salida de un proceso (8 min)
**Enunciado:** lanza `java -version` desde Java y muestra lo que imprime, junto con su código de salida.
**Predice:** ¿lo leerás con `getInputStream()`?
- Pista 1: `new ProcessBuilder("java", "-version").start()`.
- Pista 2: mira también `getErrorStream()`; el código lo da `waitFor()`.
- **Ampliación:** lanza un comando que no exista y captura la excepción. ¿Qué mensaje da?
import java.io.*;

public class Reto1 {
    public static void main(String[] args) throws Exception {
        Process p = new ProcessBuilder("java", "-version").start();
        
       
        p.getErrorStream().transferTo(System.out);
        p.getInputStream().transferTo(System.out);
        
        int codigo = p.waitFor();
        System.out.println("Código de salida: " + codigo);

         Ampliación:
        try {
            new ProcessBuilder("comando_falso").start();
        } catch (IOException e) {
            System.out.println("Excepción: " + e.getMessage());
        }
    }
}



### Reto 2 · Enviar datos al hijo (10 min)
**Enunciado:** el programa `Eco` lee líneas de su entrada hasta recibir `fin` y las devuelve en mayúsculas. Haz un padre que le envíe `hola`, `mundo` y `fin`, y muestre lo que responde.
**Predice:** ¿qué pasa si no envías `fin` ni cierras el flujo?
- Pista 1: `getOutputStream()` envuelto en un `PrintWriter` con *autoflush*.
- Pista 2: haz `close()` al terminar. Para lanzar `Eco`, usa `"java", "-cp", System.getProperty("java.class.path"), "Eco"`.
- **Ampliación:** espera con `waitFor(3, TimeUnit.SECONDS)` y, si no termina, `destroy()`.

import java.io.*;
import java.util.concurrent.TimeUnit;

public class Reto2 {
    public static void main(String[] args) throws Exception {
        String cp = System.getProperty("java.class.path");
        Process p = new ProcessBuilder("java", "-cp", cp, "Eco").start();

        try (PrintWriter pw = new PrintWriter(p.getOutputStream(), true)) {
            pw.println("hola");
            pw.println("mundo");
            pw.println("fin");
        } // close() automático al salir del bloque try

        p.getInputStream().transferTo(System.out);

        // Ampliación
        if (!p.waitFor(3, TimeUnit.SECONDS)) {
            p.destroy();
        }
    }
}


### Reto 3 · Secuencial frente a paralelo (10 min)
**Enunciado:** `Espera` duerme los segundos que le indiques. Lanza dos hijos de 2 s: primero uno tras otro, y luego los dos a la vez. Mide el tiempo de cada versión.
**Predice:** ¿cuánto tardará cada una?
- Pista 1: `System.currentTimeMillis()` antes y después.
- Pista 2: en paralelo, haz `start()` de **los dos** antes del primer `waitFor()`.
- **Ampliación:** ¿cuánto tardan 3 hijos de 2 s en paralelo?

public class Reto3 {
    public static void main(String[] args) throws Exception {
        String cp = System.getProperty("java.class.path");
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", cp, "Espera", "2");

        // 1. Secuencial
        long t1 = System.currentTimeMillis();
        pb.start().waitFor();
        pb.start().waitFor();
        System.out.println("Secuencial: " + (System.currentTimeMillis() - t1) + " ms");

        // 2. Paralelo
        long t2 = System.currentTimeMillis();
        Process p1 = pb.start();
        Process p2 = pb.start();
        p1.waitFor();
        p2.waitFor();
        System.out.println("Paralelo: " + (System.currentTimeMillis() - t2) + " ms");
    }
}
Ampliación: Tardan aproximadamente 2 segundos en total, ya que los tres procesos se inician a la vez y sus tiempos de espera transcurren de forma simultánea.

### Reto 4 · Hilos con `Runnable` y `join()` (7 min)
**Enunciado:** crea 3 hilos que duerman 1, 2 y 3 segundos e impriman su nombre al acabar. El `main` debe imprimir cuánto ha tardado **en total** cuando terminen los tres.
**Predice:** ¿cuánto tarda? ¿Y si pones el `join()` justo después de cada `start()`?
- Pista 1: una clase que implemente `Runnable` y reciba los segundos.
- Pista 2: guarda los hilos en un array y haz los `join()` en un segundo bucle.
- **Ampliación:** quita los `join()`. ¿Qué imprime ahora el `main` y cuándo?

public class Reto4 {
    public static void main(String[] args) throws Exception {
        long inicio = System.currentTimeMillis();
        Thread[] hilos = new Thread[3];

        for (int i = 0; i < 3; i++) {
            int seg = i + 1;
            hilos[i] = new Thread(() -> {
                try {
                    Thread.sleep(seg * 1000L);
                    System.out.println(Thread.currentThread().getName() + " terminó (" + seg + "s)");
                } catch (InterruptedException e) {}
            });
            hilos[i].start();
        }

        for (Thread h : hilos) {
            h.join(); // Espera a que terminen todos
        }

        System.out.println("Total: " + (System.currentTimeMillis() - inicio) + " ms");
    }
}
Ampliación: El main imprime un tiempo cercano a 0 ms y lo hace inmediatamente, antes de que terminen los hilos, porque ya no espera a que finalicen y continúa su ejecución hasta el System.out.println final mientras los hilos siguen durmiendo en segundo plano

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
| V/F (10 frases) | 6/10 | |
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
