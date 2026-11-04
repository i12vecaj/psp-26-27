INFORME TÉCNICO: Sistema de Ventas con Hilos y Procesos en Java 17

1. Prompt Utilizado para Generar el Programa
"Desarrolla una solución en Java 17 (paquete por defecto, cero dependencias) estructurada en dos ejecutables independientes: `SistemaVentas` y `Auditor`. Diseña las taquillas como hilos concurrentes totalmente desacoplados, sin compartir estado mutable, permitiendo que el hilo coordinador recolecte los resultados únicamente tras completar su ejecución mediante `join()`. El flujo de `SistemaVentas` debe ceñirse estrictamente al formato de salida requerido: listado individual de taquillas, sumatorio total sobre un aforo de 200, métrica temporal de ejecución paralela, el veredicto emitido por el `Auditor` (comunicado mediante entrada estándar) y su respectivo código de salida (0, 1, 2 o 3 según el balance)."

2. Fallos o Mejoras Detectadas y Corregidas
**Bloqueo en `ProcessBuilder` (Deadlock por flujo estándar no cerrado):** En la primera versión generada por la IA, el flujo de salida (`OutputStream`) del proceso hijo no se cerraba explícitamente mediante `try-with-resources` o `close()`, lo que provocaba que el método `waitFor()` quedara bloqueado indefinidamente esperando un marcador de fin de archivo (EOF). Se corrigió encapsulando la escritura en un bloque `try-with-resources` sobre el `PrintWriter`.

3. Influencia de una Regla de la Skill
**Independencia de datos entre hilos (Regla 4 y 5):** Gracias a la regla estricta de la skill que prohíbe el uso de variables globales modificables y exige el paso de datos inmutables por constructor, el diseño evita cualquier condición de carrera (`race condition`). Cada objeto `TaquillaTask` almacena su propio resultado de forma aislada, y el hilo principal únicamente consolida los valores una vez ejecutado el `join()`, garantizando total seguridad de hilos sin necesidad de sincronización pesada.

4. Verificación y Comprobación Experimental
El programa se compiló y ejecutó 3 veces consecutivas con los siguientes comandos:
```bash
javac Auditor.java SistemaVentas.java
java SistemaVentas