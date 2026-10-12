/*
 * ============================================================================
 *  Archivo     : main.c
 *  Tarea       : T1 - Programación de Procesos en C
 *  Descripción : Punto de entrada del programa. Pide al usuario un número
 *                entero, crea un proceso hijo con fork() y hace que:
 *                  - el proceso PADRE reste 5 a la variable,
 *                  - el proceso HIJO  sume 4 a la variable,
 *                mostrando por pantalla todos los valores.
 *
 *  Estructura  : el programa está dividido en módulos:
 *                  config.h            -> constantes del enunciado
 *                  entrada.h/.c        -> pedir y validar el número (FR2)
 *                  proceso.h/.c        -> fork, padre, hijo, espera (FR1,3,4,5)
 *                  main.c              -> coordina todo
 *
 *  Compilación : make
 *                (o bien: gcc -Wall -Wextra -o procesos main.c entrada.c proceso.c)
 *  Ejecución   : ./procesos
 *  Sistema     : Linux / Unix (fork() y waitpid() son llamadas POSIX).
 * ============================================================================
 */

#include <stdio.h>      /* printf, fprintf          */
#include <stdlib.h>     /* EXIT_SUCCESS, EXIT_FAILURE */
#include <unistd.h>     /* getpid                   */

#include "entrada.h"
#include "proceso.h"

int main(void)
{
    int variable;       /* La variable que pide el enunciado */
    int valor_original; /* Copia del valor leído, para el resumen */
    pid_t pid;          /* Valor devuelto por fork()          */

    printf("=== T1 - Programación de Procesos en C ===\n\n");

    /* ---------------- FR2: pedir la variable al usuario ---------------- */
    if (pedir_entero_hasta_valido(&variable) != 0) {
        fprintf(stderr, "\n[ERROR] No se pudo leer la entrada (EOF). "
                        "Fin del programa.\n");
        return EXIT_FAILURE;
    }
    valor_original = variable;

    printf("\n[PADRE  PID=%d] Valor original de la variable: %d\n",
           (int)getpid(), variable);
    printf("[PADRE  PID=%d] Dirección de la variable     : %p\n",
           (int)getpid(), (void *)&variable);

    /* ---------------- FR1: crear el proceso hijo ---------------- */
    pid = crear_proceso_hijo();

    if (pid < 0) {
        return EXIT_FAILURE;            /* El error ya se mostró con perror */
    }

    if (pid == 0) {
        ejecutar_hijo(&variable);       /* HIJO: FR4. No vuelve (hace exit). */
    }

    /* ---------------- Solo el PADRE llega hasta aquí ---------------- */
    ejecutar_padre(&variable, pid);                /* FR3 */

    if (esperar_hijo(pid) != 0) {
        return EXIT_FAILURE;
    }

    mostrar_resumen(valor_original, variable);     /* FR5 */

    return EXIT_SUCCESS;
}
