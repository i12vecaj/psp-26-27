# INFORME DETALLADO DEL USO DE IA: EJ2 (Taquillas, hilos y proceso Auditor)

> Documento ampliado, complementario a `INFORME_IA.md` (que es el de una página para la entrega).
> Recoge el proceso completo de la conversación, los prompts, las verificaciones y lo que no se verificó.

---

## 1. Resumen ejecutivo

| Aspecto | Resultado |
|---|---|
| Entregables | `SKILL.md`, `SistemaVentas.java`, `Auditor.java`, `INFORME_IA.md` |
| Reglas de la skill | 14 (5 de procesos, 6 de hilos y concurrencia, 3 de calidad) + lista de comprobación de 12 puntos |
| Primera compilación | **Falló** (4 errores, `cannot find symbol`) |
| Compilación tras corregir | Correcta con `javac --release 17` |
| Ejecuciones completas de `SistemaVentas` | **3** con el código final (más 1 intento inicial que no llegó a compilar) |
| Resultado de las 3 ejecuciones | Total 200 de 200, tiempo 311 / 309 / 310 ms, código de auditor 0 |
| Pruebas del `Auditor` en aislamiento | 4 casos (códigos 1, 2, 3 y 3) |
| Auditoría final contra la skill | Búsquedas sobre el código para cada regla (P1-P5, H1-H6, Q1-Q3) |
| Ficheros `.class` en la entrega | 0 |

---

## 2. Los prompts y su importancia

La calidad del resultado dependió sobre todo del **contexto inicial** que diste. Tu primer mensaje contenía cinco bloques, y cada uno aportó algo distinto.

| Bloque del prompt | Qué aportó | Cómo se reflejó en el resultado |
|---|---|---|
| Contexto del examen: "ejercicio dirigido con IA, saber usarla, guiarla y la respuesta más idónea" | Fijó el objetivo: no solo que funcione, sino que haya criterio y revisión | Se priorizó verificar y documentar fallos, no solo entregar código |
| Enunciado (teatro, 200 entradas, 5 taquillas, 5 ms por venta, Auditor) | El dominio y el comportamiento funcional | Cupos 20/30/40/50/60, `Thread.sleep(5)`, lanzamiento del proceso Auditor |
| Pasos obligatorios (skill con >=8 reglas, 3 ejecuciones, informe) | El procedimiento de trabajo y la estructura de la skill | Skill con cabecera, 14 reglas y checklist; 3 ejecuciones; informe con 4 apartados |
| Requisitos técnicos (Java 17, sin paquete, nombres de clase, formato exacto de salida, códigos 0-3) | Restricciones comprobables | Regla Q1 de la skill; salida exacta; `Auditor` con 4 códigos |
| Criterios de evaluación y estructura de entrega por git | Qué se puntúa y dónde va cada fichero | Carpetas `skill/`, `src/`, informe; ningún `.class` |

**Por qué importan:** un prompt vago ("haz un programa con hilos") habría producido un código que quizá compila pero incumple el formato, el paquete o la independencia de datos entre hilos. Que el prompt incluyera requisitos **verificables** permitió convertirlos en reglas de la skill y en una checklist contra la que auditar.

Mensajes posteriores y su función:
1. *"Genial, pues comparte lo generado"* → entrega de los ficheros y recordatorio de la estructura del repositorio.
2. *"Puedes comprobar tú mismo, usando la skill generada..."* → verificación independiente de que la skill sirve para la aplicación.
3. *Esta petición* → documentación ampliada del proceso.

---

## 3. Cómo se creó la skill

1. **Fuente de las reglas.** Se derivaron del enunciado, de los requisitos técnicos y de buenas prácticas de Java sobre `Thread` y `ProcessBuilder`. No se tuvieron a mano tus apuntes de clase, así que conviene contrastarlas con ellos.
2. **Estructura.** Cabecera YAML con `name: hilos-procesos-java` y `description` (qué hace y cuándo usarla), reglas agrupadas, y lista de comprobación final.
3. **Reglas de procesos (P1-P5):** `ProcessBuilder` con argumentos separados; `java.home` y `java.class.path` portables; comunicación por flujos estándar cerrando la entrada; `waitFor` con timeout; validación de entradas en el hijo.
4. **Reglas de hilos (H1-H6):** datos propios por hilo; todos los `start()` y luego todos los `join()`; `start()` y no `run()`; leer resultados solo tras `join()`; tratar `InterruptedException`; medir con `nanoTime()`.
5. **Reglas de calidad (Q1-Q3):** formato exacto y requisitos técnicos; sin números mágicos; cerrar recursos.
6. **Cada regla es verificable**: se puede comprobar leyendo el código o ejecutándolo (por ejemplo con `grep` de `start`/`join`).
7. **Mejora durante el desarrollo.** La regla P3 se **modificó** tras detectar un fallo real (ver sección 5): originalmente recomendaba `redirectErrorStream(true)`.

