Texto dado:
Necesito que diseñes una aplicación con los siguientes requisitos:
Java 17, sin librerias externas, sin paquete.
Una clase principal llamada SistemaVentas con un proceso independiente "Auditor", cada uno
debe tener su main.
Las distintas taquillas deben ser hilos con sus propios datos independientes, no comparten 
variables entre ellos y el programa debe leer solo el resultado de cada taquilla al acabar.
SistemaVentas debe escribir exactamente en este mismo formato:
-Linea por taquilla "Taquilla N: V entradas"
- Total vendido
- Tiempo de ventas: Xms
- La salida de Auditor tal como la escribe.
- Codigo del auditor
-Auditor recibe dos líneas, primero el aforo y luego el total vendido y devuelve su veredicto de la siguiente forma:
0: Total coincide con aforo
1: total mayor que aforo
2: total menor que aforo
3: datos no validos.

Como posible fallo o mejora alomejor pondría un tiempo de espera a la ejecución de los distintos hilos teniendo 
en cuenta que en la práctica podría existir el factor humano, algún error, o algúna posibilidad de interrumpir
procesos con cierto margen de error para poder tener ese control de contingencias en caso de que fuese necesario
siempre y cuando fuese algo que el supuesto cliente quisiese implementar.

Creo que el la skill ha influido sobre todo en las comprobaciones finales a la hora de que el programa cumpla
las funciones que hemos especificado al final, además de que haya tomado un formato correspondiente al que le 
hemos pedido para que sea más accesible y más comprensible, sin la skill seguramente hubiese tomado otro camino
que se hubiese alejado de lo que queríamos.
Hemos comprobado dividiendo el aforo equitativamente entre las taquillas para este ejemplo, sin embargo en un
caso práctico en el que no hubiesemos dividido las taquillas equitativamente no debería dar error.