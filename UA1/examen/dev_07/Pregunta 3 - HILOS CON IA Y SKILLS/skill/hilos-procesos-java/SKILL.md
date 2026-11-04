---
name: hilos-procesos-java
description: Utilizar esta skill cuando se generen programas Java que incluyan hilos, concurrencia, sincronización o ejecución de procesos externos.
---

# Reglas verificables

## Procesos

### P1
Cada proceso debe tener su propio método main.

### P2
La comunicación entre procesos debe realizarse mediante System.in y System.out.

### P3
El proceso padre debe esperar la finalización del hijo mediante waitFor().

### P4
Los códigos de salida deben comprobarse desde el proceso padre.

## Hilos y concurrencia

### H1
Cada hilo debe ejecutar su trabajo en el método run().

### H2
Los hilos deben iniciarse mediante start().

### H3
No deben compartirse variables modificables entre hilos cuando trabajan con datos independientes.

### H4
El programa principal debe usar join() para esperar a todos los hilos.

### H5
La medición del tiempo debe comenzar antes de lanzar los hilos y terminar cuando todos hayan finalizado.

## Calidad

### C1
El código debe compilar en Java 17 sin librerías externas.

# Lista de comprobación final

- [ ] Existe una clase principal.
- [ ] Existe un proceso independiente.
- [ ] Los hilos se lanzan con start().
- [ ] Se utiliza join().
- [ ] No hay datos modificables compartidos.
- [ ] Se mide correctamente el tiempo.
- [ ] Se usa entrada/salida estándar entre procesos.
- [ ] Se comprueba el código de salida.
- [ ] Compila en Java 17.