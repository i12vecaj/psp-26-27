Primero le indico el siguiente prompt:

Primero te pedire que me generes una SKILL.md con los siguientes requisitos que te indico:

1. Cabecera con name y description (qué hace y cuándo debe usarse).
2. Mínimo 8 reglas verificables basadas en lo visto en clase: al menos 3 de procesos, 4 de hilos y concurrencia y 1 de calidad.
3. Una lista de comprobación final.
4. Puedes pedir ayuda a la IA para redactarla, pero las reglas deben ser correctas y estar adaptadas a lo que hemos trabajado.
5. Colócala en la carpeta de skills de tu agente para que la use y copia el fichero a la carpeta de entrega.

Para que puedas generar un skill correspondiente al programa que vamos a generar te facilito los requisitos que debe de tener el sistema que vamos a implementar:

1. Java 17, sin librerías externas y sin paquete (paquete por defecto).
2. La clase principal se llama SistemaVentas y el proceso independiente es la clase Auditor (cada una con su main).
3. Las taquillas son hilos que trabajan con sus propios datos: no se comparten variables modificables entre hilos. El programa principal solo lee el resultado de cada taquilla cuando esta ha terminado.
4. SistemaVentas debe escribir exactamente estas líneas, en este orden:
    - Una línea por taquilla: Taquilla N: V entradas (N de 1 a 5).
    - Total vendido: T de 200
    - Tiempo de ventas: X ms (desde que se lanzan las taquillas hasta que han terminado todas).
    - La salida del Auditor, tal como la escribe.
    - Codigo del auditor: C
5. Auditor recibe por su entrada estándar dos líneas (primero el aforo y luego el total vendido), escribe una línea con su veredicto y termina con estos códigos de salida:
    - 0: el total coincide con el aforo.
    - 1: el total es mayor que el aforo.
    - 2: el total es menor que el aforo.
    - 3: los datos no son válidos.

Ademas de todo esto tienes que tener en cuenta que en clase hemos visto hilos y procesos, usos 
del waitFor, slep, diferenciancion entre runnable y Threads, empezar el uso de hilos con .start...

-- Como pega le podria indicar que hay mas conceptos dados o vistos en clase, y que podrian añadirse mas validaciones. 

En siguiente instancia vamos a pedir este segundo prompt:

En base a esta skill, genera el código con las especificaciones que he indicado anteriormente, 
ambas clases con sus respectivos atributos, métodos, validaciones internas, excepciones de errores, 
y cualquier metodología valida en java que se te pueda ocurrir y sea falta a la mia.

Vamos a obtener los codigos literales con las clases java que nos dan (Codigo literal copypasteado)

Comprobamos los resultados ejecutandolo tres veces y observamos que los datos son correctos y las salidas 
son correspondientes a lo que esta pidiendo. 

Luego tengo en cuenta que ha seguido la estructura de uso, por ejemplo ha utilizado
.start para empezar los procesos, y podria haber usado .run ya que la IA no difiere de estos conceptos. 

Como fallo o mejora, veo que no ha comentado el codigo, agrego los comentarios y listo. 