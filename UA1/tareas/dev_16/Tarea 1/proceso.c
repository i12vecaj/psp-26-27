/*
 * ============================================================================
 *  Archivo     : proceso.c
 *  Módulo      : Gestión de procesos (FR1, FR3, FR4, FR5)
 *  Descripción : Implementación de la creación del hijo, el trabajo de cada
 *                proceso y la espera del padre.
 * ============================================================================
 */

#include <stdio.h>      /* printf, fprintf, perror, fflush        */
#include <stdlib.h>     /* exit, EXIT_SUCCESS                     */
#include <errno.h>      /* errno, EINTR                           */
#include <unistd.h>     /* fork, getpid, getppid                  */
#include <sys/wait.h>   /* waitpid, WIFEXITED, WEXITSTATUS...     */

#include "proceso.h"
#include "config.h"

pid_t crear_proceso_hijo(void)
{
    pid_t pid;

    /*
     * Vaciamos el búfer de salida ANTES de fork(). Si no, el texto que aún
     * esté pendiente de imprimir se copiaría al hijo y saldría DOS veces
     * (muy típico cuando la salida se redirige a un fichero).
     */
    fflush(stdout);

    pid = fork();

    /* Control de errores: fork() ha fallado (no se creó el hijo). */
    if (pid < 0) {
        perror("[ERROR] fork");
    }

    return pid;
}

void ejecutar_hijo(int *variable)
{
    /* "*variable" es la COPIA del hijo: cambiarla no afecta al padre,
     * aunque su dirección (virtual) sea la misma que en el padre. */
    printf("[HIJO   PID=%d, PPID=%d] He recibido la variable con valor: %d\n",
           (int)getpid(), (int)getppid(), *variable);

    /* FR4: el hijo le suma 4 a SU copia de la variable. */
    *variable += SUMA_HIJO;

    /* FR5: mostrar el valor */
    printf("[HIJO   PID=%d] Tras sumar %d -> variable = %d   (dir. %p)\n",
           (int)getpid(), SUMA_HIJO, *variable, (void *)variable);
    fflush(stdout);

    /* El hijo termina aquí y nunca ejecuta el código del padre. */
    exit(EXIT_SUCCESS);
}

void ejecutar_padre(int *variable, pid_t pid_hijo)
{
    /* fork() devuelve al padre el PID del hijo (un número > 0). */
    printf("[PADRE  PID=%d] He creado un hijo con PID=%d\n",
           (int)getpid(), (int)pid_hijo);

    /* FR3: el padre le resta 5 a SU copia de la variable. */
    *variable -= RESTA_PADRE;

    /* FR5: mostrar el valor */
    printf("[PADRE  PID=%d] Tras restar %d -> variable = %d   (dir. %p)\n",
           (int)getpid(), RESTA_PADRE, *variable, (void *)variable);
    fflush(stdout);
}

int esperar_hijo(pid_t pid_hijo)
{
    int estado_hijo;
    pid_t terminado;

    /* Reintentar si una señal interrumpe la espera (EINTR). */
    do {
        terminado = waitpid(pid_hijo, &estado_hijo, 0);
    } while (terminado == -1 && errno == EINTR);

    if (terminado == -1) {
        perror("[ERROR] waitpid");
        return -1;
    }

    if (WIFEXITED(estado_hijo)) {
        printf("[PADRE  PID=%d] El hijo %d terminó con código %d\n",
               (int)getpid(), (int)pid_hijo, WEXITSTATUS(estado_hijo));
        if (WEXITSTATUS(estado_hijo) != EXIT_SUCCESS) {
            fprintf(stderr, "[ERROR] El hijo terminó con errores.\n");
            return -1;
        }
    } else if (WIFSIGNALED(estado_hijo)) {
        fprintf(stderr, "[ERROR] El hijo fue terminado por la señal %d\n",
                WTERMSIG(estado_hijo));
        return -1;
    }

    return 0;
}

void mostrar_resumen(int valor_original, int valor_padre)
{
    /*
     * OJO: el padre NO puede leer la variable del hijo (son memorias
     * distintas). El valor del hijo que se muestra aquí lo CALCULA el padre;
     * el valor real del hijo es el que el propio hijo imprimió antes.
     */
    printf("\n=== RESUMEN ===\n");
    printf("Valor original           : %d\n", valor_original);
    printf("Valor en el padre (-%d)   : %d\n", RESTA_PADRE, valor_padre);
    printf("Valor en el hijo  (+%d)   : %d\n", SUMA_HIJO, valor_original + SUMA_HIJO);
    printf("\nEl padre sigue teniendo %d: el +%d del hijo NO le afectó,\n"
           "porque cada proceso tiene su propia copia de la memoria.\n",
           valor_padre, SUMA_HIJO);
}
