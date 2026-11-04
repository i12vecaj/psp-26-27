# Informe de uso de IA – UA1 · Actividad 2

## 1. Texto dado a la IA

Se pegó al agente el enunciado completo de la actividad, con la descripción del teatro y los requisitos técnicos obligatorios: Java 17, sin paquete, clases `SistemaVentas` y `Auditor` con su `main`, hilos sin variables modificables compartidas, formato exacto de salida y códigos de salida 0/1/2/3. También se le pidió que siguiera la skill `hilos-procesos-java`, instalada en `.claude/skills/` del proyecto, que no saliera de la carpeta del proyecto y que no instalara nada.

## 2. Fallo detectado y corrección

Al probar `Auditor` por separado desde PowerShell, **siempre respondía "datos no validos" (código 3), incluso con `200` y `200`**. La causa era que PowerShell 5.1 antepone un BOM UTF-8 (`﻿`) al texto que envía por la tubería, y `trim()` no lo elimina, así que `Integer.parseInt` fallaba. Desde `SistemaVentas` funcionaba porque `PrintWriter` no escribe BOM, pero el Auditor es un proceso independiente y debe aceptar cualquier emisor razonable.

**Corrección:** en `leerNumero` se elimina un BOM inicial antes de `trim()` y `parseInt`. Tras el cambio se repitieron las pruebas y todos los casos dan el código esperado, también con BOM y con saltos de línea CRLF.

| Entrada | Código |
|---|---|
| 200 / 200 | 0 |
| 200 / 210 | 1 |
| 200 / 150 | 2 |
| 200 / abc · solo una línea · vacía · -5 / 0 | 3 |

## 3. Influencia de una regla de la skill

La **regla H2** ("lanzar todos los hilos antes de esperar a ninguno") hizo que el código tuviera un bucle con todos los `start()` y otro bucle aparte con todos los `join()`, en lugar de hacer `start(); join();` dentro del mismo bucle. Para comprobar su efecto se hizo una prueba de control con la versión incorrecta, en `pruebas/` y fuera de la entrega:

| Versión | Ejecución 1 | Ejecución 2 | Ejecución 3 |
|---|---|---|---|
| Secuencial (`start` + `join` en el mismo bucle) | 1141 ms | 1128 ms | 1122 ms |
| `SistemaVentas` (bucles separados) | 338 ms | 335 ms | 339 ms |

Otras reglas también se reflejan en el código:
- **H3/H4:** cada `Taquilla` tiene su propio contador y solo se lee después del `join()`.
- **P2/P3:** se cierra la entrada del Auditor antes de leer su salida, y la salida se lee antes de `waitFor()`.
- **P1:** la JVM y el classpath se toman de `java.home` y `java.class.path`, sin rutas fijas.

## 4. Cómo se comprobó la concurrencia y el total

- **Compilación:** `javac --release 17 -Xlint:all` no da errores ni avisos.
- **Total:** `SistemaVentas` se ejecutó 3 veces más las 3 de la comparativa. Siempre salió `Total vendido: 200 de 200` y `Codigo del auditor: 0`.
- **A la vez:** el tiempo de ventas fue de 335–351 ms. Eso se parece al de la taquilla más lenta (60 × 5 ms ≈ 300 ms, más la sobrecarga de `sleep` en Windows) y no a la suma de todas (200 × 5 ms ≈ 1000 ms). La versión secuencial de control tarda unos 1130 ms, lo que confirma que la diferencia viene del paralelismo.
- **Sin compartir datos:** no hay campos `static` modificables. Cada hilo escribe solo en su propio objeto `Taquilla`, y `join()` garantiza que el hilo principal ve el valor final.
