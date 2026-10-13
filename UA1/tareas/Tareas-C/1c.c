
/*
 * Programa: Procesos padre e hijo
 *
 * Funcionalidades:
 * FR1: Crear un proceso hijo mediante fork().
 * FR2: Pedir un numero entero al usuario.
 * FR3: El proceso padre resta 5 al numero.
 * FR4: El proceso hijo suma 4 al numero.
 * FR5: Mostrar los valores y resultados por pantalla.
 *
 * Control de errores:
 * - Comprueba que el usuario introduzca un numero entero.
 * - Comprueba si falla la creacion del proceso hijo.
 * - Comprueba los limites de los enteros.
 * - Comprueba si falla la espera del proceso hijo.
 *
 * Requisitos: Linux, Unix o WSL con un compilador GCC.
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <limits.h>

int main(void) {
    int numero;
    pid_t pid;
    int estado_hijo;

    // Evitar problemas de visualizacion de la salida.
    setbuf(stdout, NULL);

    // FR2: Pedir un numero entero al usuario.
    printf("Introduce un numero entero: ");

    if (scanf("%d", &numero) != 1) {
        fprintf(stderr, "Error: debes introducir un numero entero.\n");
        return EXIT_FAILURE;
    }

    // FR1: Crear un proceso hijo.
    pid = fork();

    // Controlar si no se pudo crear el proceso hijo.
    if (pid == -1) {
        perror("Error al crear el proceso hijo");
        return EXIT_FAILURE;
    }

    // El hijo recibe 0 como resultado de fork().
    if (pid == 0) {
        int resultado_hijo;

        printf("\n--- PROCESO HIJO ---\n");
        printf("PID del hijo: %ld\n", (long)getpid());
        printf("Valor original del hijo: %d\n", numero);

        // FR4: Comprobar que sumar 4 no desborde un entero.
        if (numero > INT_MAX - 4) {
            fprintf(stderr, "Error: el hijo no puede sumar 4 sin desbordar.\n");
            return EXIT_FAILURE;
        }

        resultado_hijo = numero + 4;

        // FR5: Mostrar el resultado del hijo.
        printf("Operacion del hijo: %d + 4 = %d\n",
               numero, resultado_hijo);

        return EXIT_SUCCESS;

    } else {
        int resultado_padre;

        // El padre recibe el PID del hijo como resultado de fork().
        printf("\n--- PROCESO PADRE ---\n");
        printf("PID del padre: %ld\n", (long)getpid());
        printf("PID del hijo creado: %ld\n", (long)pid);
        printf("Valor original del padre: %d\n", numero);

        // FR3: Comprobar que restar 5 no desborde un entero.
        if (numero < INT_MIN + 5) {
            fprintf(stderr, "Error: el padre no puede restar 5 sin desbordar.\n");

            if (waitpid(pid, &estado_hijo, 0) == -1) {
                perror("Error al esperar al hijo");
            }

            return EXIT_FAILURE;
        }

        resultado_padre = numero - 5;

        // FR5: Mostrar el resultado del padre.
        printf("Operacion del padre: %d - 5 = %d\n",
               numero, resultado_padre);

        // Esperar a que termine el hijo y comprobar posibles errores.
        if (waitpid(pid, &estado_hijo, 0) == -1) {
            perror("Error al esperar al proceso hijo");
            return EXIT_FAILURE;
        }

        // Comprobar si el proceso hijo termino correctamente.
        if (!WIFEXITED(estado_hijo) ||
            WEXITSTATUS(estado_hijo) != EXIT_SUCCESS) {
            fprintf(stderr, "Error: el proceso hijo termino con problemas.\n");
            return EXIT_FAILURE;
        }

        printf("\nEl padre y el hijo han terminado correctamente.\n");
    }

    return EXIT_SUCCESS;
}
//Cuando se ejecuta fork se crean dos procesos independientes, el padre y el hijo, los dos tienen una copia de la variable original, por lo que si el usuario introduce 10, el padre resta 5 y obtiene 5, mientras que el hijo suma 4 y obtiene 14. Esto pasa porque cada proceso tiene su propia variable y los cambios de uno no afectan al otro.