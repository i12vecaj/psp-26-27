# Informe de uso de IA

## 1. Texto dado a la IA

**Primera petición:**

> Realiza la siguiente aplicacion en java. Es importante que utilices SKILL.md. Has de tener el cuanta los requisitos técnicos y los pasos obligatorios, no puedes saltarte nada. Enunciado de la actividad. SI tienes dudas realizalas -> [El enunciado de la actividad]

**Segunda petición:**

> Reescribe el codigo siguiendo el nivel repo donde nos encontramos


**Tercera petición:**

> Haz el informe más bonito

## 2. Fallos y mejoras detectados

No encontré ningún fallo de funcionamiento. Lo único que podría considerarse un fallo es que la IA escribió el código de una forma muy avanzada, y le pedí que lo reescribiera al nivel del repositorio. Ahora sigue el estilo de `Padre`, `Hijo` y `Reto4`: 

## 3. Influencia de una regla de la skill

La **regla 5** pide hacer primero todos los `start()` y después, en otro bucle, todos los `join()`. Por eso `SistemaVentas` usa dos bucles separados.

Para comprobar su efecto, probé una copia con el `join()` justo después de cada `start()`:


El total es correcto en los dos casos, así que el fallo no se habría visto mirando solo el total.

## 4. Comprobación de la concurrencia y del total

- **Total:** ejecuté el programa 11 veces. En todas, cada taquilla vendió su cupo, el total fue 200 y el código del Auditor fue 0.
- **Tiempo:** estuvo entre 329 y 351 ms, parecido a la taquilla más lenta (unos 300 ms) y muy lejos de la suma (unos 1000 ms). Supera un poco los 300 ms porque cada `sleep(5)` dura algo más de 5 ms.
- **Venta a la vez:** en una copia con trazas, las cinco taquillas empezaron en el mismo milisegundo. Terminaron 144, 199, 256, 319 y 374 ms después, según su cupo.
