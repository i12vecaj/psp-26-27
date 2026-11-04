# Informe de revisión

## Lo que está bien

- **Hilos:** creo las 5 taquillas, las lanzo todas con `start()` y solo después hago `join()` (`Teatro.java:21-32`). El tiempo real es de unos 340 ms frente a los ~1000 ms que tardaría en secuencial.
- **Datos propios por hilo:** cada `Taquilla` usa su `cupo` y su `vendidas` privados; no tengo variables `static` mutables ni contadores compartidos (`Taquilla.java:4-5`).
- **Proceso:** escribo exactamente dos líneas por `getOutputStream()`, con `flush()` y cierre del flujo, y consumo la salida antes de llamar a `waitFor()` (`Teatro.java:56-74`).
- **Auditor:** valida `null`, formato y negativos, y devuelve los códigos 0/1/2/3. He probado los cuatro casos.
- **Medición del tiempo:** uso `nanoTime` justo antes del primer `start()` y después del último `join()`, sin incluir el lanzamiento del `Auditor` (`Teatro.java:20-33`).

## Errores cometidos

1. **El error principal:** en la primera versión usaba `new ProcessBuilder("java", "Auditor")`, dando por hecho que el directorio de trabajo contenía `Auditor.class`. En un IDE o al compilar en `bin/`/`out/` falla con `ClassNotFoundException`. No lo detecté hasta que apareció el error. Lo corregí usando `java.home` y `java.class.path` (`Teatro.java:48-52`) Como en el ejercicio numero 3.

2. Borré los `.class` generados durante las pruebas, así que ahora hay que recompilar.

3. No tengo pruebas automatizadas: la validación fue manual.

## Riesgos pendientes

- Si `java.class.path` llega vacío (por ejemplo con `java Teatro.java` en modo fuente), el proceso hijo seguirá fallando. Como mejora usaría `"."` o `pb.directory(new File(System.getProperty("user.dir")))`.

- Si un `join()` se interrumpe, salgo sin esperar al resto de taquillas (`Teatro.java:28-32`). Es aceptable para el ejercicio, pero se puede mejorar.

- `Auditor` ignora las líneas extra; según el enunciado es correcto, pero conviene tenerlo en cuenta.

## Veredicto

El programa sigue las normas que dice en la skill y el resultado es el correcto y esperado. El unico fallo que detecte es por probarlo en un ide externo donde no estaban compiladas las clases.

