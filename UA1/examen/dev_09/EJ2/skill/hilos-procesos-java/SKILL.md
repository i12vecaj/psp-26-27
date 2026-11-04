
---
name: hilos-procesos-java
description: Skill para desarrollar, revisar y corregir programas Java 17 que utilizan hilos, concurrencia y procesos independientes. Debe utilizarse cuando una actividad requiera crear Threads, coordinar tareas concurrentes, sincronizar resultados o ejecutar otros programas mediante ProcessBuilder.
---

# Skill: Hilos y procesos en Java

## 1. Objetivo

Establecer unas reglas para desarrollar programas en Java 17 que utilicen hilos y procesos independientes de forma correcta, sencilla y segura.

El código debe ser claro, ordenado y adecuado al nivel de programación trabajado en clase.

## 2. Reglas de procesos

1. **Proceso independiente:** cuando se necesite ejecutar otro programa Java, se utilizará `ProcessBuilder` para crear un proceso independiente.

2. **Comunicación por entrada estándar:** los datos destinados al proceso hijo se enviarán mediante `getOutputStream()`, escribiendo los valores en el orden solicitado y separados por saltos de línea.

3. **Lectura de resultados:** la salida estándar del proceso hijo se recogerá mediante `getInputStream()` y un `BufferedReader`, para poder mostrar su respuesta en el proceso principal.

4. **Código de salida:** se utilizará `waitFor()` para esperar a que termine el proceso y obtener su código de salida mediante `exitValue()` o el valor devuelto por `waitFor()`.

## 3. Reglas de hilos y concurrencia

5. **Creación de hilos:** cada tarea concurrente tendrá su propio hilo, utilizando una clase que extienda `Thread` cuando resulte adecuado.

6. **Datos independientes:** cada hilo trabajará con sus propios datos y variables locales. Se evitará compartir variables modificables entre distintos hilos.

7. **Ejecución simultánea:** todos los hilos se iniciarán mediante `start()` antes de esperar a que finalice cualquiera de ellos.

8. **Finalización de hilos:** se utilizará `join()` para esperar a que cada hilo termine antes de consultar sus resultados.

9. **Simulación de trabajo:** cuando una actividad requiera simular tiempo de trabajo, se utilizará `Thread.sleep()` con el tiempo indicado en el enunciado.

10. **Medición del tiempo:** se utilizará `System.nanoTime()` para medir el tiempo transcurrido entre el inicio de los hilos y la finalización de todos ellos.

## 4. Reglas de calidad

11. **Claridad y validación:** el programa tendrá nombres descriptivos, código organizado y validación de los datos de entrada. No se añadirán funcionalidades innecesarias ni librerías externas cuando no estén permitidas.

## 5. Lista de comprobación final

- [ ] El programa utiliza Java 17.
- [ ] No utiliza librerías externas.
- [ ] Cada hilo tiene sus propios datos.
- [ ] Todos los hilos se inician antes de utilizar `join()`.
- [ ] Los resultados solo se consultan cuando los hilos han terminado.
- [ ] El tiempo se mide correctamente.
- [ ] El proceso independiente se ejecuta mediante `ProcessBuilder`.
- [ ] El proceso hijo recibe los datos por entrada estándar.
- [ ] Se recoge la salida estándar del proceso hijo.
- [ ] Se obtiene y muestra el código de salida.
- [ ] Los datos de entrada se validan.
- [ ] El código es claro, ordenado y fácil de mantener.
