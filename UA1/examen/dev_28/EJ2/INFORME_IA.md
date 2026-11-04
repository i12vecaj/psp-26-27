# Informe IA · Examen UA1 · Actividad 2

**Alumno:** David Villena · **Fecha:** 04/11/2026 · **Agente:** Claude Code · **Skill:** `hilos-procesos-java`

## 1. Instrucción dada a la IA (tras crear la skill)

> Genera en Java 17, sin librerías externas y sin paquete, las clases SistemaVentas y Auditor (cada una con su main). Cinco taquillas (hilos) con cupos 20, 30, 40, 50 y 60 venden a la vez con datos propios, Thread.sleep(5) por venta. SistemaVentas imprime exactamente "Taquilla N: V entradas" (N de 1 a 5), "Total vendido: T de 200", "Tiempo de ventas: X ms" (desde antes de arrancar las taquillas hasta que terminan todas), la salida del Auditor tal cual y "Codigo del auditor: C". Auditor es un proceso independiente que lee por entrada estándar dos líneas (aforo y total), escribe una línea con su veredicto y sale con 0 (coincide), 1 (mayor), 2 (menor) o 3 (datos no válidos). Sigue todas las reglas de la skill hilos-procesos-java.

## 2. Fallos y mejoras corregidos

- **Fallo en Auditor:** hacía `Integer.parseInt(entrada.readLine().trim())`.
  - Con entrada vacía o sin segunda línea lanzaba `NullPointerException` y salía con **código 1**, el de "exceso".
  - Con un total de `-5` devolvía **código 2**, el de "faltan entradas".
  - **Corrección:** el método `leerNumero()` devuelve -1 si la línea es `null`, no es numérica o es negativa, y en ese caso el programa sale con **código 3**.
- **Mejoras tras la revisión de Codex:**
  - `PrintWriter` se cambió por `BufferedWriter`, porque `PrintWriter` ocultaba los errores de escritura.
  - Si falla la comunicación con el Auditor, se llama a `destroy()` para no dejar el proceso hijo vivo.
  - Se añadió `-encoding UTF-8` al comando de compilación.

## 3. Influencia de una regla de la skill

**Regla H2:** "un bucle de `start()` y otro distinto de `join()`". `SistemaVentas` sigue esa estructura. Una prueba temporal, que no está en la entrega, hacía `start()` y `join()` en el mismo bucle: tardó **1133–1141 ms**, frente a unos **340 ms** de la versión entregada. La regla P4 es la que permitió detectar el fallo del apartado 2.

## 4. Comprobación de la concurrencia y del total

- **Compilación:** `javac --release 17 -encoding UTF-8 -d <carpeta temporal> src/*.java`, sin avisos.
- **Ejecuciones con la versión final:** 5. En todas, cada taquilla vendió su cupo, el total fue **200 de 200** y el Auditor devolvió **código 0**. Tiempos: **339, 340, 343, 340 y 339 ms**.
- **Concurrencia:** el tiempo se acerca al de la taquilla más lenta (60 × 5 ms ≈ 300 ms, más el retraso real de `sleep` en Windows). Está muy lejos de la suma de todas, unos 1000 ms.
- **Auditor por separado:**
  - 200/200 → 0
  - 200/201 → 1
  - 200/150 → 2
  - `abc`, entrada vacía, falta una línea o `-5` → 3

Las pruebas las ejecutó el agente de IA. Los datos se han copiado de la salida real.
