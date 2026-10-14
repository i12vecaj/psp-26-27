# Seguridad al lanzar procesos desde Java

Leer cuando el comando o sus argumentos provienen de un usuario, de la red o de un modelo de IA.

## Principio
Todo lo que viene de fuera es **dato**, nunca **comando**. Un modelo de IA puede equivocarse o ser manipulado (inyección de instrucciones), así que su salida se trata igual que la de un usuario anónimo.

## Reglas

1. **Lista blanca de programas.** El ejecutable lo decide el código, no el texto externo.
   ```java
   private static final Set<String> PERMITIDOS = Set.of("git", "ls", "ping");
   if (!PERMITIDOS.contains(programa)) throw new IllegalArgumentException("No permitido: " + programa);
   ```
2. **Argumentos separados.** Con `new ProcessBuilder(lista)` el sistema no interpreta `;`, `&&`, `|` ni `$()`. Concatenar y pasar a un intérprete (`sh -c "..."`, `cmd /c "..."`) reabre la puerta a la inyección.
3. **Valida los argumentos**: longitud máxima, caracteres permitidos, rutas dentro de un directorio base.
   ```java
   Path base = Path.of("/datos").toRealPath();
   Path destino = base.resolve(entrada).normalize();
   if (!destino.startsWith(base)) throw new SecurityException("Ruta fuera del directorio permitido");
   ```
4. **Argumentos que empiezan por `-`**: pueden interpretarse como opciones. Usa `--` para separar opciones de datos cuando el programa lo soporte.
5. **Entorno mínimo.** `pb.environment().clear()` y añade solo lo necesario (PATH, etc.). Así el hijo no hereda claves ni tokens.
6. **Timeout y tamaño máximo de salida** siempre, para evitar bloqueos y agotar memoria.
7. **Directorio de trabajo acotado** con `pb.directory(...)`.
8. **Registro de auditoría**: quién pidió qué, comando, PID, código de salida, duración.
9. **Mínimo privilegio**: el proceso Java no debería ejecutarse como administrador/root.

## Agentes de IA que ejecutan herramientas

- Define un **catálogo de herramientas** con nombre, programa fijo y esquema de argumentos; el modelo solo elige herramienta y rellena parámetros.
- Pide **confirmación humana** para acciones destructivas o con efectos externos.
- Devuelve al modelo la salida **truncada y etiquetada como dato** (no como instrucciones).
- No dejes que el modelo escriba el comando completo como texto libre.
