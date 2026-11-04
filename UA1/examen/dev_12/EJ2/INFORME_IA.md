# INFORME_IA.md

**Alumno:** Miguel · **Ejercicio:** Sistema de ventas del teatro (hilos y procesos en Java)

## 1. Texto que le di a la IA
> Sigue la skill `hilos-procesos-java`. Programa en Java 17, sin librerías externas y sin paquete. Un teatro con aforo de 200 entradas tiene cinco taquillas con cupos de 20, 30, 40, 50 y 60 entradas que venden a la vez (cada venta tarda 5 ms con `Thread.sleep(5)`). Clase principal `SistemaVentas` y proceso independiente `Auditor`, cada una con su `main`. Las taquillas son hilos con sus propios datos, sin variables modificables compartidas; el principal solo lee el resultado cuando la taquilla ha terminado. `SistemaVentas` escribe: una línea `Taquilla N: V entradas` por taquilla, `Total vendido: T de 200`, `Tiempo de ventas: X ms`, la salida del Auditor y `Codigo del auditor: C`. El Auditor recibe por entrada estándar el aforo y el total, escribe una línea de veredicto y sale con 0 (coincide), 1 (mayor), 2 (menor) o 3 (datos no válidos).

## 2. Fallo detectado y corrección
La primera versión de `SistemaVentas` **no compilaba**: había llamado `java` a la variable con la ruta del ejecutable y después usaba `java.io.File.separator`, de modo que el compilador buscaba `io` dentro de la variable `String` (error *cannot find symbol*). Lo corrigí renombrando la variable a `rutaJava` y sustituyendo los nombres completos (`java.io.…`) por `import`, lo que además deja el código más legible (regla Q1). Recompilé con `-Xlint:all` sin avisos.

## 3. Cómo influyó una regla de la skill
La regla **H2** (lanzar todos los hilos con `start()` antes de hacer ningún `join()`) hizo que el programa tuviera dos bucles separados. Sin ella es muy fácil escribir `start(); join();` dentro de un mismo bucle, y el programa sería correcto en el total pero secuencial. La regla **P6** (leer la salida del Auditor antes de `waitFor()`) también fijó el orden: escribir datos, cerrar el flujo, leer, esperar.

## 4. Cómo comprobé que venden a la vez y que el total es correcto
- **Ejecuté el programa 4 veces:** total 200 de 200 y tiempos de 311, 308, 309 y 312 ms, es decir, el de la taquilla más lenta (60 × 5 ms = 300 ms) más un pequeño margen.
- **Contraste:** probé una copia con `join()` justo después de cada `start()`. Dio el mismo total, pero 1041, 1062 y 1545 ms, cercano a la suma (≈ 1000 ms). Por tanto, el tiempo de unos 310 ms solo se explica si las taquillas trabajan simultáneamente.
- **Auditor:** lo probé directamente con 200/200 → código 0, 200/250 → 1, 200/150 → 2, y con texto, número negativo, una sola línea y entrada vacía → 3.
- **Revisión de código:** sin `.run()`, sin `Runtime.exec`, sin `synchronized`/`volatile` y sin campos `static` modificables; cada `Taquilla` solo escribe su propio contador y el principal lo lee tras `join()`.
