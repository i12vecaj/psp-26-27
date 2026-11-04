# INFORME_IA.md

## 1. Instrucción utilizada para la IA

Solicité al agente de IA desarrollar la aplicación del examen UA1 Actividad 2 utilizando Java 17, sin librerías externas y sin paquetes. Le indiqué que debía crear las clases `SistemaVentas` y `Auditor`, respetar todos los requisitos del enunciado y utilizar cinco hilos independientes para simular las ventas. También solicité que el Auditor se ejecutase como un proceso independiente mediante `ProcessBuilder`, recibiendo los datos por entrada estándar.

Posteriormente, facilité mi skill `hilos-procesos-java` al agente para revisar el código y comprobar que respetaba sus reglas.

## 2. Revisión crítica y correcciones

Durante la revisión mejoré el código de `Auditor.java`, sustituyendo la lectura inicial con `Scanner` por `BufferedReader` y añadiendo comprobaciones para detectar si faltaban datos de entrada.

También comprobé que `SistemaVentas.java` iniciaba los cinco hilos antes de utilizar `join()` y que cada taquilla trabajaba con sus propios datos.

## 3. Influencia de la skill

La regla 7 establece que todos los hilos deben iniciarse antes de esperar a que finalice cualquiera de ellos.

Esta regla se refleja en `SistemaVentas.java`: primero se ejecuta `start()` para las cinco taquillas y después se utiliza `join()` para esperar a que terminen.

## 4. Pruebas realizadas

Compilé el programa mediante `javac` y lo ejecuté tres veces desde PowerShell.

| Ejecución | Total vendido | Tiempo |
|---|---:|---:|
| 1 | 200 | 358 ms |
| 2 | 200 | 366 ms |
| 3 | 200 | 356 ms |

El tiempo medio fue de 360 ms. En la primera ejecución comprobé también que el Auditor devolvía el código 0.

Para comprobar la concurrencia observé que el tiempo total se aproximaba al de la taquilla más lenta (300 ms), en lugar de acercarse a la suma de los tiempos individuales.

## Conclusión

El programa se ha desarrollado y revisado utilizando la skill creada. Las pruebas realizadas muestran que las taquillas venden las 200 entradas y que el tiempo de ejecución es coherente con el trabajo concurrente.