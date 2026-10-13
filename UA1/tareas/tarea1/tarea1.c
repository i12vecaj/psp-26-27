#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
    int numero;
    pid_t pid;

    // Pedimos un número al usuario.
    printf("Introduce un numero entero: ");

    // Comprobamos que la entrada sea correcta.
    if (scanf("%d", &numero) != 1) {
        fprintf(stderr, "Error: debes introducir un numero entero.\n");
        return EXIT_FAILURE;
    }

    // Creamos un proceso hijo.
    pid = fork();

    // Comprobamos si ha ocurrido un error al crear el proceso.
    if (pid < 0) {
        perror("Error al crear el proceso");
        return EXIT_FAILURE;
    }

    // Código que ejecuta el proceso hijo.
    if (pid == 0) {
        numero = numero + 4;

        printf("Proceso hijo (PID %d): numero + 4 = %d\n",
               (int)getpid(), numero);

        return EXIT_SUCCESS;
    }

    // Código que ejecuta el proceso padre.
    numero = numero - 5;

    printf("Proceso padre (PID %d): numero - 5 = %d\n",
           (int)getpid(), numero);

    // El padre espera a que termine el hijo.
    if (waitpid(pid, NULL, 0) == -1) {
        perror("Error al esperar al proceso hijo");
        return EXIT_FAILURE;
    }

    printf("Proceso padre: el proceso hijo ha terminado.\n");

    return EXIT_SUCCESS;
}