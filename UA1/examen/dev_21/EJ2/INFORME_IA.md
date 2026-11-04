# Informe de uso de IA · Actividad 2

| | |
|---|---|
| **Alumno** | Juan Raigón Mínguez |
| **Fecha** | 04/11/2026 |
| **Ejercicio** | UA1 · EJ2 · Venta de entradas con taquillas en paralelo y Auditor |
| **Skill usada** | `psp-concurrencia-java` |
| **Entregables** | `src/SistemaVentas.java`, `src/Taquilla.java`, `src/Auditor.java` |

---

## 1. Prompt utilizado

Pegué el enunciado completo de la actividad 2 y debajo añadí este encargo, separado por bloques para que la IA no se saltara nada:

```text
OBJETIVO
Resolver el ejercicio 2 del enunciado de arriba.

CLASES (Java 17, sin paquete, carpeta src)
- SistemaVentas: programa principal.
- Taquilla: la tarea de cada hilo. Hazla con Runnable.
- Auditor: proceso hijo que recibe aforo y total por la entrada estándar
  y termina con 0, 1, 2 o 3.

CÓMO LO QUIERO
- Aplica mi skill psp-concurrencia-java entera.
- Que el propio programa demuestre que las taquillas van a la vez:
  cada una mide su tiempo y el main enseña el tiempo real frente a la suma.
- Main corto, dividido en métodos con nombres claros.
- Nivel de clase: arrays y Scanner, nada de listas, lambdas ni librerías.

PRUEBAS
Ejecútalo 3 veces, haz la contraprueba con run() en una copia y
prueba el Auditor por separado con un caso de cada código.
Dame los resultados en tablas.
```

---

## 2. Problema detectado y cómo se corrigió

**Qué pasó.** El Auditor saca el mensaje "OK" por `System.out` y los de error por `System.err`, como pide la skill. Para comprobar que el padre enseñaba bien un error, en una copia le pasé al Auditor el total con 15 entradas de menos. El código de salida llegaba bien (2), pero el mensaje `INCOMPLETO - quedan 15 de 200 entradas sin vender` no salía en la pantalla del padre:

```text
=============== AUDITORIA ===============
Resultado: codigo 2 (quedan entradas sin vender)
```

**Por qué.** El padre solo lee `getInputStream()`, que es la salida normal del hijo. Lo que el hijo escribe por `System.err` va por otro canal que nadie estaba leyendo.

**Solución.** Añadir `pb.redirectErrorStream(true)` antes de `start()`, que junta las dos salidas del hijo en una. Con eso ya aparece:

```text
=============== AUDITORIA ===============
  > INCOMPLETO - quedan 15 de 200 entradas sin vender
Resultado: codigo 2 (quedan entradas sin vender)
```

Para que no vuelva a pasar en otros ejercicios lo dejé como regla **P2** de la skill y añadí la prueba **V6** a su tabla de verificación.

---

## 3. Regla de la skill que más influyó: H6

> *H6 · El programa demuestra su propio paralelismo. Cada tarea mide cuánto ha tardado ella sola. El main mide el tiempo real y muestra las dos cifras.*

Por esta regla, `Taquilla` guarda en su propio atributo `msAbierta` lo que tarda en vender su cupo, y `SistemaVentas` muestra al final una tabla por taquilla con la suma de esos tiempos al lado del tiempo real. Así no hace falta fiarse de una prueba aparte: en cada ejecución se ve que el tiempo real es casi igual al de la taquilla de 60 entradas y unas tres veces menor que la suma.

Como también se aplica **H4** (nada compartido), cada `Taquilla` cuenta sus `vendidas` en su objeto y el total se calcula en `mostrarResumen()`, que solo se llama cuando `esperarCierre()` ya ha hecho el `join()` de los cinco hilos.

---

## 4. Verificación

### 4.1 Tres ejecuciones normales

| Ejecución | Vendidas | Tiempo real | Suma de tiempos | Código Auditor |
|:-:|:-:|:-:|:-:|:-:|
| 1 | 200 / 200 | 308 ms | 1025 ms | 0 |
| 2 | 200 / 200 | 308 ms | 1025 ms | 0 |
| 3 | 200 / 200 | 308 ms | 1022 ms | 0 |

Teoría: la taquilla más lenta vende 60 × 5 ms = 300 ms; en serie serían 200 × 5 ms = 1000 ms. El tiempo real queda pegado a 300 ms, así que van a la vez.

### 4.2 Contraprueba en serie

En una copia cambié `h.start()` por `h.run()` dentro de `abrir()`:

| Versión | Tiempo real | Suma de tiempos |
|---|:-:|:-:|
| Con `start()` (paralelo) | 308 ms | 1025 ms |
| Con `run()` (serie) | 1030 ms | 1028 ms |

Con `run()` el tiempo real se iguala a la suma: las taquillas van una detrás de otra en el hilo del `main`.

### 4.3 Auditor por separado

Probado con `echo <datos> | java -cp out Auditor` y mirando el código de salida:

| Entrada | Mensaje | Código |
|---|---|:-:|
| `200 200` | OK - 200 entradas vendidas, aforo de 200 completo | 0 |
| `200 210` | SOBREVENTA - 210 vendidas para 200 plazas, 10 de mas | 1 |
| `200 150` | INCOMPLETO - quedan 50 de 200 entradas sin vender | 2 |
| `200 abc` | ERROR - no ha llegado el total vendido o no es un numero | 3 |
| *(vacío)* | ERROR - no ha llegado el aforo o no es un numero | 3 |
| `-5 10` | ERROR - valores fuera de rango (aforo -5, vendidas 10) | 3 |
| `200 -1` | ERROR - valores fuera de rango (aforo 200, vendidas -1) | 3 |

### 4.4 Compilación

`javac --release 17 -Xlint:all -d out src/*.java` compila sin ningún aviso. La entrega no lleva `.class` ni ficheros del IDE.
