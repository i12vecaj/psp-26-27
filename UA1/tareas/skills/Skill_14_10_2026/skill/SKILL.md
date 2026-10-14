---
name: procesos-seguros-java
description: Guía para que Claude escriba, revise y depure código Java que lanza procesos externos o herramientas de línea de comandos con ProcessBuilder (start, pid, waitFor con timeout, getInputStream, getErrorStream, redirecciones, destroy). Úsala siempre que el usuario pida ejecutar comandos desde Java, integrar una CLI o un script en una aplicación Java, construir un agente o herramienta de IA que invoque programas del sistema, o revisar código con Runtime.exec/ProcessBuilder, aunque no mencione "seguridad" ni "skill". Cubre bloqueos por buffer lleno, timeouts, códigos de salida, inyección de comandos y compatibilidad Windows/Linux.
---

# Procesos externos en Java: patrones seguros

Esta skill hace que el código Java que lanza procesos sea correcto, no bloqueante y seguro frente a entradas no confiables (por ejemplo, texto generado por un modelo de IA o escrito por un usuario).

## Flujo de trabajo

1. **Entiende el caso**: ¿qué comando se ejecuta, quién controla sus argumentos, cuánto puede tardar y qué salida se necesita?
2. **Parte de la plantilla** `assets/EjecutorProcesos.java` (clase reutilizable con timeout, captura de stdout/stderr y resultado tipado). Cópiala y adáptala; no reescribas la lógica desde cero.
3. **Aplica la lista de comprobación** de la sección siguiente.
4. **Si el comando depende de entrada no confiable** (IA, usuario, red), lee `references/seguridad.md` antes de escribir nada.
5. **Si hay un fallo o cuelgue**, lee `references/diagnostico.md`.
6. **Prueba el código** antes de entregarlo (`java Archivo.java` funciona con Java 11+ sin compilar aparte). Comprueba al menos: caso correcto, comando que falla, y comando que supera el timeout.

## Lista de comprobación (aplícala siempre)

- [ ] Comando como **lista de argumentos separados** (`new ProcessBuilder("git","log","-n","5")`), nunca una sola cadena concatenada.
- [ ] **No usar `cmd /c` ni `sh -c`** con texto externo concatenado.
- [ ] `waitFor(timeout, unidad)` y, si vence, `destroy()` y luego `destroyForcibly()`. Nunca `waitFor()` sin límite en código de producción.
- [ ] **Leer stdout y stderr a la vez** (dos hilos, o `redirectErrorStream(true)`). Si no, un buffer lleno bloquea al hijo y a todo el programa.
- [ ] Leer la salida **antes o a la vez** que se espera; nunca `waitFor()` primero y leer después.
- [ ] Cerrar `getOutputStream()` si el hijo lee de stdin, o redirigir con `Redirect.from(...)`/`Redirect.INHERIT`.
- [ ] Interpretar el **código de salida** (0 = correcto) y no solo la salida de texto.
- [ ] Fijar `directory()` y, si procede, limpiar `environment()` (no pasar secretos al hijo).
- [ ] **Limitar el tamaño** de la salida capturada si el comando puede producir mucha.
- [ ] Código **multiplataforma**: detectar el SO o evitar comandos propios de Windows/Linux.
- [ ] Registrar `pid()`, comando (sin secretos) y duración para poder auditar.

## Formato de la respuesta

1. Código completo y ejecutable (o el cambio mínimo sobre el código del usuario).
2. Una nota breve con **qué riesgos se han cubierto** (timeout, buffers, inyección).
3. Si el código original tenía un problema, explica **por qué fallaba** en una o dos frases.

## Cuando el usuario es alumnado o está aprendiendo

Explica con la regla: *getInputStream = leo del hijo; getOutputStream = escribo al hijo*. Mantén la plantilla pero comenta cada bloque. Para material didáctico sobre procesos, genera además un ejercicio con enunciado, pistas y solución.
