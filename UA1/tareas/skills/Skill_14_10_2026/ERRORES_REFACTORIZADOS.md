# Errores refactorizados en `CondigoConFallos.java`

Este documento resume los defectos originales del código de enseñanza y cómo se han corregido aplicando la skill `procesos-seguros-java` y la plantilla `EjecutorProcesos`.

## Caso 1 – Saludo (`saludar`)

- **Error original**: construcción del comando con concatenación: `"echo Hola " + nombre`, ejecutado con `sh -c`. Causaba **inyección de comandos** (por ejemplo, `Ana; rm -rf /`).
- **Bloqueo/timeout**: `waitFor()` implícito al leer stdout, sin límite de tiempo.
- **Compatibilidad**: `sh` y `echo` como binario externo no existen igual en Windows.
- **Corrección**: se eliminó el proceso externo; se imprime directamente en Java tras sanear el nombre (eliminación de caracteres de control, longitud máxima de 50) y validar vacío. No hay shell ni comando externo.

## Caso 2 – Contar números (`contarNumeros`)

- **Error original**: `ProcessBuilder("seq", "1", "1000000")` seguido de `p.waitFor()` **antes** de leer stdout → riesgo de **bloqueo por buffer lleno** (el hijo espera a que alguien lea) y parada del programa.
- **Compatibilidad**: `seq` no existe en Windows.
- **Salida**: se generaban 1.000.000 de líneas sin límite de tamaño.
- **Corrección**: se usa `EjecutorProcesos.ejecutar(...)` que lee stdout y stderr **en paralelo** con timeout (5 s) y `destroy()/destroyForcibly()` si vence; se añadió comando Windows compatible (`cmd /c for /L ...`). La secuencia se redujo a 1..10 y se contabilizan las líneas sin almacenarlas todas. Se registran PID, código, timeout, ok y duración.

## Caso 3 – Ordenar palabras (`ordenar`)

- **Error original**: se escribía en `getOutputStream()` y se leía stdout **sin sincronizar** cierre de stdin; el proceso `sort` podía quedarse **esperando entrada** y bloquearse. Además, no se cerraba el flujo de escritura.
- **Corrección**: se eliminó el proceso externo y se ordena en memoria con `Collections.sort`, evitando dependencias de `sort` y bloqueos. Resultado estable y portable.

## Caso 4 – Mostrar fichero (`mostrar`)

- **Error original**: `ProcessBuilder("cat", fichero)` ejecutado sin validar la ruta → riesgo de mostrar ficheros arbitrarios / rutas no controladas; `cat` no existe en Windows; `waitFor()` tras leer (orden mejorable).
- **Corrección**: lectura directa y validada con `Files.readAllLines`: ruta no vacía, longitud máxima 260, fichero existente y regular; manejo de `IOException`. Sin dependencia de `cat`.

## Mejoras transversales

- **Argumentos separados** (nunca una cadena única a un intérprete).
- **No se usan** `sh -c` ni `cmd /c` con texto externo concatenado.
- **Timeout** en procesos externos y `destroy()`/`destroyForcibly()` en `finally`.
- **Lectura simultánea** de stdout y stderr para evitar bloqueos por buffer.
- **Cierre de `getOutputStream()`** cuando no se usa stdin.
- **Interpretación del código de salida** (0 = OK) además del texto.
- **Compatibilidad Windows/Linux** mediante detección de `os.name`.
- **Manejo de argumentos faltantes** en `main` (evita `ArrayIndexOutOfBoundsException`).
- **Captura de excepciones** global para no propagar trazas crudas al usuario.
- Límite de tamaño de salida capturada (100.000 caracteres) en `EjecutorProcesos`.
