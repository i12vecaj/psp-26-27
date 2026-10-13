
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>
#include <limits.h>

int main() {
    int numero;
    pid_t pid;

    // FR2: Pedir un numero al usuario
    printf("Introduce un numero entero: ");
    fflush(stdout);

    // Control de errores: comprobar que se introduce un entero valido
    if (scanf("%d", &numero) != 1) {
        fprintf(stderr, "Error: debes introducir un numero entero valido.\n");
        return EXIT_FAILURE;
    }

    // FR1: Crear un proceso hijo mediante fork()
    pid = fork();

    // Control de errores: comprobar si fork() ha fallado
    if (pid < 0) {
        perror("Error al crear el proceso hijo");
        return EXIT_FAILURE;
    }

    if (pid == 0) {
        // FR4: Codigo que ejecuta el proceso hijo

        // Comprobar que la suma no provoca desbordamiento
        if (numero > INT_MAX - 4) {
            fprintf(stderr, "Error: el resultado de la suma es demasiado grande.\n");
            exit(EXIT_FAILURE);
        }

        int resultadoHijo = numero + 4;

        // FR5: Mostrar los valores del proceso hijo
        printf("\n--- PROCESO HIJO ---\n");
        printf("PID del hijo: %ld\n", (long)getpid());
        printf("Numero original: %d\n", numero);
        printf("Operacion: %d + 4 = %d\n", numero, resultadoHijo);
        fflush(stdout);

        exit(EXIT_SUCCESS);

    } else {
        // FR3: Codigo que ejecuta el proceso padre

        // Comprobar que la resta no provoca desbordamiento
        if (numero < INT_MIN + 5) {
            fprintf(stderr, "Error: el resultado de la resta es demasiado pequeno.\n");
        } else {
            int resultadoPadre = numero - 5;

            // FR5: Mostrar los valores del proceso padre
            printf("\n--- PROCESO PADRE ---\n");
            printf("PID del padre: %ld\n", (long)getpid());
            printf("PID del hijo creado: %ld\n", (long)pid);
            printf("Numero original: %d\n", numero);
            printf("Operacion: %d - 5 = %d\n", numero, resultadoPadre);
            fflush(stdout);
        }

        // Esperar a que termine el proceso hijo
        int estado;
        pid_t terminado;

        do {
            terminado = waitpid(pid, &estado, 0);
        } while (terminado == -1 && errno == EINTR);

        // Control de errores al esperar al hijo
        if (terminado == -1) {
            perror("Error al esperar al proceso hijo");
            return EXIT_FAILURE;
        }

        // Comprobar que el hijo ha finalizado correctamente
        if (!WIFEXITED(estado) || WEXITSTATUS(estado) != EXIT_SUCCESS) {
            fprintf(stderr, "Error: el proceso hijo no termino correctamente.\n");
            return EXIT_FAILURE;
        }

        if (numero < INT_MIN + 5) {
            return EXIT_FAILURE;
        }

        printf("\nLos dos procesos han finalizado correctamente.\n");
    }

    return EXIT_SUCCESS;
}
