# INFORME DE USO DE IA

## 1. Herramienta utilizada

Para realizar esta práctica he utilizado ChatGPT como agente de inteligencia artificial.

He utilizado la skill `hilos-procesos-java` como guía para desarrollar el programa, siguiendo sus reglas sobre procesos, hilos, concurrencia y calidad del código.

## 2. Desarrollo del programa

La IA ha ayudado a desarrollar dos clases:

- `SistemaVentas.java`: crea cinco hilos independientes para simular las ventas de las cinco taquillas. Cada taquilla vende sus entradas y tarda 5 milisegundos por venta.
- `Auditor.java`: es un proceso independiente que recibe el aforo y el total vendido mediante la entrada estándar. Después comprueba los datos y devuelve un código de salida.

Se han utilizado `ProcessBuilder`, `start()`, `join()` y `Thread.sleep(5)`.

## 3. Comprobaciones realizadas

He ejecutado el programa tres veces y estos han sido los resultados:

| Ejecución | Tiempo |
|---|---:|
| 1 | 342 ms |
| 2 | 341 ms |
| 3 | 344 ms |

En las tres ejecuciones se han vendido las 200 entradas y el Auditor ha devuelto el código 0.

El tiempo medio de las tres ejecuciones ha sido de 342,33 ms.

## 4. Errores y correcciones

Durante la compilación apareció un error porque la importación de `IOException` estaba colocada dentro de la clase.

Se corrigió colocando la importación al principio del archivo, antes de declarar la clase `SistemaVentas`.

Después de corregirlo, los dos archivos compilaron y el programa se ejecutó correctamente.

## 5. Conclusión

La práctica ha permitido comprobar cómo se pueden utilizar varios hilos para ejecutar tareas simultáneamente y cómo se puede comunicar un proceso principal con otro proceso independiente.

También he comprobado que `join()` permite esperar a que terminen todos los hilos antes de calcular el total vendido.
