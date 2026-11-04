# name: hilos-procesos-java
# description: Skill para validar y generar ejercicios de Java que combinan procesos independientes y múltiples hilos concurrentes. Verifica el cumplimiento estricto de aislamiento de datos en hilos, uso de ProcessBuilder, simulación de tiempos y salidas por consola con formato específico.

## Reglas de la Skill

### Procesos (Mínimo 3 reglas)
1. **Lanzamiento Aislado:** El proceso independiente (`Auditor`) debe ser invocado obligatoriamente desde el programa principal (`SistemaVentas`) usando `ProcessBuilder` (o `Runtime.getRuntime().exec()`), apuntando al comando `java Auditor`.
2. **Comunicación por E/S Estándar:** El proceso principal debe enviar los datos requeridos (aforo y total vendido) escribiendo en la entrada estándar del subproceso (el `OutputStream` del proceso hijo).
3. **Sincronización y Códigos de Salida:** El hilo principal de `SistemaVentas` debe esperar a que el proceso `Auditor` termine usando el método `waitFor()` y recuperar su código de salida (`exitValue()`) para mostrarlo al final.

### Hilos y Concurrencia (Mínimo 4 reglas)
4. **Independencia de Estado:** Cada hilo (taquilla) debe poseer sus propias variables de instancia. Queda terminantemente prohibido usar variables compartidas o estáticas para acumular ventas.
5. **Simulación de Trabajo:** La acción de vender cada entrada debe simularse individualmente aplicando un retardo con `Thread.sleep(5)` por entrada.
6. **Barrera de Sincronización:** El programa principal debe lanzar todos los hilos simultáneamente y luego invocar `join()` sobre cada uno de ellos para asegurar que todas las ventas han concluido antes de continuar.
7. **Lectura Segura a Posteriori:** El cálculo del total vendido y del tiempo total empleado (midiendo desde antes de iniciar los hilos hasta después de todos los `join()`) solo debe realizarse una vez que todos los hilos hayan finalizado su ejecución.

### Calidad técnica (Mínimo 1 regla)
8. **Restricciones del Entorno:** El código debe ser Java 17 puro, sin librerías de terceros (solo Java SE). Las clases no deben estar en ningún paquete (no usar `package`). `SistemaVentas` y `Auditor` deben estar separados y las impresiones por consola deben coincidir letra por letra con el formato exigido.

---

## Lista de comprobación final

Antes de dar la solución por válida, verifica:

- [ ] ¿El proyecto utiliza Java 17 nativo y sin declaraciones de `package`?
- [ ] ¿Las clases `SistemaVentas` y `Auditor` tienen cada una su propio `public static void main(String[] args)`?
- [ ] ¿Los hilos de las 5 taquillas trabajan con datos puramente locales/de instancia (sin `static` mutables)?
- [ ] ¿Se usa `Thread.sleep(5)` *por cada entrada* vendida dentro del bucle de cada taquilla?
- [ ] ¿El `SistemaVentas` espera a todos los hilos con `join()`?
- [ ] ¿Se calcula y muestra el tiempo total transcurrido desde el inicio al fin de los hilos (`Tiempo de ventas: X ms`)?
- [ ] ¿La salida de las taquillas tiene el formato exacto `Taquilla N: V entradas` seguido de `Total vendido: T de 200`?
- [ ] ¿Se lanza `Auditor` como proceso externo, pasándole el aforo y el total por su entrada estándar?
- [ ] ¿El `Auditor` retorna un código de salida `0`, `1`, `2` o `3` con `System.exit()`?
- [ ] ¿El `SistemaVentas` lee e imprime la respuesta textual del Auditor y su código en el formato `Codigo del auditor: C`?