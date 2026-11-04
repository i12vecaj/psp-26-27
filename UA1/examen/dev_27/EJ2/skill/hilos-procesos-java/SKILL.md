---
name: hilos-procesos-java
description: Guía para crear, revisar y ejecutar programas Java 17 que usan hilos concurrentes y procesos independientes. Debe utilizarse en ejercicios de clase sobre Thread, concurrencia, ProcessBuilder y comunicación mediante entrada/salida estándar.
---

# Skill: hilos-procesos-java

## Reglas de procesos

1. Para iniciar un programa Java externo se debe utilizar `ProcessBuilder` indicando el ejecutable, el classpath y la clase principal.
2. Los datos que recibe el proceso hijo por entrada estándar se deben escribir mediante `getOutputStream()` y cerrar la entrada cuando se hayan enviado todos los datos.
3. La salida estándar del proceso hijo se debe leer desde `getInputStream()` y se debe obtener su código de finalización con `waitFor()`.
4. El proceso hijo debe validar los datos recibidos y terminar con `System.exit(codigo)` usando los códigos que indique el enunciado.

## Reglas de hilos y concurrencia

5. Cada trabajo concurrente debe tener su propio objeto `Thread` y sus propios datos, evitando variables modificables compartidas entre hilos.
6. Para ejecutar varios trabajos a la vez, primero se deben iniciar todos con `start()` y después esperar a todos con `join()`.
7. El método `run()` contiene el trabajo del hilo; no se debe llamar directamente a `run()` desde el programa principal porque eso no crea un hilo nuevo.
8. El programa principal solo debe consultar los resultados de un hilo después de que `join()` haya confirmado que ha terminado.
9. Si se simula una operación con una pausa, se debe usar `Thread.sleep()` dentro del hilo y controlar `InterruptedException` restaurando la interrupción cuando corresponda.

## Regla de calidad

10. El código debe usar nombres claros, separar responsabilidades en métodos y controlar errores de entrada, interrupciones y ejecución del proceso externo.

## Lista de comprobación final

- [ ] El proyecto usa Java 17 y no tiene declaración `package`.
- [ ] La clase principal y el proceso hijo tienen sus propios métodos `main`.
- [ ] Cada hilo tiene sus propios datos y no modifica datos compartidos.
- [ ] Se llama a `start()` para todos los hilos antes de usar `join()`.
- [ ] Los resultados se leen después de que los hilos hayan terminado.
- [ ] El proceso hijo recibe los datos por entrada estándar.
- [ ] Se lee la salida del proceso hijo y se recoge su código de salida.
- [ ] Se han probado los casos de datos válidos y no válidos del Auditor.
- [ ] Se ha compilado y ejecutado el programa al menos tres veces.
