# Informe de uso de la IA

**Nombre:** Miguel Sarmiento Morilla · **Fecha:** 04/11/2026

## 1. Texto que le di a la IA

> Te paso el enunciado completo del ejercicio del teatro (cinco taquillas con cupos 20, 30, 40, 50 y 60, aforo 200, `Thread.sleep(5)` por venta y proceso Auditor). Genera el programa en Java 17, sin librerías externas y sin paquete, con la clase principal `SistemaVentas` y el proceso independiente `Auditor`, cada una con su `main`. Las taquillas tienen que ser hilos que trabajen con sus propios datos, sin variables modificables compartidas, y el principal solo lee el resultado de cada taquilla cuando ha terminado. Respeta exactamente las líneas de salida y los códigos de salida 0, 1, 2 y 3 del Auditor. Sigue las reglas y la lista de comprobación de la skill `hilos-procesos-java`.

## 2. Fallo o mejora que detecté y cómo lo corregí

- **Aforo 0 aceptado como válido.** En la primera versión el Auditor solo rechazaba valores negativos (`aforo < 0`). Probando a mano con `0` y `0` devolvía el código 0, «coincide», cuando un aforo de 0 no tiene sentido para un teatro. Lo cambié a `aforo <= 0 || total < 0`, y ahora ese caso da el código 3 (datos no válidos).
- **Comprobé si hacía falta cerrar la entrada del Auditor.** Quité el `close()` y el programa siguió funcionando, porque el Auditor lee exactamente dos líneas. Después quité también el `flush()` y el programa se quedó colgado: el `PrintWriter` no envía nada hasta vaciar el buffer y el Auditor espera en `readLine()` para siempre (lo tuve que parar con `timeout`). Por eso dejé los dos, como dice la regla P2.

## 3. Cómo influyó una regla de mi skill

La regla **H2** («lanzar todos los hilos antes de esperar a ninguno») hizo que el código tenga un bucle solo con `start()` y otro distinto con `join()`. Para comprobar que importaba, hice una copia con `start()` y `join()` en el mismo bucle: el tiempo pasó a **1041 ms**, porque cada taquilla esperaba a que acabara la anterior. Con la versión buena sale **unos 311 ms**.

La regla **H3** también se ve en el resultado: cada `Taquilla` tiene su propio `vendidas` privado y el principal suma los cinco valores después del `join()`, en vez de usar un contador compartido con `synchronized` o `AtomicInteger`.

Después de instalar la skill en el agente se la volví a cargar y le pedí que generara el programa desde cero en otra carpeta. Salieron las mismas decisiones que marcan las reglas (dos bucles `start()`/`join()`, contador privado, `-cp`, `flush()` + `close()` y lectura antes de `waitFor()`), pasó la misma lista de comprobación y tardó 310–316 ms. Solo cambiaron detalles de estilo, como usar `try` con recursos para cerrar los flujos, así que mantuve la versión que ya había revisado.

## 4. Cómo comprobé que venden a la vez y que el total es correcto

- Compilé con `javac -Xlint:all *.java` sin avisos y ejecuté `java SistemaVentas` siete veces. Siempre salió `Total vendido: 200 de 200`, cada taquilla con su cupo exacto y `Codigo del auditor: 0`.
- El tiempo fue de **310 a 313 ms** en todas las ejecuciones. Se parece a la taquilla más lenta (60 × 5 ms = 300 ms) y no a la suma (200 × 5 ms = 1000 ms), así que venden a la vez. La variante en secuencia (1041 ms) lo confirma.
- Probé el Auditor por separado pasándole la entrada a mano: `200/200` da el código 0, `200/210` da 1, `200/190` da 2, y `200/abc`, una sola línea, entrada vacía, aforo negativo o `0/0` dan 3.
- Revisé la lista de comprobación de la skill y busqué en los fuentes `package`, `static int`, `synchronized` y `catch` vacíos: no aparece ninguno.
