### Reto 1 · Leer la salida de un proceso (8 min)
**Enunciado:** lanza `java -version` desde Java y muestra lo que imprime, junto con su código de salida.
**Predice:** ¿lo leerás con `getInputStream()`? Diría que si, en principio el proceso debería mostrar el texto con getInputStream.

**Ejecución**:
A la hora de ejecutarlo no hemos podido ver nada en el código de salida 
A la hora de ejecutarlo y no recibir ningún mensaje con GetInputStream he seguido la pista de getErrorStream(), he optado que si no podemos ver por getInput, puede que
tuviese que verse como mensaje de Error, por lo que siguiendo los apuntes he redirijido los mensajes de error para ver que recibíamos, viendo que se ha dado el resultado esperado del comando

- **Ampliación:** lanza un comando que no exista y captura la excepción. ¿Qué mensaje da?
Nos da un mensaje de Unrecognized option, tal como nos lo daría por consola con un código de salida 1 a diferencia de en el proceso anterior que nos dá un código de salida 0
