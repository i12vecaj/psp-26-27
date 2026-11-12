#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>
#include <limits.h>


int main(void) {
    int numero;
    pid_t pid;

    // Evita problemas de visualizacion del texto antes de fork().
    setbuf(stdout, NULL);

    // FR2: pedir una variable al usuario.
    printf("Introduce un numero entero: ");

    if (scanf("%d", &numero) != 1) {
        fprintf(stderr, "Error: debes introducir un numero entero.\n");
        return EXIT_FAILURE;
    }

    // FR1: crear un proceso hijo.
    pid = fork();

    // Controlar si la creacion del proceso ha fallado.
    if (pid == -1) {
        perror("Error al crear el proceso hijo");
        return EXIT_FAILURE;
    }

    // FR4: codigo ejecutado por el proceso hijo.
    if (pid == 0) {
        printf("\n[HIJO]\n");
        printf("PID del hijo: %ld\n", (long)getpid());
        printf("Valor inicial: %d\n", numero);

        // Controlar un posible desbordamiento al sumar 4.
        if (numero > INT_MAX - 4) {
            fprintf(stderr, "[HIJO] Error: desbordamiento al sumar 4.\n");
            _exit(EXIT_FAILURE);
        }

        numero = numero + 4;

        // FR5: mostrar los resultados del hijo.
        printf("Operacion: %d + 4 = %d\n", numero - 4, numero);

        _exit(EXIT_SUCCESS);
    }

    // FR3: codigo ejecutado por el proceso padre.
    printf("\n[PADRE]\n");
    printf("PID del padre: %ld\n", (long)getpid());
    printf("PID del hijo: %ld\n", (long)pid);
    printf("Valor inicial: %d\n", numero);

    // Controlar un posible desbordamiento al restar 5.
    if (numero < INT_MIN + 5) {
        fprintf(stderr, "[PADRE] Error: desbordamiento al restar 5.\n");

        // Esperar al hijo antes de terminar.
        if (waitpid(pid, NULL, 0) == -1) {
            perror("Error al esperar al hijo");
        }

        return EXIT_FAILURE;
    }

    numero = numero - 5;

    // FR5: mostrar los resultados del padre.
    printf("Operacion: %d - 5 = %d\n", numero + 5, numero);

    // Esperar a que el proceso hijo termine.
    int estado;
    if (waitpid(pid, &estado, 0) == -1) {
        perror("Error al esperar al proceso hijo");
        return EXIT_FAILURE;
    }

    // Comprobar si el hijo ha terminado correctamente.
    if (WIFEXITED(estado)) {
        if (WEXITSTATUS(estado) != EXIT_SUCCESS) {
            fprintf(stderr, "El proceso hijo ha terminado con errores.\n");
            return EXIT_FAILURE;
        }
    } else {
        fprintf(stderr, "El proceso hijo termino de forma anormal.\n");
        return EXIT_FAILURE;
    }

    printf("\n[PADRE] El proceso hijo ha terminado correctamente.\n");

    return EXIT_SUCCESS;
}

