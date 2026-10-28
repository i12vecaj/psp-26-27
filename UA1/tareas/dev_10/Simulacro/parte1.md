# Parte 1 · Calentamiento (10 min)
## Escribe V o F:

1. `start()` ejecuta la tarea en el hilo `main`.
    - Falso. Eso lo hace `run()`. `start()` crea un hilo nuevo.
2. Programación concurrente y programación paralela son lo mismo.
    - Falso. Programacion Concurrente varias tareas trabajan en el mismo periodo, el sistema alterna entre ellas pero no 
             pero no a la vez, mientras que paralelas signica que se ejecutan al mismo tiempo por ejemplo en distintos 
             nucleos.
3. Dos hilos del mismo proceso comparten memoria.
    - Verdadero. Los hilos del mismo proceso comparten memoria y recursos del procesador aunque cada hilo tiene su 
                 propia pila y estado de hilo.
4. `getInputStream()` de un `Process` es lo que el hijo recibe.
    - Falso. Para saber lo que recibe es `getOutputStream()`, con `getInputStream` lees lo que el hijo escribe en su 
             salida.
5. `waitFor()` bloquea al padre hasta que el hijo termina.
    - Verdadero. Normalmente, `waitFor()` bloquea el hilo hasta que el hijo termina y devuelve su codigo de salida.
6. Crear un hilo es más costoso que crear un proceso.
    - Falso. Crear un hilo suele ser menos costoso porque solo utiliza memoria y recursos del procesador, y un proceso
             suele necesitar su propio espacio en la memoria 
7. Tres hilos de 10, 4 y 5 segundos lanzados con `start()` tardan 19 segundos en total.
    - Falso. tardan 10 segundo si se ejecutan concurretemente 
8. Internet es un ejemplo típico de programación distribuida.
    - Verdadero
9. Por convenio, un código de salida distinto de 0 indica error.
    - Verdadero. Normalmente el codigo 0 significa que ha terminado correctamente
10. Un mismo objeto `Thread` se puede arrancar dos veces con `start()`.
    - Falso. un objeto `Thread` solo se puede llamar una vez con `start()`.

# Parte 2 · Teoría
## Responde cada pregunta con tus palabras, en unas 5 líneas, **sin apuntes** (7 minutos por pregunta).

1. ¿Qué diferencias hay entre un **proceso** y un **hilo**, y cómo se relacionan?


Un proceso es un programa en ejecucion. Tiene su propio espacio de memoria y recursos. Un hilo es una secuencia de
ejecucion dentro de un proceso. Un proceso puede tener varios hilos, pero cada hilo tiene su propio estados de ejecucion y pila.


2. ¿Qué es la **programación concurrente** y qué problemas plantea (exclusión mutua y condición de sincronización)? 
    Pon un ejemplo de cada uno.


   La programación concurrente permite que varias tareas avancen durante el mismo periodo. Pueden alternarse en un solo 
       núcleo o ejecutarse al mismo tiempo en varios núcleos. 
   - **Exclusión mutua** dos tareas acceden a la vez a un recurso compartido y una operacion interfiere con la otra.
   - **Condicion de sincronización** Una tarea debe esperar a que otra cumpla la condicion antes de continuar.


3. ¿Qué diferencias hay entre **programación paralela** y **programación distribuida**? Ventajas e inconvenientes de cada una.


|                 | Paralela           |distribuida |
|-----------------|-------------       |-------------|
| Donde se ejecuta| En varios nucleos o procesos que trabajan conjuntamente, normalmente en un mismo equipo| En varios ordenadores independientes conectados por una red
| Como se ejecuta | A menudo en memoria compartida  | Mediante el intercambio por mensaje de red |
| Ventajas        | Puede acelerar cálculos que se dividen en partes; la comunicación entre tareas suele ser rápida. | Permite repartir trabajo y recursos entre máquinas; puede crecer añadiendo equipos. |
| Desventajas     | No todos los problemas se pueden dividir bien; coordinar tareas y acceder a memoria compartida puede causar conflictos. | La red añade retrasos y puede fallar; coordinar máquinas y mantener los datos consistentes es más complejo. |