#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


int main(void) {
    int numero = 0;
    pid_t pid;

 
    printf("=========================================\n");
    printf("   PROGRAMA DE PROCESOS EN C (fork)      \n");
    printf("=========================================\n");
    printf("Introduce un numero entero: ");

    if (scanf("%d", &numero) != 1) {
        fprintf(stderr, "Error: Debe introducir un valor numerico entero valido.\n");
        return EXIT_FAILURE;
    }

    printf("\n[Inicio] Valor original introducido: %d (PID del proceso inicial: %d)\n\n", 
           numero, getpid());

    pid = fork();
    if (pid < 0) {
        perror("Error al ejecutar fork()");
        return EXIT_FAILURE;
    }


    if (pid == 0) {
        pid_t mi_pid = getpid();
        pid_t pid_padre = getppid();

        printf("[HIJO]  -> Iniciado (Mi PID: %d, PID de mi Padre: %d)\n", mi_pid, pid_padre);
        printf("[HIJO]  -> Valor recibido antes de operar: %d\n", numero);

        numero += 4;

        printf("[HIJO]  -> Resultado tras SUMAR 4: %d\n", numero);
        printf("[HIJO]  -> Finalizando proceso hijo.\n\n");
        exit(EXIT_SUCCESS);
    } 
    
    else {
        pid_t mi_pid = getpid();

        printf("[PADRE] -> En ejecucion (Mi PID: %d, PID de mi Hijo: %d)\n", mi_pid, pid);
        printf("[PADRE] -> Valor antes de operar: %d\n", numero);

        numero -= 5;

        printf("[PADRE] -> Resultado tras RESTAR 5: %d\n", numero);
        int status;
        pid_t pid_finalizado = wait(&status);

        if (pid_finalizado < 0) {
            perror("Error en wait()");
        } else {
            if (WIFEXITED(status)) {
                printf("[PADRE] -> Proceso hijo (PID %d) finalizo correctamente con codigo de salida %d.\n",
                       pid_finalizado, WEXITSTATUS(status));
            } else {
                printf("[PADRE] -> Proceso hijo termino de manera anormal.\n");
            }
        }

        printf("[PADRE] -> Finalizando proceso padre.\n");
    }

    return EXIT_SUCCESS;
}
