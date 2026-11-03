

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <limits.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(void)
{
    char linea[64];
    char *fin;
    long valor;
    int variable;
    int estado;

    /* FR2: pedir la variable. Se repite hasta que el numero sea valido. */
    while (1) {
        printf("Introduce un numero entero: ");
        fflush(stdout);

        /* Error de lectura o fin de entrada (Ctrl+D): no se puede seguir. */
        if (fgets(linea, sizeof(linea), stdin) == NULL) {
            fprintf(stderr, "Error: no se ha podido leer la entrada.\n");
            return EXIT_FAILURE;
        }

        errno = 0;
        valor = strtol(linea, &fin, 10);

        /* Debe haber al menos un digito y nada mas que el salto de linea. */
        if (fin == linea || (*fin != '\n' && *fin != '\0')) {
            fprintf(stderr, "Error: eso no es un numero entero.\n");
            continue;
        }
        /* El numero debe caber en un int, tambien tras restar 5 o sumar 4. */
        if (errno == ERANGE || valor < (long)INT_MIN + 5 || valor > (long)INT_MAX - 4) {
            fprintf(stderr, "Error: numero fuera de rango.\n");
            continue;
        }
        variable = (int)valor;
        break;
    }

    printf("Valor inicial: %d\n", variable);

    /* Vaciar el buffer antes de fork(); si no, el hijo heredaria
     * el texto pendiente y se imprimiria dos veces. */
    fflush(stdout);

    /* FR1: crear el proceso hijo.
     * fork() devuelve -1 si falla, 0 en el hijo y el PID del hijo en el padre. */
    pid_t pid = fork();

    if (pid < 0) {
        perror("Error en fork()");
        return EXIT_FAILURE;
    }

    if (pid == 0) {
        /* FR4 + FR5: proceso HIJO, suma 4 a su copia de la variable. */
        variable += 4;
        printf("[HIJO]  PID %d: %d + 4 = %d\n", (int)getpid(), (int)valor, variable);
        return EXIT_SUCCESS;
    }

    /* FR3 + FR5: proceso PADRE, resta 5 a su copia de la variable. */
    variable -= 5;
    printf("[PADRE] PID %d: %d - 5 = %d\n", (int)getpid(), (int)valor, variable);

    /* Esperar al hijo para que no quede zombie y comprobar como termino. */
    if (wait(&estado) == -1) {
        perror("Error en wait()");
        return EXIT_FAILURE;
    }
    if (!WIFEXITED(estado) || WEXITSTATUS(estado) != EXIT_SUCCESS) {
        fprintf(stderr, "Error: el hijo no termino correctamente.\n");
        return EXIT_FAILURE;
    }

    printf("[PADRE] El hijo ha terminado. Mi variable sigue valiendo %d.\n", variable);
    return EXIT_SUCCESS;
}
