#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

/**
 * Tarea 1 - Programación de Procesos en C
 * Este programa crea un proceso hijo, pide un valor al usuario y 
 * realiza operaciones matemáticas distintas en el padre y el hijo.
 */
int main() {
    int variable_inicial;

    // FR2: Pedir al usuario una variable
    printf("Introduce un numero entero: ");
    
    // Implementación de control de errores al leer la entrada
    if (scanf("%d", &variable_inicial) != 1) {
        fprintf(stderr, "Error: Entrada no valida. Se esperaba un numero entero.\n");
        return EXIT_FAILURE;
    }

    // FR5: Mostrar todos los valores por pantalla (valor original)
    printf("Valor original introducido: %d\n", variable_inicial);

    // FR1: Crear un proceso (existirán dos procesos, padre e hijo)
    pid_t pid = fork();

    // Implementación del control de errores para la creación del proceso
    if (pid < 0) {
        perror("Error critico: fallo al ejecutar fork()");
        return EXIT_FAILURE;
    } 
    else if (pid == 0) {
        // Código del proceso hijo
        // FR4: El proceso hijo le sumará 4
        int valor_hijo = variable_inicial + 4;
        
        // FR5: Mostrar todos los valores por pantalla
        printf("Proceso Hijo (PID %d): Valor tras sumar 4 = %d\n", getpid(), valor_hijo);
        
        // Terminar el proceso hijo correctamente
        exit(EXIT_SUCCESS);
    } 
    else {
        // Código del proceso padre
        // FR3: El proceso padre restará 5 a dicha variable
        int valor_padre = variable_inicial - 5;
        
        // FR5: Mostrar todos los valores por pantalla
        printf("Proceso Padre (PID %d): Valor tras restar 5 = %d\n", getpid(), valor_padre);
        
        // Control de procesos: El padre espera a que el hijo termine para evitar procesos zombie
        wait(NULL);
    }

    return EXIT_SUCCESS;
}