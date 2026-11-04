# INFORME_IA — Sistema de ventas del teatro

## 1. Texto dado a la IA

> Sigue la skill `hilos-procesos-java`. Genera en Java 17, sin librerías externas y sin paquete, dos clases: `SistemaVentas` (main) y `Auditor` (main, proceso independiente). Un teatro tiene aforo 200 repartido en 5 taquillas con cupos 20, 30, 40, 50 y 60. Las taquillas son hilos que venden a la vez, cada una solo su cupo, con `Thread.sleep(5)` por venta y sin compartir variables modificables; el main lee el resultado de cada una cuando ha terminado. `SistemaVentas` escribe, en este orden: `Taquilla N: V entradas` (N de 1 a 5), `Total vendido: T de 200`, `Tiempo de ventas: X ms` (desde que se lanzan hasta que acaban todas), la salida del Auditor tal como la escribe y `Codigo del auditor: C`. Después lanza `Auditor` como proceso independiente y le envía por su entrada estándar dos líneas (aforo y total). `Auditor` escribe una línea y sale con 0 (coincide), 1 (total mayor), 2 (total menor) o 3 (datos no válidos).

## 2. Fallo detectado y corrección

Al ejecutar, entre `Tiempo de ventas` y la respuesta del Auditor apareció una línea extra (`Picked up JAVA_TOOL_OPTIONS: ...`). La escribe la JVM del proceso hijo por la **salida de error**, y el código generado usaba `redirectErrorStream(true)`, que mezcla stderr con stdout. Así cualquier mensaje de error del hijo se colaba en la salida del programa y rompía el formato exacto exigido.
**Corrección:** `pb.redirectError(ProcessBuilder.Redirect.INHERIT)`. El hijo no se bloquea por su canal de error y solo se lee su stdout. Como la causa estaba en una regla mía demasiado simple, también corregí la regla 2 de la skill y añadí un punto a la lista de comprobación.

## 3. Cómo influyó una regla de la skill

La **regla 6** («todos los `start()` antes de los `join()`, en bucles separados») hizo que la IA generara dos bucles. Si hubiera hecho `start()` y `join()` en el mismo bucle, las taquillas se ejecutarían una tras otra y el tiempo sería ≈ 1000 ms; con la regla sale ≈ 308 ms. La **regla 10** también hizo que el tiempo se midiera con `nanoTime()` justo antes del primer `start()` y después del último `join()`.

## 4. Comprobaciones

- **Venden a la vez:** el cupo mayor (60 ventas × 5 ms) son 300 ms y la suma de todas (200 × 5 ms) serían unos 1000 ms. En 4 ejecuciones el tiempo fue 309, 307, 307 y 308 ms, claramente cercano al de la taquilla más lenta y no a la suma.
- **Total correcto:** en las 4 ejecuciones cada taquilla vendió su cupo (20, 30, 40, 50, 60), el total fue 200 de 200 y el Auditor devolvió código 0. No hay datos compartidos entre hilos y el main lee cada valor solo tras `join()`.
- **Auditor:** probado a mano con `200/200` (código 0), `200/210` (1), `200/190` (2), `200/abc` y una sola línea (3).