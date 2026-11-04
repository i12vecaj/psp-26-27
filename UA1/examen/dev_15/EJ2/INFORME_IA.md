# Informe de uso de IA

Andres, 04/11/2026. Agente: Claude Code.

## Texto que le di a la IA

Lo preparé con ayuda de la IA y lo cambié antes de mandarlo:

```
vamos a hacer la actividad 2 del examen, usa la skill hilos-procesos-java en C:\Users\BOSS\.claude\skills\hilos-procesos-java\SKILL.md y
siguela paso a paso incluida la lista de comprobacion del final

 tienes que crear SistemaVentas.java y Auditor.java en
C:\Users\BOSS\Desktop\psp-26-27\UA1\examen\dev_15\EJ2\src
y copiame la skill a EJ2\skill\hilos-procesos-java\SKILL.md

el programa es un teatro con 200 entradas y 5 taquillas con cupos de 20, 30, 40,
50 y 60. venden todas a la vez y cada una solo lo suyo, cada venta tarda 5 ms con
Thread.sleep(5). cuando acaban todas se enseñan los resultados y se lanza el
Auditor como proceso aparte, pasandole el aforo y el total

ojo con esto que no quiero liarla:
- java 17, nada de librerias ni de paquete
- las taquillas son hilos con sus propios datos, nada de contador compartido ni
  atomic ni cosas raras. el main lee lo de cada taquilla solo cuando ya ha terminado
- la salida tiene que ser exactamente esta y en este orden, no me metas titulos ni
  mensajes de mas:
    Taquilla N: V entradas   (N de 1 a 5)
    Total vendido: T de 200
    Tiempo de ventas: X ms   (desde que se lanzan hasta que acaban todas)
    lo que escriba el Auditor, tal cual
    Codigo del auditor: C
  y "Codigo" va sin tilde
- al Auditor le llegan dos lineas por la entrada estandar, primero el aforo y luego
  el total, NO por argumentos. escribe una sola linea y sale con 0 si coincide, 1 si
  es mayor, 2 si es menor y 3 si los datos no valen

con comentarios cortos y nada de cosas que no se hayan pedido

antes de decirme que esta bien compruebalo, aunque yo lo revisare despues de ti:
- compila con javac --release 17 pero fuera de src, que no quiero .class en la entrega
- ejecutalo minimo 3 veces y enseñame lo que sale. el total tiene que dar 200 y el
  tiempo parecerse a 300 ms, no a 1000
- prueba los 4 codigos del auditor por separado, tambien con datos malos
- si algo no cumple o lo has tenido que arreglar me lo dices, no me lo escondas

y no hagas commit ni push, eso lo hago yo cuando lo revise despues de ti
```

## Mejora que corregí

Funcionaba a la primera, pero al pedirle a la IA que revisara todo salió una mejora en el Auditor: si faltaba una línea, capturaba una `NullPointerException` para salir con código 3. Lo cambié por un `if` que comprueba si la línea es `null` antes de convertirla, quité un `trim()` que no se había pedido y volví a probar los casos.

## Cómo influyó una regla de la skill

Ni el enunciado ni mi texto hablan de la salida de error del hijo. Por la regla P4 el código tiene `pb.redirectError(ProcessBuilder.Redirect.INHERIT)`, así que si el Auditor falla el error no se pierde.

## Cómo comprobé que venden a la vez y que el total es correcto

El agente lo ejecutó tres veces con Java 17: total 200 en todas y 339, 345 y 335 ms, cerca de los 300 ms de la taquilla más lenta y lejos de los 1000 ms de la suma. En el código hay un bucle de `start()` y otro de `join()`, y los resultados se leen después. El Auditor se probó con los códigos 0, 1, 2 y 3, y el 3 con letras, líneas que faltan y un negativo.
