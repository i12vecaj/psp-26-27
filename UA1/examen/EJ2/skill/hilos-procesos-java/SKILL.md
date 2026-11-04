---
name: hilos-procesos-java
description: Guía para diseñar, revisar y ejecutar programas Java 17 que usan hilos y procesos independientes. Debe aplicarse en actividades que requieran concurrencia, Thread, sincronización mediante join y comunicación con procesos externos mediante ProcessBuilder.
---

# Skill: hilos-procesos-java

## Reglas verificables

### Procesos
1. **Crear procesos externos con ProcessBuilder.** No simular un proceso independiente llamando directamente a un método de otra clase.
2. **Comunicar la entrada estándar correctamente.** Escribir los datos requeridos en el orden especificado mediante `Process.getOutputStream()` y cerrar el flujo para indicar el fin de la entrada.
3. **Recoger la salida y el código de terminación.** Leer la salida estándar del proceso y llamar a `waitFor()` para obtener su código de salida.
4. **Validar los datos recibidos por el proceso.** Comprobar el número de datos, su formato y sus rangos; devolver un código de error documentado cuando no sean válidos.

### Hilos y concurrencia
5. **Usar un hilo independiente por tarea concurrente.** Cada taquilla debe ejecutarse en su propio `Thread` y todas deben iniciarse antes de esperar a que termine cualquiera.
6. **Mantener los datos de cada hilo independientes.** Cada taquilla recibe su cupo al construirse y modifica únicamente su propio contador; no se usan contadores compartidos ni variables estáticas mutables.
7. **Esperar la finalización con `join()`.** El hilo principal debe ejecutar `join()` sobre cada taquilla antes de consultar su resultado.
8. **Medir el tiempo de concurrencia correctamente.** Iniciar la medición justo antes de lanzar los hilos y terminarla después de que todos los `join()` hayan finalizado; no sumar los tiempos individuales.
9. **Respetar la interrupción.** Si un hilo recibe `InterruptedException`, debe restaurar el estado de interrupción o propagar la excepción, y no ignorarla silenciosamente.

### Calidad
10. **Mantener el código comprobable y portable.** Usar Java 17, paquete por defecto y biblioteca estándar; dar nombres claros, validar entradas y documentar los códigos de salida.

## Lista de comprobación final

- [ ] El proyecto compila con Java 17 y no usa librerías externas.
- [ ] Las clases ejecutables tienen `main` y están en el paquete por defecto.
- [ ] Todas las tareas concurrentes se inician antes de los `join()`.
- [ ] Cada hilo trabaja con su propio cupo y contador.
- [ ] Los resultados solo se leen después de que cada hilo haya terminado.
- [ ] El tiempo medido incluye el lanzamiento y la finalización de todos los hilos.
- [ ] El proceso externo recibe los datos por entrada estándar.
- [ ] Se muestra la salida del proceso y se recoge su código de salida.
- [ ] Se prueban los casos de auditoría correcto, exceso, defecto y datos no válidos.
- [ ] No se entregan archivos `.class` ni configuración del IDE.
