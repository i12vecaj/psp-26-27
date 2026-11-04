---
name: hilos-procesos-java
description: Skill para desarrollar y revisar programas Java que utilizan procesos independientes, hilos y concurrencia. Se debe utilizar en ejercicios de clase donde sea necesario ejecutar tareas simultáneamente, coordinar hilos o comunicar procesos padre e hijo.
---

# Skill: Procesos e hilos en Java

## 1. Reglas sobre procesos

1. Para crear un proceso independiente se utilizará `ProcessBuilder`.
2. Para enviar información al proceso hijo se utilizará `getOutputStream()`, escribiendo los datos en su entrada estándar.
3. Para leer la respuesta del proceso hijo se utilizará `getInputStream()`.
4. Se utilizará `waitFor()` para esperar a que termine el proceso y `exitValue()` para consultar su código de salida cuando haya terminado.

## 2. Reglas sobre hilos y concurrencia

5. Cada tarea que deba ejecutarse simultáneamente tendrá su propio hilo.
6. Cada hilo trabajará con sus propios datos y no modificará variables compartidas con otros hilos.
7. Se utilizará `start()` para iniciar los hilos. No se utilizará `run()` directamente para ejecutar tareas concurrentes.
8. Se utilizará `join()` para esperar a que todos los hilos hayan terminado antes de consultar sus resultados.
9. El tiempo de ejecución se medirá desde que comienzan los hilos hasta que terminan todos.

## 3. Reglas de calidad del código

10. Se utilizarán nombres de variables descriptivos y comentarios sencillos que expliquen las partes importantes.
11. Se controlarán las excepciones y se utilizará Java 17 sin librerías externas.
12. Las clases estarán en el paquete por defecto, sin declaraciones `package`.

## 4. Lista de comprobación final

- [ ] Se utiliza ProcessBuilder para crear el proceso independiente.
- [ ] El proceso hijo recibe los datos mediante su entrada estándar.
- [ ] Se lee la salida del proceso hijo.
- [ ] Se comprueba el código de salida del proceso hijo.
- [ ] Cada taquilla trabaja en un hilo independiente.
- [ ] Se utiliza start() para iniciar los hilos.
- [ ] Se utiliza join() para esperar a que terminen.
- [ ] No existen variables modificables compartidas entre las taquillas.
- [ ] El total vendido coincide con la suma de las cinco taquillas.
- [ ] El tiempo mide la ejecución concurrente de todas las taquillas.
- [ ] El código contiene comentarios y controla las excepciones.
- [ ] El programa funciona con Java 17 y sin librerías externas.