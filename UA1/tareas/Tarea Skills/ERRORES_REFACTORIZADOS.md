# Errores Refactorizados en Procesos Java

Este documento analiza los errores presentes en [`CodigoConFallos.java`](file:///c:/Users/ian6m/Desktop/Skills/src/CodigoConFallos.java) y la solución implementada en [`CodigoSeguro.java`](file:///c:/Users/ian6m/Desktop/Skills/src/CodigoSeguro.java), aplicando las pautas de la skill [`procesos-seguros-java`](file:///c:/Users/ian6m/Desktop/Skills/.agents/skills/procesos-seguros-java/SKILL.md).

---

## 📌 Regla Nemotécnica Fundamental

Al trabajar con `java.lang.Process`:
* `p.getInputStream()`: **Leo del hijo** (captura su `stdout` o `stderr`).
* `p.getOutputStream()`: **Escribo al hijo** (envía datos a su `stdin`).

---

## 📊 Resumen Comparativo de Problemas

| Caso | Método original | Síntoma / Problema | Causa técnica | Solución aplicada |
|---|---|---|---|---|
| **1** | `saludar(String)` | Vulnerabilidad crítica e incompatibilidad en Windows | Concatenación de entrada en shell (`sh -c`) | Argumentos separados en `ProcessBuilder`, validación de entrada con regex y detección de SO |
| **2** | `contarNumeros()` | El programa se cuelga indefinidamente | Deadlock: `waitFor()` antes de vaciar `getInputStream()` | Lectura concurrente con hilos (`ExecutorService`) antes de esperar el fin del proceso |
| **3** | `ordenar()` | Cuelgue indefinido leyendo la salida de `sort` | `getOutputStream()` nunca se cierra; el hijo no recibe `EOF` | Cierre explícito de `p.getOutputStream()` tras escribir |
| **4** | `mostrar(String)` | Falsos positivos de éxito, path traversal y cuelgues | No se lee `getErrorStream()`, se ignora `exitValue()`, sin timeout ni validación de rutas | `Path.normalize()` contra raíz, captura de `stderr`, comprobación de `exitValue() == 0` y timeout de seguridad |

---

## 🔍 Análisis Detallado Caso por Caso

### Caso 1: Saludo con Inyección de Comandos (`saludar`)

#### ❌ Código Original
```java
static void saludar(String nombre) throws Exception {
    String comando = "echo Hola " + nombre;
    Process p = new ProcessBuilder("sh", "-c", comando).start();
    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
    String linea;
    while ((linea = br.readLine()) != null) System.out.println(linea);
}
```

#### 🚨 Por qué fallaba
1. **Inyección de Comandos (CWE-78)**: Al usar `sh -c` y concatenar una cadena recibida de fuera, un atacante o usuario puede pasar `"Carlos; rm -rf /"` o `"Carlos & calc.exe"`, ejecutando comandos arbitrarios.
2. **Incompatibilidad de Sistema Operativo**: En Windows no existe el binario `sh` de forma nativa, provocando `java.io.IOException: Cannot run program "sh"`.

####  Solución en [`CodigoSeguro.java`](file:///c:/Users/ian6m/Desktop/Skills/src/CodigoSeguro.java)
* Validación de caracteres permitidos (solo alfanuméricos y separadores seguros).
* Comando multiplataforma (`cmd.exe /c echo` en Windows, `echo` directo en Linux).
* Separación de argumentos en `List<String>`, evitando que una shell interprete operadores como `;`, `&&` o `|`.

---

### Caso 2: Bloqueo por Buffer Lleno (`contarNumeros`)

#### ❌ Código Original
```java
static void contarNumeros() throws Exception {
    Process p = new ProcessBuilder("seq", "1", "1000000").start();
    p.waitFor(); //  DEADLOCK AQUÍ
    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
    int n = 0;
    while (br.readLine() != null) n++;
    System.out.println("Números generados: " + n);
}
```

#### 🚨 Por qué fallaba
1. **Deadlock por buffer del pipe lleno**: Los procesos comunican su salida al proceso padre mediante un búfer de tamaño finito (típicamente 4 KB a 64 KB según el SO). Cuando el hijo genera más datos de los que caben en el buffer y nadie los lee, el sistema operativo suspende al hijo hasta que haya espacio.
2. Como el hilo de Java se queda esperando en `p.waitFor()` a que el hijo termine, y el hijo está suspendido esperando a que Java lea el buffer, **ambos se quedan bloqueados mutuamente para siempre**.
3. En Windows, además, el comando `seq` no existe por defecto.

####  Solución en [`CodigoSeguro.java`](file:///c:/Users/ian6m/Desktop/Skills/src/CodigoSeguro.java)
* La lectura de `p.getInputStream()` se delega inmediatamente a un hilo secundario en paralelo (`ExecutorService`).
* `p.waitFor(...)` se ejecuta mientras la lectura se encuentra activa drenando el flujo.
* Se establece un límite de caracteres (`maxChars`) para no agotar la memoria JVM.

---

### Caso 3: Interbloqueo por Entrada No Cerrada (`ordenar`)

#### ❌ Código Original
```java
static void ordenar() throws Exception {
    Process p = new ProcessBuilder("sort").start();
    p.getOutputStream().write("pera\nmanzana\nbanana\n".getBytes());
    p.getOutputStream().flush();
    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
    String linea;
    while ((linea = br.readLine()) != null) System.out.println(linea); //  CUELGUE AQUÍ
}
```

#### 🚨 Por qué fallaba
1. **Falta de señal EOF (End Of File)**: La herramienta `sort` necesita leer **toda** la entrada hasta el final antes de poder calcular el orden de las líneas y emitir la primera línea ordenada.
2. Aunque se hizo `flush()`, el descriptor de archivo de `stdin` seguía abierto. Por tanto, `sort` seguía esperando más palabras y no emitía nada a `stdout`.
3. El bucle `br.readLine()` se quedaba esperando indefinidamente datos que nunca llegarían.

####  Solución en [`CodigoSeguro.java`](file:///c:/Users/ian6m/Desktop/Skills/src/CodigoSeguro.java)
* Cierre explícito del flujo mediante `try (OutputStream os = p.getOutputStream()) { ... }`.
* Al salir del bloque `try`, Java cierra el stream, enviando el carácter `EOF` al proceso hijo, permitiendo que `sort` procese los datos y termine.

---

### Caso 4: Control de Errores y Seguridad de Rutas (`mostrar`)

#### ❌ Código Original
```java
static void mostrar(String fichero) throws Exception {
    Process p = new ProcessBuilder("cat", fichero).start();
    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
    String linea;
    while ((linea = br.readLine()) != null) System.out.println(linea);
    p.waitFor();
    System.out.println("Fichero mostrado correctamente");
}
```

#### 🚨 Por qué fallaba
1. **Silenciamiento de Errores**: Si `fichero` no existe o no tiene permisos, el mensaje de error del sistema se emite por `stderr` (`p.getErrorStream()`), el cual nunca se lee.
2. **Falsos Positivos**: El código ignora `p.exitValue()`. Aunque el comando falle con código `1`, el método siempre imprime `"Fichero mostrado correctamente"`.
3. **Path Traversal**: Aceptar cualquier ruta sin validar permite acceder a archivos sensibles del sistema (ej. `../../../../etc/passwd` o `..\..\Windows\win.ini`).
4. **Ausencia de Timeout**: Si el comando se detiene o pide confirmación, el proceso se queda colgado sin límite de tiempo.
5. `cat` no existe por defecto en el intérprete nativo de Windows.

####  Solución en [`CodigoSeguro.java`](file:///c:/Users/ian6m/Desktop/Skills/src/CodigoSeguro.java)
* **Validación de ruta**: Se usa `Path.normalize()` y se verifica `destino.startsWith(baseDirectory)` para impedir salidas del árbol permitido.
* **Captura concurrente de `stderr`**: Se lee `getErrorStream()` en un hilo paralelo.
* **Evaluación de `p.exitValue()`**: Si el código es distinto de `0`, se muestra el mensaje de error de `stderr` y se notifica el fallo.
* **Comando según SO**: Detección automática (`type` en Windows, `cat` en Linux).
* **Timeout con escalado**: Si transcurren más de 5 segundos, se invoca `destroy()` y progresivamente `destroyForcibly()`.

---

## 🛡️ Medidas Globales de Seguridad Incorporadas

1. **Destrucción de procesos huérfanos**: Bloque `finally` que garantiza que si el proceso sigue vivo tras el timeout o una excepción, se ejecuta `p.destroyForcibly()`.
2. **Cierre de stdin no utilizado**: Cuando un proceso no requiere entrada del usuario, se llama inmediatamente a `p.getOutputStream().close()`.
3. **Codificación explícita**: Especificación de `StandardCharsets.UTF_8` en todos los `InputStreamReader` para evitar caracteres corruptos entre distintos sistemas operativos.
