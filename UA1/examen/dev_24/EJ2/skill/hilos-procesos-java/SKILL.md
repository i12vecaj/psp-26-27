---
name: hilos-procesos-java
description: Reglas de arquitectura, concurrencia y gestión de procesos en Java 17 estándar para desarrollo de sistemas concurrentes y comunicación interprocesos.
---

# Skill: Hilos y Procesos en Java

Esta skill define las directrices obligatorias para diseñar, codificar y depurar aplicaciones concurrentes e intercomunicación de subprocesos en Java estándar.

## Reglas de Procesos (ProcessBuilder & IPC)

1. **Invocación limpia con ProcessBuilder**: Todo proceso externo o subordinado en Java debe instanciarse mediante `new ProcessBuilder("java", "-cp", classpath, "NombreClase")` o lista de argumentos equivalente, evitando llamadas directas a `Runtime.getRuntime().exec(String)`.
2. **Gestión bidireccional y vaciado de flujos (Streams)**:
   - Los datos enviados a la entrada estándar del proceso hijo deben escribirse mediante su `OutputStream` (`process.getOutputStream()`), usando un `BufferedWriter` o `PrintWriter`, ejecutando `flush()` y cerrando el stream (`close()`) inmediatamente para enviar la señal EOF.
   - La salida estándar del proceso (`process.getInputStream()`) debe leerse en su totalidad con un `BufferedReader` antes de o durante la espera de terminación.
3. **Sincronización y captura de códigos de retorno**:
   - Se debe esperar la finalización del proceso subordinado mediante `process.waitFor()` para obtener el código de salida exacto (`exitValue` / `exitCode`).
   - Los procesos subordinados deben invocar explícitamente `System.exit(code)` con los códigos numéricos definidos en el contrato de especificación.

## Reglas de Hilos y Concurrencia (Thread & Concurrency)

4. **Aislamiento de estado (No Shared Mutable State)**: Los hilos trabajadores deben encapsular sus propios contadores y datos mutables como campos privados de instancia (`Runnable` o extensión de `Thread`). No se permite el uso de variables globales, estáticas o compartidas sin sincronización estricta.
5. **Lanzamiento no bloqueante y concurrente**: Todos los hilos deben iniciarse mediante `start()`. Nunca se debe invocar el método `run()` directamente. Se debe arrancar el lote completo de hilos antes de bloquear con esperas individuales.
6. **Sincronización por barrera (`join`)**: La recolección de métricas y lecturas de resultados de los hilos trabajadores debe realizarse solo después de que cada hilo haya finalizado mediante `hilo.join()`.
7. **Tratamiento estricto de InterruptedException**: Si un hilo maneja demoras (`Thread.sleep()`), `InterruptedException` debe capturarse de forma explícita, restaurando el estado de interrupción con `Thread.currentThread().interrupt()` o relanzándolo según el ciclo de vida del hilo.

## Reglas de Calidad y Entorno

8. **Ausencia de dependencias y conformidad Java 17**: El código debe compilar en Java 17 sin librerías de terceros, respetando las restricciones de empaquetado (paquete por defecto si se especifica) y gestionando adecuadamente las excepciones checked (`IOException`, `InterruptedException`).

---

## Lista de Comprobación Final (Checklist)

- [ ] ¿Los hilos se inician todos con `.start()` antes de ejecutar cualquier `.join()`?
- [ ] ¿El tiempo total de ejecución refleja la concurrencia real (concurrente $\approx \max(t_i)$, no $\sum t_i$)?
- [ ] ¿Se realiza `flush()` y `close()` al escribir en la entrada estándar del proceso hijo?
- [ ] ¿Se captura el código de retorno con `process.waitFor()`?
- [ ] ¿El proceso subordinado evalúa validación de entradas no numéricas y asigna código de salida de error (`System.exit(3)`)?
- [ ] ¿Las clases están en el paquete por defecto y compilan directamente con `javac *.java`?