### Cómo se puso a prueba la skill

- **Instalación:** el usuario la subió a su cuenta y apareció como `hilos-procesos-java` en la lista de skills disponibles.
- **Carga:** se invocó con la herramienta de skills y se comprobó que su contenido coincide con el de la carpeta de entrega, incluida la P3 corregida.
- **Auditoría del código contra cada regla** mediante búsquedas sobre `SistemaVentas.java` y `Auditor.java`:
  - P1/P2/P4: `new ProcessBuilder`, `java.home`, `java.class.path`, `waitFor`, `destroyForcibly`, `exitValue` presentes; sin `Runtime.exec`.
  - H2/H3: un bucle de `start()` (línea 59) y otro de `join()` (línea 63); ningún `.run()`.
  - H5: `catch (InterruptedException)` con `Thread.currentThread().interrupt()`.
  - H6: `nanoTime()` antes del primer `start()` y después del último `join()`.
  - H1: sin `static` mutables, `synchronized`, `volatile` ni `Atomic`.
  - Q1-Q3: sin `package`; constantes con nombre; `try-with-resources`.
- **Límite honesto de esta prueba:** la skill se usó para **auditar** el código y para guiar su escritura, pero no se hizo un experimento comparativo (generar un programa con la skill y otro sin ella, o con un agente independiente). Es una validación de coherencia, no una evaluación estadística.

---

## 4. Cómo se generó y verificó el código

### Diseño
- `SistemaVentas`: clase interna `Taquilla extends Thread` con campos `cupo` y `vendidas`. Solo el propio hilo incrementa `vendidas`; el hilo principal la lee con `getVendidas()` **después** de `join()`, lo que garantiza la visibilidad de los datos sin compartir ni sincronizar.
- Lanzamiento: se toma `nanoTime()`, se hace `start()` de las cinco taquillas, luego `join()` de todas, y se calcula el tiempo.
- Salida en el orden exacto del enunciado: cinco líneas `Taquilla N: V entradas`, `Total vendido`, `Tiempo de ventas`, salida del Auditor y `Codigo del auditor`.
- `Auditor`: lee dos líneas de la entrada estándar, valida `null`, formato numérico y negativos, imprime una línea y sale con 0, 1, 2 o 3.

### Historial de verificaciones

| Nº | Prueba | Resultado |
|---|---|---|
| 1 | Primera compilación (versión inicial) | **Falló**: 4 errores `cannot find symbol` (la variable `java` ocultaba el paquete `java.*`) |
| 2 | Pruebas del `Auditor` directo: `200/201` | Código 1 |
| 3 | `200/150` | Código 2 |
| 4 | `200/abc` | Código 3 |
| 5 | Solo una línea de entrada (`200`) | Código 3 |
| 6 | Recompilación tras corregir | Correcta (`--release 17`) |
| 7 | Ejecución completa 1 | 200 de 200; **311 ms**; auditor 0 |
| 8 | Ejecución completa 2 | 200 de 200; **309 ms**; auditor 0 |
| 9 | Ejecución completa 3 | 200 de 200; **310 ms**; auditor 0 |
| 10 | Búsqueda de `.class` en la carpeta de entrega | 0 encontrados |

> Nota: las pruebas 2-5 se lanzaron en la misma tanda que la primera compilación fallida. Funcionaron porque `Auditor.java` sí compiló; `SistemaVentas.java` no. Por eso las **ejecuciones completas válidas son las tres del código final**.

