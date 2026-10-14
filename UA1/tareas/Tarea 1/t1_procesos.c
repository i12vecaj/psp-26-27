/*
 * T1 - Programacion de procesos en C
 *
 * Pide un numero entero, crea un proceso hijo con fork() y cada proceso
 * modifica su propia copia del valor: el padre le resta 5 y el hijo le suma 4.
 *
 * Compilar:  gcc -Wall -o t1_procesos t1_procesos.c
 * Necesita Linux/WSL, porque fork() y waitpid() son de POSIX.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <limits.h>
#include <unistd.h>
#include <sys/wait.h>

#define RESTA 5
#define SUMA 4

/*
 * Lee un entero por teclado. Devuelve 0 si es valido y -1 si no.
 * Uso fgets y strtol en vez de scanf("%d") porque scanf deja la entrada
 * mal escrita en el buffer y no avisa de los desbordamientos.
 */
int pedir_entero(int *destino)
{
    char linea[64];
    char *fin;
    long valor;

    printf("Introduce un numero entero: ");
    fflush(stdout);

    if (fgets(linea, sizeof linea, stdin) == NULL) {
        printf("Error al leer la entrada.\n");
        return -1;
    }

    linea[strcspn(linea, "\n")] = 0;

    if (linea[0] == 0) {
        printf("No has escrito nada.\n");
        return -1;
    }

    errno = 0;
    valor = strtol(linea, &fin, 10);

    if (errno == ERANGE) {
        printf("El numero es demasiado grande.\n");
        return -1;
    }

    if (*fin != 0) {
        printf("\"%s\" no es un numero entero.\n", linea);
        return -1;
    }

    /* tiene que caber en un int y seguir cabiendo despues del -5 y del +4 */
    if (valor < (long) INT_MIN + RESTA || valor > (long) INT_MAX - SUMA) {
        printf("El numero esta fuera del rango permitido.\n");
        return -1;
    }

    *destino = (int) valor;
    return 0;
}

int main(void)
{
    int original, resultado, estado;
    pid_t pid;

    /* leo el valor antes del fork para que los dos procesos partan de el */
    if (pedir_entero(&original) != 0) {
        return EXIT_FAILURE;
    }

    printf("\nValor original: %d\n", original);

    /* si no vacio el buffer antes del fork, el hijo hereda una copia
       con los mensajes de arriba y se imprimen dos veces */
    fflush(stdout);

    pid = fork();

    if (pid == -1) {
        perror("Error en fork");
        return EXIT_FAILURE;
    }

    if (pid == 0) {
        /* hijo */
        resultado = original + SUMA;
        printf("Hijo (pid %d): %d + %d = %d\n", getpid(), original, SUMA, resultado);
        return EXIT_SUCCESS;
    }

    /* padre */
    resultado = original - RESTA;
    printf("Padre (pid %d): %d - %d = %d\n", getpid(), original, RESTA, resultado);

    /* espero al hijo para no dejarlo zombi */
    if (waitpid(pid, &estado, 0) == -1) {
        perror("Error en waitpid");
        return EXIT_FAILURE;
    }

    if (WIFEXITED(estado)) {
        printf("El hijo ha terminado con codigo %d.\n", WEXITSTATUS(estado));
    } else {
        printf("El hijo no ha terminado con normalidad.\n");
    }

    printf("\nResumen:\n");
    printf("  original -> %d\n", original);
    printf("  padre    -> %d\n", resultado);
    printf("  hijo     -> %d\n", original + SUMA);

    return EXIT_SUCCESS;
}

/*
 * Que esta pasando:
 *
 * fork() se llama una vez pero devuelve dos veces, una en cada proceso (0 en
 * el hijo y el pid del hijo en el padre), asi que desde esa linea hay dos
 * procesos ejecutando el mismo codigo y el if los separa.
 *
 * El hijo recibe una copia de la memoria del padre, por lo que la variable
 * resultado existe dos veces. La resta y la suma no se acumulan: con un 10 el
 * padre acaba con 5 y el hijo con 14. El hijo del resumen lo vuelvo a calcular
 * yo en el padre, porque no puedo leer la variable del otro proceso.
 *
 * El orden en que salen los mensajes depende del planificador y puede cambiar
 * de una ejecucion a otra. El waitpid solo asegura que el resumen sale cuando
 * el hijo ya ha acabado.
 */
