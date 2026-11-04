# INFORME_IA

## Prompt utilizado

Genera una aplicación Java 17 sin paquetes y sin librerías externas. Debe existir una clase principal llamada SistemaVentas y otra llamada Auditor. Un teatro tiene cinco taquillas con cupos de 20, 30, 40, 50 y 60 entradas. Cada taquilla debe ejecutarse como hilo independiente y vender únicamente sus entradas simulando cada venta con Thread.sleep(5). No debe haber variables modificables compartidas entre hilos. El programa principal debe esperar a todos los hilos mediante join() y mostrar las ventas de cada taquilla, el total vendido y el tiempo empleado. Después debe lanzar un proceso independiente llamado Auditor, comunicarle por entrada estándar el aforo y el total vendido, mostrar su mensaje y su código de salida. Sigue la skill hilos-procesos-java.

## Fallo o mejora detectada

La primera versión generada no comprobaba correctamente el resultado final de las ventas mediante un proceso independiente. Se revisó el código para asegurar que el proceso Auditor recibiera los datos por entrada estándar y devolviera el código de salida adecuado según el resultado de la comprobación.

## Influencia de una regla de la skill

La regla H4 indica que el programa principal debe esperar a todos los hilos mediante join() antes de utilizar sus resultados. Gracias a esta regla se garantiza que todas las taquillas han terminado de vender antes de calcular y mostrar el total vendido.

## Comprobaciones realizadas

Se ejecutó el programa tres veces. En todas las ejecuciones el total vendido fue de 200 entradas.

También se comprobó el tiempo de ventas. La taquilla más lenta vende 60 entradas y cada venta tarda 5 ms, por lo que el tiempo esperado es cercano a 300 ms. Los tiempos observados fueron próximos a esa cifra y muy inferiores a 1000 ms, lo que demuestra que las cinco taquillas trabajan simultáneamente.