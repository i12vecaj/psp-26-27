# Informe actividad 2

Nombre: Federico Lora
Fecha: 04/11/2026

## Texto que le di a la ia

Le pegue el enunciado entero de la actividad 2 con todos los requisitos tecnicos y le pedi esto.

> Hazme el ultimo ejercicio en Java 17 sin paquete, con las clases SistemaVentas, Taquilla y Auditor en la carpeta src. Sigue mi skill hilos-procesos-java. Codigo sencillo como los ejemplos de clase y comentarios cortos. Pruebalo 3 veces y prueba tambien los codigos del Auditor.

## Fallo o mejora que vi

En el ejercicio anterior del examen la ia me dio un codigo demasiado profesional para lo que hemos dado. Usaba listas, PrintWriter, try con parentesis, una cabecera larga con copyright y comentarios largos. Se lo hice cambiar por arrays y por OutputStream con write, flush y close, como en los ejemplos de clase, y comentarios cortos. Para que no volviera a pasar lo meti en la skill (reglas 2 y 10), y en este ejercicio ya salio asi a la primera.

## Como influyo una regla de la skill

La regla 7 dice que los hilos no comparten variables. Por eso cada Taquilla guarda sus entradas en su propio atributo `vendidas` y no hay un total comun. El main suma el total al final con `getVendidas()`, solo despues del `join()` de todas.

## Como comprobe que venden a la vez y que el total esta bien

- Lo ejecute 3 veces. Las tres dio Total vendido 200 de 200 y el Auditor con codigo 0. Los tiempos fueron 345, 338 y 339 ms.
- Eso se parece a la taquilla mas lenta, 60 entradas por 5 ms que son 300 ms, y no a la suma, que son 1000 ms.
- Para estar seguro cambie en una copia `start()` por `run()`. Asi van una detras de otra y tardo 1099 ms.
- Probe el Auditor solo. Con 200 y 200 da 0, con 200 y 210 da 1, con 200 y 150 da 2, y con letras o sin datos da 3.
