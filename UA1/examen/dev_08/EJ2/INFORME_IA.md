# INFORME_IA · Examen UA1 · Actividad 2

**Alumno:** Rafael García Adamuz · **Entrega:** `UA1/examen/dev_XX/EJ2/`

## 1. Texto que le di a la IA

> Actúa como desarrollador Java y sigue al pie de la letra las reglas y la lista de comprobación de mi skill `hilos-procesos-java`. Genera en Java 17, sin librerías externas y sin paquete, un teatro de 200 entradas con cinco taquillas de cupos 20, 30, 40, 50 y 60 que venden a la vez; cada venta es un `Thread.sleep(5)`. Las taquillas son hilos con sus propios datos, sin variables compartidas, y el programa principal lee su resultado solo cuando han terminado. La clase principal es `SistemaVentas` y debe escribir exactamente, en este orden: `Taquilla N: V entradas` por taquilla, `Total vendido: T de 200`, `Tiempo de ventas: X ms` (desde lanzar las taquillas hasta terminar todas), la salida del `Auditor` tal cual y `Codigo del auditor: C`. Después lanza el proceso independiente `Auditor` (otra clase con su `main`), envíale por su entrada estándar el aforo y el total en dos líneas y muestra su respuesta y su código de salida: 0 coincide, 1 total mayor, 2 total menor, 3 datos no válidos. Dame solo el código, sin comentarios, y al final indica qué regla de la skill cumple cada parte.

## 2. Fallos y mejoras que detecté

- **Tildes rotas entre procesos.** El `Auditor` escribía `Datos no válidos` y, al leerlo `SistemaVentas`, salía `Datos no v�lidos`, porque el hijo escribe en la codificación de la consola de Windows y el padre lee en UTF-8. La línea ya no era «tal como la escribe». Lo corregí con mensajes sin tildes en lo que viaja entre procesos y lo añadí a la regla 12.
- **`NullPointerException` atrapada.** Para detectar la entrada vacía, el `Auditor` capturaba `NullPointerException`, que es un parche. Lo cambié por una comprobación explícita de `null` en las dos líneas leídas y añadí la regla 13 de validación.
- **Falsa alarma al probar.** Desde PowerShell todos los casos del `Auditor` daban 3 por cómo se pasaba la entrada, no por el código. Repetí las pruebas con tuberías de bash y salieron los códigos esperados.

## 3. Cómo influyeron las reglas de mi skill

- **Regla 7** (lanzar todos los hilos antes de esperar a ninguno): `SistemaVentas` tiene un bucle con los cinco `start()` y otro con los cinco `join()`. Con `start()` y `join()` en el mismo bucle las ventas irían en serie y tardarían unos 1000 ms en vez de unos 340 ms.
- **Regla 3** (cerrar el flujo de escritura): el `try-with-resources` cierra la entrada del `Auditor` y este recibe el fin de datos; sin cerrarla se quedaría esperando.

## 4. Cómo comprobé que venden a la vez y que el total es correcto

- Ejecuté `SistemaVentas` tres veces: `Total vendido: 200 de 200` y `Codigo del auditor: 0` siempre.
- El tiempo fue de 341, 337 y 341 ms. Se parece a la taquilla más lenta (60 ventas × 5 ms = 300 ms más el arranque) y no a la suma de todas (200 × 5 ms = 1000 ms), así que venden a la vez.
- Los resultados se leen después de `join()` y no hay variables compartidas, por lo que no hay carreras y el 200 no depende de la suerte.
- Probé el `Auditor` con `200/200`, `200/210`, `200/150`, `abc/1`, una línea, vacía, `200/-5`, `0/0` y `99999999999/1`, y obtuve los códigos 0, 1, 2, 3, 3, 3, 3, 3 y 3.
