/*
PROCESOS. Crea un único programa en C que implemente las siguientes Funcionalidades Requeridas (FRs):

FR1: cree un proceso (existirán en realidad dos procesos, uno padre y el otro hijo) - 1 punto
FR2: pedirá al usuario una variable - 1 punto
FR3: el proceso padre restará 5 a dicha variable - 1 punto
FR4: el proceso hijo le sumará 4 - 1 punto
FR5: mostrará todos los valores por pantalla - 1 punto
Implementa el control de errores - 2 puntos
Documenta el código - 2 puntos
Y ahora ... ¿Entiendes lo que está pasando? ¿De verdad?
*/


#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>

int main() {
    int variable;
    pid_t pid;

    // Pedimos al usuario una variable
    printf("Introduce un numero: ");
    if (scanf("%d", &variable) != 1) {
        fprintf(stderr, "Error: debes introducir un numero.\n");
        return EXIT_FAILURE;
    }

    // Creamos el proceso hijo
    pid = fork();

    // Controlamos si fork() ha fallado
    if (pid < 0) {
        perror("Error al crear el proceso");
        return EXIT_FAILURE;
    }

    // Proceso hijo
    if (pid == 0) {
        printf("HIJO: valor original = %d\n", variable);
        variable += 4;
        printf("HIJO: valor despues de sumar 4 = %d\n", variable);
    }

    // Proceso padre
    else {
        printf("PADRE: valor original = %d\n", variable);
        variable -= 5;
        printf("PADRE: valor despues de restar 5 = %d\n", variable);
    }

    return EXIT_SUCCESS;
}