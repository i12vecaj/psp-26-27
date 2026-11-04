---
name: Concurrency and Process Management Specialist
description: Diseñado para guiar a Claude en la revisión, diseño y depuración de código relacionado con procesos, hilos, concurrencia y calidad de software. Debe usarse siempre que se implementen o analicen sistemas concurrentes, programación multihilo y gestión de procesos del sistema operativo.
---

# Skill: Procesos, Hilos, Concurrencia y Calidad

Esta guía establece las reglas obligatorias que debes seguir al desarrollar o revisar código que involucre concurrencia, paralelismo y gestión de procesos.

## Reglas de Procesos

1. **Gestión de Ciclo de Vida y Sincronización de Procesos (`fork`/`wait`)**:
   - Todo proceso hijo creado mediante llamadas al sistema de clonación/creación debe ser recolectado correctamente por su proceso padre utilizando llamadas de espera (`wait`, `waitpid` o equivalentes) para evitar la generación de procesos zombies o huérfanos.

2. **Manejo Seguro de Comunicación Interprocesos (IPC)**:
   - Al utilizar mecanismos IPC (tuberías, memoria compartida, colas), se debe validar explícitamente el código de retorno de cada operación y asegurar la liberación de los descriptores de archivo o recursos al finalizar.

3. **Control de Errores y Señales del Sistema**:
   - Las operaciones sobre procesos deben contemplar el manejo de interrupciones por señales del sistema (`EINTR`) y registrar adecuadamente los fallos críticos del sistema operativo.

## Reglas de Hilos y Concurrencia

4. **Prevención de Condiciones de Carrera**:
   - Cualquier acceso a variables o estructuras de datos compartidas entre hilos debe estar estrictamente protegido mediante primitivas de sincronización adecuadas (Mutexes, Semáforos o Cerrojos).

5. **Ciclo de Vida y Limpieza de Hilos**:
   - Todo hilo creado debe tener un mecanismo de sincronización explícito (`pthread_join` o equivalente) que garantice que el hilo principal espera su finalización antes de liberar recursos comunes, evitando fugas de memoria o hilos colgados.

6. **Prevención de Interbloqueos (*Deadlocks*)**:
   - Al adquirir múltiples recursos o cerrojos simultáneamente, se debe establecer una jerarquía u orden global estricto de adquisición, o implementar estrategias de reintento con *timeout* para evitar interbloqueos circulares.

7. **Aislamiento y Minimización de Secciones Críticas**:
   - Las secciones críticas protegidas por cerrojos deben mantenerse lo más breves posibles, evitando llamadas bloqueantes complejas o E/S pesadas dentro de las mismas para maximizar el rendimiento concurrente.

## Reglas de Calidad

8. **Documentación de la Estrategia de Concurrencia y Mantenibilidad**:
   - Todo bloque de código multihilo o concurrente debe incluir comentarios claros o documentación adjunta que especifique la política de sincronización aplicada, los invariantes mantenidos y el manejo de excepciones previsto.

---

## Lista de Comprobación Final

Antes de finalizar o entregar cualquier solución que involucre procesos o hilos, verifica los siguientes puntos:
- [ ] ¿Se limpian y esperan adecuadamente todos los procesos creados (`wait`/`join`)?
- [ ] ¿Están todas las secciones críticas protegidas frente a condiciones de carrera?
- [ ] ¿Se previene el interbloqueo en código que utiliza múltiples cerrojos?
- [ ] ¿Se gestionan y validan los errores devueltos por llamadas al sistema relacionadas con hilos y procesos?
- [ ] ¿Se cumple con el estándar de calidad y documentación de la estrategia de concurrencia?