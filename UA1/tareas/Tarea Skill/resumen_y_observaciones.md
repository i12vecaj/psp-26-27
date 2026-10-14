# Correcciones realizadas

Este proyecto contenía errores intencionados en varios ejemplos de ejecución de comandos. Se corrigieron sustituyendo esos comandos por operaciones de Java y validando los argumentos de entrada.

## Fallos encontrados y corregidos

- **Saludo (caso 1):** concatenar el nombre del usuario en un comando ejecutado con `sh -c` permitía inyección de comandos. Se eliminó el shell y ahora Java imprime el saludo directamente.
- **Conteo (caso 2):** se esperaba a que terminara `seq` antes de leer su salida, lo que podía bloquear el programa cuando se llenaba la tubería. Se reemplazó por el conteo directo en Java.
- **Ordenación (caso 3):** `sort` esperaba el fin de la entrada, pero el flujo de entrada no se cerraba; además, se usaba la codificación predeterminada. Se ordena la lista en Java y se evita el proceso externo.
- **Lectura de fichero (caso 4):** se ignoraba el código de salida de `cat`, se anunciaba éxito aunque hubiera errores y no se controlaban rutas o fallos de lectura. Se lee con `Files`, se usa UTF-8 y se informa de errores antes de mostrar el mensaje de éxito.
- **Argumentos y errores:** se accedía a `args[0]` sin comprobar que existiera y no había una opción por defecto para valores desconocidos. Ahora se valida la opción y se muestra el uso del programa; los errores de entrada y lectura se presentan con mensajes claros.

## Nota

La versión corregida de ordenación conserva las tres palabras de ejemplo (`pera`, `manzana` y `banana`). La lectura del fichero sigue permitiendo cualquier ruta que el usuario tenga permiso para leer; una aplicación de servicio debería restringirla a una carpeta autorizada.

## Observaciones adicionales

- El conteo directo muestra el resultado esperado para el rango fijo de 1 a 1 000 000. Si en el futuro se quiere contar valores generados dinámicamente, conviene iterarlos en Java en lugar de imprimir una constante.
- La ordenación usa `String.CASE_INSENSITIVE_ORDER`, por lo que ignora diferencias entre mayúsculas y minúsculas. Para ordenar palabras con reglas lingüísticas del español (por ejemplo, tildes y `ñ`), se puede configurar un `Collator` con la región `es`.
- `Files.lines()` devuelve un flujo que debe cerrarse. En una implementación final, es preferible recorrerlo con `try-with-resources` para garantizar el cierre incluso si ocurre un error durante la lectura.
- Los métodos ya no crean procesos externos, así que desaparecen la gestión de `stderr`, `waitFor()` y el cierre de tuberías. Si en otro caso sí fuera imprescindible ejecutar un proceso, habría que consumir su salida y errores, cerrar todos los flujos y comprobar el código de salida.
