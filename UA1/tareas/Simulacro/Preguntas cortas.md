## Parte 2 · Teoría (30 min)

Responde cada pregunta con tus palabras, en unas 5 líneas, **sin apuntes** (7 minutos por pregunta).

1. ¿Qué diferencias hay entre un **proceso** y un **hilo**, y cómo se relacionan?
Los procesos tienen dos formas de interactuar entre sí, o compiten o se sincronizan con respecto a tema de memoria, ya que los procesos no se pueden distribuir
la memoria entre ellos a diferencia de los hilos que podríamos decir "colaboran" entre ellos dentro de un mismo proceso mientras que los procesos pueden llegar a pisarse entre sí.

2. ¿Qué es la **programación concurrente** y qué problemas plantea (exclusión mutua y condición de sincronización)? Pon un ejemplo de cada uno.
Como comentabamos en la pregunta anterior, los procesos pueden llegar a pisarse entre sí y no actúan en sintonía por sí solos, tenemos que dedicar un esfuerzo 
activo en coordinar distintos procesos en función de si necesitamos o no que se vayan acompasando entre ellos, por ejemplo, si necesitan acceder y modificar información
no pueden hacerlo a la vez.

3. ¿Qué diferencias hay entre **programación paralela** y **programación distribuida**? Ventajas e inconvenientes de cada una.
En la programación paralela hablamos de que hay varias CPUs actuando a la vez en un sistema, actuando a la vez sobre el mismo problema

Ventajas: Se pueden resolver problemas mas complejos de manera simultánea

Contras: Requieren de mayor complejidad y consumo.

Sin embargo en la programación distribuida se encuentran de manera distribuida como el nombre indica, conectadas mediante red

Ventajas: Tienen una flexibilidad para su escalado mucho mayor y resultan más disponibles.

Contras: Si la red falla, falla todo.
---