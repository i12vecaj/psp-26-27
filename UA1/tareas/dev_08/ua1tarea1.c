// Tarea 1 - Programación de Procesos en C
// Programa que crea un proceso hijo con fork(). El padre resta 5 a una
// variable y el hijo le suma 4.

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>
#include <string.h>

int main(void)
{
    int valor;
    pid_t pid;

    // Pedimos la variable al usuario y comprobamos que sea un entero
    printf("Introduce un número entero: ");
    if (scanf("%d", &valor) != 1) {
        fprintf(stderr, "Error: la entrada no es un número entero válido.\n");
        return EXIT_FAILURE;
    }

    printf("Valor inicial (antes del fork): %d\n", valor);
    fflush(stdout); // vaciamos el buffer para que no se imprima dos veces tras el fork

    // Creamos el proceso hijo
    pid = fork();

    if (pid < 0) {
        // fork() devuelve -1 si ha fallado la creación del proceso
        fprintf(stderr, "Error al crear el proceso hijo: %s\n", strerror(errno));
        return EXIT_FAILURE;
    }
    else if (pid == 0) {
        // Aquí entra el hijo (fork devuelve 0).
        // Modifica su propia copia de la variable, el padre no se entera.
        valor = valor + 4;
        printf("[HIJO ] PID=%d, PID del padre=%d -> valor + 4 = %d\n",
               getpid(), getppid(), valor);
        exit(EXIT_SUCCESS);
    }
    else {
        // Aquí entra el padre (fork devuelve el PID del hijo)
        valor = valor - 5;
        printf("[PADRE] PID=%d, PID del hijo=%d -> valor - 5 = %d\n",
               getpid(), pid, valor);

        // Esperamos a que termine el hijo para no dejarlo como zombi
        int estado;
        if (wait(&estado) == -1) {
            fprintf(stderr, "Error en wait(): %s\n", strerror(errno));
            return EXIT_FAILURE;
        }

        // Comprobamos cómo terminó el hijo
        if (WIFEXITED(estado)) {
            printf("[PADRE] El hijo terminó con código %d\n", WEXITSTATUS(estado));
        } else {
            printf("[PADRE] El hijo terminó de forma anormal\n");
        }

        printf("[PADRE] Valor final en el padre: %d\n", valor);
    }

    return EXIT_SUCCESS;
}