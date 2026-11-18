# INFORME_IA — Sistema de Ventas de un Teatro

## 1. Texto proporcionado a la IA

Extracto fiel del prompt (el original añade rol, contexto y condiciones de cierre):

> "Crea la skill `hilos-procesos-java` (`SKILL.md` con `name`, `description`, ≥8 reglas verificables: ≥3 de procesos, ≥4 de hilos, ≥1 de calidad, y checklist final) y **úsala** para generar `SistemaVentas.java` y `Auditor.java` en Java 17, sin `package` ni librerías: 5 taquillas (20/30/40/50/60) en 5 hilos con datos propios, `Thread.sleep(5)` por venta, `start()` y `join()`, tiempo con `System.nanoTime()`; `Auditor` como proceso independiente con `ProcessBuilder`, que recibe aforo y total por `stdin` y sale con 0/1/2/3. Salida exacta: `Taquilla N: V entradas`, `Total vendido: T de 200`, `Tiempo de ventas: X ms`, veredicto y `Codigo del auditor: C`. Compila, ejecuta al menos 3 veces, revisa, corrige e informa sin inventar resultados."

## 2. Fallo detectado y corrección

**Fallo:** `SistemaVentas` leía la salida del hijo en UTF-8, pero `Auditor` escribía con `System.out` en la codificación nativa (Cp1252 en Windows). Al probar un veredicto con tilde, `Auditoría` llegó como `Auditor?a`. Esto incumple "mostrar la salida del Auditor tal como la escribe"; con texto ASCII el fallo quedaba oculto.
**Corrección:** `Auditor` escribe con un `PrintStream` UTF-8 sobre `FileDescriptor.out`, igual que lee el padre. Tras el cambio, la tilde llega intacta.
**Otras mejoras:** con tres líneas de entrada el Auditor devolvía 0; ahora devuelve 3, porque el protocolo son exactamente dos líneas. Además, `PrintWriter`, que oculta los errores de escritura, se sustituyó por `BufferedWriter`.

## 3. Influencia de la skill

Regla **H3**: los resultados se leen solo después de `join()`, que garantiza la visibilidad (*happens-before*). Junto con **H1** (un objeto `Taquilla` por hilo, sin `static` mutable) y **H4** (sin sincronización innecesaria), permitió que el contador `vendidas` fuera un `int` normal, sin `synchronized` ni `AtomicInteger`, leído por `main` tras el bucle de `join()`. La condición de carrera de `BloquesNoSincronizados` se evita por aislamiento, no con cerrojos.

## 4. Comprobación de concurrencia y resultados

Compilado con `javac --release 17 -Xlint:all` (0 errores, 0 avisos; bytecode Java 17) y ejecutado con `java SistemaVentas`:

| Ejecución | Taquillas 1–5 | Total | Tiempo | Código |
|---|---|---|---|---|
| 1 | 20/30/40/50/60 | 200 de 200 | 344 ms | 0 |
| 2 | 20/30/40/50/60 | 200 de 200 | 352 ms | 0 |
| 3 | 20/30/40/50/60 | 200 de 200 | 357 ms | 0 |

- **A la vez:** ~350 ms se parece a los ~300 ms de la taquilla 5, no a la suma (~1000 ms). Contraprueba: con `start(); join();` en el mismo bucle tardó **1126 ms**. El exceso sobre 300 ms se debe a que en Windows cada `sleep(5)` dura ~5,6 ms.
- **Total y Auditor:** el total es la suma de los contadores de cada taquilla tras `join()`. Probado aparte: 200/200 → 0, 200/210 → 1, 200/150 → 2; `abc`, negativo, línea vacía, falta o sobra una línea → 3.
- **Limitación:** sin JDK 17 en el equipo; se compiló con `--release 17` y se ejecutó con JDK 27.
