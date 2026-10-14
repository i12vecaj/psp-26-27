# Diagnóstico de problemas con procesos

| Síntoma | Causa probable | Solución |
|---|---|---|
| El programa se queda colgado en `waitFor()` | Buffer de stdout/stderr lleno: el hijo espera a que alguien lea | Leer ambos flujos en paralelo o `redirectErrorStream(true)` |
| El hijo no termina nunca | Está esperando entrada por stdin | Cerrar `getOutputStream()` o usar `Redirect.from(fichero)` |
| `IOException: Cannot run program` | El ejecutable no existe o no está en el PATH | Comprobar nombre, PATH y SO (`dir` es un comando interno de `cmd`) |
| Código de salida distinto de 0 sin mensaje | El error va por stderr | Leer `getErrorStream()` |
| Salida con caracteres raros | Codificación distinta a la del sistema | Indicar `Charset` al crear el `InputStreamReader` |
| Procesos huérfanos tras cerrar Java | No se hizo `destroy()` | `destroy()` + `destroyForcibly()` en un `finally` o *shutdown hook* |
| Funciona en Windows y falla en Linux (o al revés) | Comando propio del SO | Detectar `os.name` y elegir comando |
| La salida llega cortada | Se leyó solo una parte antes de terminar | Leer hasta `null` y luego `waitFor()` |
