---
name: hilos-procesos-java
description: Reglas comprobables para crear, revisar y probar programas Java 17 que combinan hilos Thread y procesos independientes ProcessBuilder. Usar al programar taquillas o tareas concurrentes con datos privados, comunicacion por entrada/salida estandar, espera de finalizacion y codigos de salida.
---

# Hilos y procesos en Java 17

Aplicar estas reglas al generar codigo y revisarlo antes de entregar. Diferenciar **hilo** (dentro de la misma JVM) de **proceso** (otra JVM iniciada mediante `ProcessBuilder`).

## Reglas verificables sobre procesos

- **P1. Proceso real:** iniciar la clase ejecutable secundaria con `new ProcessBuilder(rutaJava, "-cp", classpath, "Auditor").start()`; no llamar a `Auditor.main(...)` desde la clase principal. Recuperar `rutaJava` con `System.getProperty("java.home")` y `classpath` con `System.getProperty("java.class.path")`.
- **P2. Entrada estandar:** enviar al proceso hijo primero el aforo y despues el total, cada uno en su linea, usando `process.getOutputStream()`; cerrar el flujo tras escribir para indicar fin de entrada.
- **P3. Salida y codigo:** leer el veredicto desde `process.getInputStream()`, imprimirlo sin modificar y llamar a `process.waitFor()` antes de mostrar el codigo de salida. Evitar imprimir mensajes adicionales en la salida normal del padre.
- **P4. Auditor robusto:** interpretar dos enteros no negativos leidos con `System.in`; producir una unica linea y finalizar con `0` si son iguales, `1` si vendido > aforo, `2` si vendido < aforo o `3` si faltan datos, no son enteros o son negativos. No mezclar mensajes de depuracion en `System.out`.

## Reglas verificables sobre hilos y concurrencia

- **H1. Arranque concurrente:** crear exactamente cinco objetos `Thread` o subclases y llamar a `start()` en los cinco antes de ejecutar cualquier `join()`. No usar `run()` como sustituto de `start()`.
- **H2. Estado privado:** guardar `cupo` y `vendidas` en los campos de cada objeto taquilla; no utilizar contadores `static` modificables ni colecciones compartidas para registrar ventas.
- **H3. Simulacion de ventas:** iterar una vez por entrada del cupo y ejecutar `Thread.sleep(5)` en cada iteracion antes de incrementar el contador propio.
- **H4. Orden seguro de lectura:** esperar a cada hilo con `join()` antes de leer su contador de ventas. El padre es quien calcula el total tras haber esperado a los cinco hilos.
- **H5. Interrupcion:** en `run()`, capturar `InterruptedException`, restaurar la marca con `Thread.currentThread().interrupt()` y terminar el bucle sin fingir que se vendio una entrada no procesada.
- **H6. Tiempo de conjunto:** usar `System.nanoTime()` inmediatamente antes de iniciar el primer hilo y despues del ultimo `join()`; convertir la diferencia a milisegundos. El tiempo esperado es cercano al del hilo de 60 entradas, no a la suma de los cinco tiempos.

## Regla de calidad y salida

- **Q1. Entrega verificable:** usar Java 17 sin `package` ni dependencias externas; compilar con `javac --release 17`, comprobar al menos tres ejecuciones y mantener exactamente el orden de salida pedido: cinco taquillas, total, tiempo, veredicto del Auditor y codigo. No entregar archivos `.class`.

## Lista de comprobacion final

- [ ] Hay `SistemaVentas.java` y `Auditor.java`, ambos con `public static void main(String[] args)` y sin `package`.
- [ ] Los cupos son 20, 30, 40, 50 y 60; cada taquilla conserva su propio estado.
- [ ] Las cinco llamadas a `start()` ocurren antes de cualquier `join()`.
- [ ] Cada venta hace `Thread.sleep(5)` y se atiende la interrupcion.
- [ ] Solo se leen resultados despues del `join()` correspondiente.
- [ ] El total es 200 y el tiempo se parece a 300 ms, no a 1000 ms, en tres pruebas.
- [ ] `Auditor` se ejecuta en una JVM hija, recibe dos lineas y emite una sola linea.
- [ ] Se comprueban los codigos 0, 1, 2 y 3 del Auditor.
- [ ] La salida de `SistemaVentas` contiene exactamente nueve lineas, en el orden requerido.
- [ ] La entrega solo contiene fuentes `.java`, `SKILL.md` e `INFORME_IA.md`.
