
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
    int variable;
    pid_t pid;

    // FR2: Pedirá al usuario una variable
    printf("Introduce un valor entero para la variable: ");
    if (scanf("%d", &variable) != 1) {
        // Control de errores en la lectura de la entrada
        fprintf(stderr, "Error: No se ha introducido un valor válido.\n");
        exit(EXIT_FAILURE);
    }

    // FR1: Cree un proceso (existirán en realidad dos procesos, uno padre y el otro hijo)
    pid = fork();

    // Control de errores para la función fork
    if (pid < 0) {
        perror("Error al crear el proceso con fork()");
        exit(EXIT_FAILURE);
    }

    // Código ejecutado por el proceso hijo
    if (pid == 0) {
        printf("[Hijo] PID: %d, PID del Padre: %d\n", getpid(), getppid());
        
        // FR4: El proceso hijo le sumará 4 a la variable
        variable += 4;
        
        // FR5: Mostrará todos los valores por pantalla 
        printf("[Hijo] El valor de la variable tras sumarle 4 es: %d\n", variable);
        
        exit(EXIT_SUCCESS);
    } 
    // Código ejecutado por el proceso padre
    else {
        // Esperamos a que finalice el proceso hijo para mantener un orden limpio en la salida
        wait(NULL);

        printf("[Padre] PID: %d, PID del Hijo creado: %d\n", getpid(), pid);

        // FR3: El proceso padre restará 5 a dicha variable
        variable -= 5;

        // FR5: Mostrará todos los valores por pantalla 
        printf("[Padre] El valor de la variable tras restarle 5 es: %d\n", variable);
    }

    return 0;
}