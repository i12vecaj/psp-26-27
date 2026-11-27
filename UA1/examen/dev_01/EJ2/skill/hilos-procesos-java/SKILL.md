
---
name: hilos-procesos-java
description: Ayuda a crear y revisar programas de procesos e hilos en Java 17, utilizando los conceptos vistos en clase.
---

# Reglas de procesos

1. Utilizar ProcessBuilder para crear procesos independientes.
2. Utilizar la entrada y salida estándar para comunicarse con los procesos.
3. Utilizar waitFor() para esperar a que termine un proceso y obtener su código de salida.

# Reglas de hilos y concurrencia

4. Utilizar Thread o Runnable para crear los hilos.
5. Utilizar start() en lugar de run() para iniciar los hilos.
6. Utilizar join() para esperar a que terminen los hilos antes de leer sus resultados.
7. Evitar compartir variables modificables entre hilos si no es necesario.
8. Utilizar synchronized cuando varios hilos modifican un mismo recurso compartido.
9. Controlar InterruptedException cuando se utiliza sleep() o join().

# Reglas de calidad

10. Utilizar código sencillo, nombres de variables claros y comentarios que expliquen el funcionamiento.
11. Utilizar Java 17, sin paquetes ni librerías externas para esta actividad.

# Lista de comprobación final

- [ ] Los hilos se inician con start().
- [ ] Los hilos trabajan de forma concurrente.
- [ ] Se espera a todos los hilos mediante join().
- [ ] Cada hilo trabaja con sus propios datos.
- [ ] Se utiliza ProcessBuilder para lanzar el proceso.
- [ ] Se controla la entrada y salida del proceso.
- [ ] Se obtiene el código de salida.
- [ ] Se controlan las excepciones.
- [ ] El programa compila y funciona correctamente.
