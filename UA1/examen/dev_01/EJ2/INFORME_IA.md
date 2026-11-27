# INFORME IA

## 1. Instrucciones a la IA

Lo primero que he hecho es pasarle el enunciado del examen a mi inteligencia artificial (ChatGPT). Le he pedido que haga el código siguiendo las reglas de la skill, que fuese un código sencillo y que utilizase cosas que hemos visto en clase. También le he pedido que comente las partes más importantes con palabras básicas.

## 2. Mejoras del código

Una de las cosas que he mejorado ha sido la documentación del código, ya que al principio había cosas que no estaban comentadas. Por ejemplo, le he pedido a la IA que me comente por qué utilizamos extends Thread, para dejar claro que cada operario es un hilo.

También he revisado el código para entender lo que hace cada clase.

## 3. Uso de la skill

Una de las reglas que he puesto en mi skill es utilizar start() para iniciar los hilos y join() para esperar a que terminen.

Esto lo tenemos en la clase SistemaAlmacen, donde tenemos dos bucles for, el primero para iniciar todos los hilos y el segundo para esperar a que terminen todos.

En este ejercicio no se usa  synchronized, ya que cada operario tiene su propio contador de pedidos y no lo comparte con los demás.

## 4. Comprobaciones

He ejecutado el programa cuatro veces para comprobar que funciona bien y estos han sido los resultados:

Ejecucion Pedidos Tiempo Codigo del inspector
1         150       305ms   0
2         150       306ms   0   
3         150       302ms   0   
4         150       303ms   0

En las cuatro veces que he ejecutado el programa nos da un total de 150 pedidos y el Inspector me devuelve un 0, por lo que el total de pedidos es correcto.

También he podido comprobar que los operarios trabajan a la vez, ya que el programa tarda unos 300 milisegundos y si trabajasen uno detrás de otro tardarían como mínimo 750 milisegundos.

También he ejecutado el Inspector por separado para comprobar que funcionan  los códigos de salida. He probado poniendo más pedidos de los esperados, menos pedidos y también he puesto una palabra en vez de un número para comprobar que detecta los datos que no son válidos. En las tres pruebas el codigo era el que debia devolver (se han preparado mas pedidos, se han preparado menos pedidos, y datos no validos).
He comprobado que el código es compatible con Java 17 utilizando el comando javac --release 17 y no me ha dado ningún error.

Lo ultimo, le he pasado mi archivo SKILL.md a ChatGPT y le he pedido lo siguiente:

"Revisa las tres clases Java que hemos creado siguiendo todas las reglas de mi skill hilos-procesos-java y comprueba que cumplen los requisitos del examen."

Después de la revisión no se ha encontrado ningún fallo de funcionamiento en el código. Lo único que he cambiado ha sido la descripción de la skill para explicar mejor cuándo se tiene que utilizar.