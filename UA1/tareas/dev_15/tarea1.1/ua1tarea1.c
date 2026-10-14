/*
 * Tarea 1 - Programacion de procesos en C
 * Andres Martinez Cano (dev_15)
 *
 * El programa pide un numero al usuario y crea un proceso hijo con fork().
 * El proceso padre le resta 5 y el proceso hijo le suma 4.
 * Cada proceso tiene su propia copia de la variable, por eso los cambios
 * de uno no afectan al otro.
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
    int variable;
    pid_t pid;

    /* FR2: pedimos la variable al usuario */
    printf("Introduce un numero entero: ");
    if (scanf("%d", &variable) != 1) {
        /* control de errores: lo que se ha escrito no es un numero */
        printf("Error: no has introducido un numero entero.\n");
        exit(1);
    }
    printf("Valor inicial de la variable: %d\n", variable);
    /* vaciamos la salida antes del fork para que el hijo no repita lo ya escrito */
    fflush(stdout);

    /* FR1: creamos el proceso hijo, a partir de aqui hay dos procesos */
    pid = fork();

    if (pid == -1) {
        /* control de errores: no se ha podido crear el hijo */
        printf("Error: no se ha podido crear el proceso hijo.\n");
        exit(1);
    }

    if (pid == 0) {
        /* FR4: estamos en el proceso hijo, suma 4 a su copia de la variable */
        variable = variable + 4;
        /* FR5: mostramos el valor */
        printf("Soy el hijo (PID %d, mi padre es %d). Le sumo 4: %d\n", getpid(), getppid(), variable);
        exit(0);
    } else {
        /* el padre espera a que termine el hijo para que no se mezclen los mensajes */
        wait(NULL);
        /* FR3: estamos en el proceso padre, resta 5 a su copia de la variable */
        variable = variable - 5;
        /* FR5: mostramos el valor */
        printf("Soy el padre (PID %d, mi hijo era %d). Le resto 5: %d\n", getpid(), pid, variable);
    }

    return 0;
}
