# INFORME_IA

**Alumno:** Antonio E. Salces Romero  
**Fecha:** 04/11/2026

## 1. Instrucción dada a la IA

> Desarrolla una aplicación en Java 17, sin paquetes ni librerías externas, con las clases `SistemaVentas` y `Auditor`. En `SistemaVentas`, crea cinco hilos independientes con cupos 20, 30, 40, 50 y 60. Cada hilo simula cada venta con `Thread.sleep(5)`, trabaja con sus propios datos y no comparte variables modificables con los demás. Inicia todos los hilos antes de esperar mediante `join()`. Muestra los resultados de cada taquilla, el total, el tiempo total de ventas medido desde el lanzamiento hasta la finalización de todos los hilos y, después, ejecuta `Auditor` como proceso independiente con `ProcessBuilder`. Envía aforo y total por la entrada estándar, recoge su salida y muestra su código de salida. `Auditor` debe validar los datos y devolver 0 si coinciden, 1 si el total supera el aforo, 2 si es inferior y 3 si los datos no son válidos. Sigue las reglas de la skill `hilos-procesos-java` y cumple exactamente el formato de salida solicitado.

## 2. Revisión crítica y corrección

Una mejora importante que revisé fue evitar consultar el contador de una taquilla mientras todavía podía estar modificándolo. La solución espera a cada hilo con `join()` antes de leer `getVendidas()`. También comprobé que el proceso Auditor no se simula con una llamada a un método: se inicia realmente con `ProcessBuilder`, se le envían dos líneas por su entrada estándar y se recoge su código de salida.

## 3. Influencia de una regla de la skill

La regla 5 indica que todas las taquillas deben iniciarse antes de esperar a que termine cualquiera. Por eso el código tiene un primer bucle con todas las llamadas a `start()` y un segundo bucle con `join()`. Así las cinco ventas se ejecutan concurrentemente.

## 4. Comprobaciones

Compilé los dos archivos con Java 17 y ejecuté `SistemaVentas` tres veces. En las tres ejecuciones, las taquillas vendieron 20, 30, 40, 50 y 60 entradas, respectivamente, y el total fue 200 de 200. El tiempo medido fue del orden de 300 ms, cercano a la taquilla más lenta (60 × 5 ms), y no a la suma de los tiempos de las cinco taquillas. La auditoría mostró que el total coincide con el aforo y terminó con código 0.

### Tiempos observados

- Ejecución 1: Tiempo de ventas: 308 ms
- Ejecución 2: Tiempo de ventas: 308 ms
- Ejecución 3: Tiempo de ventas: 307 ms
