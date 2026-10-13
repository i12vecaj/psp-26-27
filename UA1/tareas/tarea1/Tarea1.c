#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>  
#include <sys/types.h>

int main() {

    int numero;

    printf("Introduce un número entero: ");
    if (scanf("%d", &numero) != 1) {   // control de errores
        fprintf(stderr, "Error: No se introdujo un número válido.\n");
        exit(EXIT_FAILURE);
    }

    pid_t pid = fork();   // creación del proceso

    if (pid < 0) {
        // Error al crear el proceso
        perror("Error al crear el proceso con fork()");
        exit(EXIT_FAILURE);
    }
    else if (pid == 0) {
        // PROCESO HIJO
        int resultadoHijo = numero + 4;   // FR4
        printf("\n[HIJO] PID: %d\n", getpid());
        printf("[HIJO] Número original: %d\n", numero);
        printf("[HIJO] Número + 4: %d\n\n", resultadoHijo);
    }
    else {
        // PROCESO PADRE
        int resultadoPadre = numero - 5;   // FR3
        printf("\n[PADRE] PID: %d\n", getpid());
        printf("[PADRE] Número original: %d\n", numero);
        printf("[PADRE] Número - 5: %d\n\n", resultadoPadre);
    }

    return 0;
}
