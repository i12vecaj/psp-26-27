# Informe de Revisión Crítica - Actividad 2: Hilos con IA y Skill

## 1. Prompt suministrado a la IA
> "Actúa como un desarrollador experto en Java 17 y aplica estrictamente todas las reglas de la skill `hilos-procesos-java`. Genera la solución completa para el problema de las taquillas (aforo 200, cupos 20, 30, 40, 50, 60, sleep de 5ms por venta). Implementa SistemaVentas y Auditor en paquete por defecto, cumpliendo el formato exacto de salida y gestionando la IPC mediante ProcessBuilder."

## 2. Fallo y mejora detectada en la revisión crítica
- **Fallo inicial**: En la primera propuesta de la IA, el bucle de creación y arranque realizaba un `t.start()` seguido inmediatamente de un `t.join()` dentro de la misma iteración (`for (Taquilla t : taquillas) { t.start(); t.join(); }`).
- **Impacto**: Esto serializaba la ejecución de los hilos, sumando los tiempos secuencialmente ($\approx 1000\text{ ms}$) en lugar de ejecutarse de forma concurrente.
- **Corrección**: Se desacoplaron en dos bucles independientes: el primer bucle inicia todas las taquillas con `start()`, y un segundo bucle posterior realiza los `join()`.

## 3. Influencia concreta de la skill en el resultado
- **Regla 2 (Gestión de Streams en IPC)**: La regla forzó que el agente implementara un bloque `try-with-resources` o cierre explícito con `flush()` sobre el `OutputStream` de `ProcessBuilder`. Sin esta directriz, el proceso subordinado `Auditor` se quedaba bloqueado indefinidamente en `reader.readLine()` al no recibir el fin de flujo (EOF).
- **Regla 4 (Aislamiento de estado)**: Evitó el uso de un acumulador estático compartido (`static int total`) con sincronización innecesaria, obligando a que cada taquilla mantenga su estado interno privado y se consulte tras el `join()`.

## 4. Verificación de concurrencia y resultados
Se compilaron las clases con `javac *.java` y se realizaron 3 ejecuciones de prueba:

1. **Ejecución 1**: Total vendido = 200 de 200 | Tiempo: 312 ms | Salida Auditor: Auditoria correcta... | Código: 0
2. **Ejecución 2**: Total vendido = 200 de 200 | Tiempo: 308 ms | Salida Auditor: Auditoria correcta... | Código: 0
3. **Ejecución 3**: Total vendido = 200 de 200 | Tiempo: 315 ms | Salida Auditor: Auditoria correcta... | Código: 0

**Conclusión de verificación**:
- La taquilla más lenta vende 60 entradas $\times 5\text{ ms} = 300\text{ ms}$.
- Los tiempos obtenidos oscilan entre 308 y 315 ms, lo que certifica que las 5 taquillas operaron de forma concurrente en paralelo y no secuencial (la suma secuencial habría sido $\approx 1000\text{ ms}$).
- El total acumulado fue de 200 y el proceso `Auditor` validó con código `0`.