### Cómo se comprobó la concurrencia
- **Tiempo.** Las taquillas son independientes: la más lenta (60 ventas x 5 ms) tarda 300 ms. Si se ejecutaran en serie serían 200 x 5 ms = 1000 ms. Los 309-311 ms medidos corresponden a la taquilla más lenta más un pequeño sobrecoste de arranque de hilos y planificación.
- **Estructura.** El bucle de `start()` termina antes del primer `join()`; ningún `join()` está dentro del primer bucle.
- **Datos.** Cada taquilla vendió exactamente su cupo (20, 30, 40, 50, 60) y el total fue siempre 200, lo cual es coherente con no compartir variables modificables.

---

## 5. Fallos y mejoras detectados (revisión crítica)

### Fallo 1: el código no compilaba
- **Síntoma:** `cannot find symbol: variable nio` y `variable util` (4 errores).
- **Causa:** en `ejecutarAuditor` existía una variable local llamada `java` (ruta del ejecutable). Más abajo se escribía `java.nio.charset...` y `java.util.concurrent...`; el compilador interpretaba `java` como la variable de tipo `String`, no como el paquete.
- **Corrección:** `import` explícitos de `Paths`, `StandardCharsets`, `TimeUnit`, `Writer`, etc., y renombrado de la variable a `ejecutableJava`.
- **Lección:** el compilador detecta este error enseguida; la comprobación `javac` es obligatoria antes de entregar.

### Fallo 2 (potencial, anticipado): contaminación de la salida del Auditor
- **Síntoma observado:** al probar `Auditor` en aislamiento aparecía por pantalla la línea `Picked up JAVA_TOOL_OPTIONS...`, que la JVM escribe por **stderr**.
- **Riesgo:** la primera versión usaba `redirectErrorStream(true)`, que mezcla stderr con stdout del hijo. En un entorno con `JAVA_TOOL_OPTIONS` (habitual en algunos IDE, servidores o aulas) esa línea se habría mostrado como parte de "la salida del Auditor", incumpliendo el formato exacto.
- **Corrección:** `pb.redirectError(ProcessBuilder.Redirect.INHERIT)`: el stderr del hijo va a la consola del padre sin entrar en el flujo que se lee.
- **Matiz:** en la ejecución final se redirigió el stderr del padre a `/dev/null` para mostrar la salida limpia, por lo que con `INHERIT` el mensaje puede seguir viéndose en la consola real. Es ruido de la JVM, no parte del veredicto, pero conviene saberlo antes de ejecutar en el aula.
- **Efecto en la skill:** la regla P3 se reescribió para reflejar esta decisión.

### Revisión adicional sin fallos
- `Auditor` valida `null`, formato y valores negativos.
- El padre usa `waitFor` con timeout y `destroyForcibly`.
- Los recursos se cierran con `try-with-resources`.

---

## 6. Ejemplos concretos de cómo influyeron las reglas

- **H2 (todos los `start()` y luego todos los `join()`):** determinó la estructura de `main` en dos bucles. Es la regla que explica el tiempo de ~300 ms en lugar de ~1000 ms.
- **H1 (datos propios por hilo):** evitó usar un contador compartido y `synchronized`. El resultado es más sencillo y sin condiciones de carrera.
- **P3 (cerrar la entrada del hijo):** evita que el `Auditor` se quede esperando más datos al leer la segunda línea.
- **Q1 (formato exacto):** obligó a revisar textos y orden, por ejemplo `Taquilla N: V entradas` y `Codigo del auditor: C` sin tilde.

---

## 7. Qué NO se ha verificado (limitaciones)

- Las reglas de la skill no se contrastaron con los apuntes de clase; se redactaron a partir del enunciado y de buenas prácticas generales.
- No se probó con otra versión de Java distinta a la del entorno (se compiló con `--release 17`).
- No se ejecutó en Windows; el código usa rutas portables (`java.home`, `java.class.path`), pero no se ha comprobado.
- No se hizo una comparación "con skill / sin skill" ni una evaluación con un agente independiente.
- Los tiempos (311, 309 y 310 ms) son de un entorno cloud y variarán en tu máquina.

---

## 8. Conclusión

El proceso funcionó porque los requisitos del enunciado se tradujeron en **reglas verificables** y estas se usaron para **auditar** el código en lugar de fiarse de la primera versión. La IA generó una primera versión que no compilaba y que tenía un riesgo de formato; ambos puntos se detectaron ejecutando y revisando, no leyendo por encima. El resultado final compila, vende 200 entradas en unos 310 ms con cinco hilos concurrentes, y el `Auditor` responde con los códigos 0 a 3 según el caso.
