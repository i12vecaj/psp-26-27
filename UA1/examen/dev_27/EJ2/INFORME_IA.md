# INFORME_IA

## Texto enviado a la IA

"Genera una aplicación en Java 17, sin paquetes ni librerías externas, siguiendo la skill `hilos-procesos-java`. Crea `SistemaVentas` y `Auditor`, ambos con `main`. SistemaVentas debe lanzar cinco hilos independientes con cupos 20, 30, 40, 50 y 60. Cada hilo simula cada venta con `Thread.sleep(5)`. Inicia los cinco antes de esperar con `join()`, calcula el tiempo total desde el lanzamiento hasta el final y muestra las líneas exigidas en el enunciado. Después ejecuta Auditor con ProcessBuilder, envíale por la entrada estándar el aforo y el total vendido, lee su salida y muestra su código de salida. Auditor debe validar los datos y devolver 0 si coinciden, 1 si el total supera el aforo, 2 si es menor y 3 si los datos no son válidos. Mantén el código sencillo y controla las excepciones."

## Revisión crítica

Durante la revisión comprobé que los resultados de cada taquilla se guardan en un atributo propio y que el programa principal no los consulta hasta después de ejecutar `join()`. Esto evita depender de una variable compartida que varios hilos pudieran modificar a la vez. También revisé que se cierren los flujos de entrada y salida del proceso Auditor y que se recoja su código con `waitFor()`.

Una mejora importante fue controlar los datos incorrectos en `Auditor`: si faltan líneas, no se pueden convertir a número o son negativos, muestra "Datos no validos" y termina con código 3.

## Influencia de la skill

La regla 6 indica iniciar todos los hilos con `start()` antes de esperar con `join()`. Por eso el programa inicia las cinco taquillas en un primer bucle y las espera en un segundo. Así trabajan a la vez y el tiempo se aproxima al de la taquilla más lenta, no a la suma de los tiempos.

## Comprobaciones de ejecución

Ejecutar tres veces y completar esta tabla con los tiempos reales obtenidos en el equipo:

| Ejecución | Total vendido | Tiempo de ventas |
|---|---:|---:|
| 1 | 200 de 200 | ______ ms |
| 2 | 200 de 200 | ______ ms |
| 3 | 200 de 200 | ______ ms |

Las cinco taquillas tienen cupos que suman 200. La más lenta vende 60 entradas y tarda aproximadamente 300 ms (60 × 5 ms). Al iniciarse todos los hilos antes de esperar a que terminen, el tiempo total debe acercarse a esos 300 ms, aunque puede variar por la planificación del sistema operativo y la carga del equipo.

El Auditor debe mostrar `Aforo completo` y devolver el código 0 cuando recibe 200 y 200.
