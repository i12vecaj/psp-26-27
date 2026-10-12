/*
 * ============================================================================
 *  Archivo     : procesos.c
 *  Tarea       : T1 - Programación de Procesos en C
 *  Descripción : Programa que pide al usuario un número entero, crea un
 *                proceso hijo con fork() y hace que:
 *                  - el proceso PADRE reste 5 a la variable,
 *                  - el proceso HIJO  sume 4 a la variable,
 *                mostrando por pantalla todos los valores (original, del
 *                padre y del hijo) junto con los PID de cada proceso.
 *
 *  Idea clave  : tras fork() el hijo recibe una COPIA de la memoria del
 *                padre. Cada proceso trabaja con SU PROPIA variable, por lo
 *                que lo que hace uno NO afecta al otro.
 *
 *  Compilación : gcc -Wall -Wextra -o procesos procesos.c
 *  Ejecución   : ./procesos
 *  Sistema     : Linux / Unix (fork() y waitpid() son llamadas POSIX).
 * ============================================================================
 */

#include <stdio.h>      /* printf, fprintf, fgets, perror, fflush        */
#include <stdlib.h>     /* strtol, exit, EXIT_SUCCESS, EXIT_FAILURE      */
#include <string.h>     /* strchr                                         */
#include <errno.h>      /* errno, ERANGE, EINTR                           */
#include <limits.h>     /* INT_MIN, INT_MAX                               */
#include <unistd.h>     /* fork, getpid, getppid                          */
#include <sys/types.h>  /* pid_t                                          */
#include <sys/wait.h>   /* waitpid, WIFEXITED, WEXITSTATUS...             */

/* Constantes del enunciado: así no hay "números mágicos" en el código. */
#define RESTA_PADRE 5   /* FR3: el padre resta 5 */
#define SUMA_HIJO   4   /* FR4: el hijo suma 4   */

/* Tamaño del búfer donde se guarda lo que escribe el usuario. */
#define TAM_BUFFER 64

/*
 * ----------------------------------------------------------------------------
 *  leer_entero
 * ----------------------------------------------------------------------------
 *  FR2 + control de errores: pide un número entero al usuario y lo valida.
 *
 *  Se usa fgets() + strtol() en lugar de scanf("%d") porque scanf no avisa
 *  si el número es demasiado grande y deja "basura" en la entrada cuando
 *  el usuario escribe letras.
 *
 *  Parámetros:
 *    resultado -> puntero donde se guardará el número leído.
 *
 *  Devuelve:
 *     0 si se leyó un entero válido.
 *    -1 si se llegó al final de la entrada (EOF, p. ej. Ctrl+D) o hubo
 *       un error de lectura.
 *     1 si lo escrito no es un entero válido (hay que volver a pedirlo).
 * ----------------------------------------------------------------------------
 */
static int leer_entero(int *resultado)
{
    char buffer[TAM_BUFFER];
    char *fin;          /* strtol nos dice aquí dónde dejó de leer */
    long valor;

    printf("Introduce un número entero: ");
    fflush(stdout);     /* Asegura que el mensaje salga antes de leer */

    /* Error 1: no se puede leer nada (EOF o fallo de lectura). */
    if (fgets(buffer, sizeof(buffer), stdin) == NULL) {
        return -1;
    }

    /* Error 2: la línea es más larga que el búfer -> se descarta el resto. */
    if (strchr(buffer, '\n') == NULL && !feof(stdin)) {
        int c;
        while ((c = getchar()) != '\n' && c != EOF) {
            /* vaciamos la línea sobrante */
        }
        fprintf(stderr, "  [ERROR] Entrada demasiado larga.\n");
        return 1;
    }

    errno = 0;
    valor = strtol(buffer, &fin, 10);

    /* Error 3: no había ningún número (p. ej. "hola" o línea vacía). */
    if (fin == buffer) {
        fprintf(stderr, "  [ERROR] Eso no es un número entero.\n");
        return 1;
    }

    /* Error 4: hay caracteres extra después del número (p. ej. "12abc"). */
    while (*fin == ' ' || *fin == '\t') {
        fin++;
    }
    if (*fin != '\n' && *fin != '\0') {
        fprintf(stderr, "  [ERROR] Hay caracteres no válidos tras el número.\n");
        return 1;
    }

    /* Error 5: el número no cabe en un int. */
    if (errno == ERANGE || valor < INT_MIN || valor > INT_MAX) {
        fprintf(stderr, "  [ERROR] Número fuera de rango (%d .. %d).\n",
                INT_MIN, INT_MAX);
        return 1;
    }

    /*
     * Error 6: evitar desbordamiento al hacer las operaciones.
     * Si el número es muy pequeño, restar 5 se saldría de rango;
     * si es muy grande, sumar 4 también. Se comprueba ANTES de operar.
     */
    if (valor < (long)INT_MIN + RESTA_PADRE || valor > (long)INT_MAX - SUMA_HIJO) {
        fprintf(stderr, "  [ERROR] Con ese número las operaciones se "
                        "desbordarían. Prueba con otro.\n");
        return 1;
    }

    *resultado = (int)valor;
    return 0;
}

/*
 * ----------------------------------------------------------------------------
 *  main
 * ----------------------------------------------------------------------------
 */
int main(void)
{
    int variable;       /* La variable que pide el enunciado */
    int estado_lectura;
    pid_t pid;          /* Valor devuelto por fork() */

    printf("=== T1 - Programación de Procesos en C ===\n\n");

    /* ---------------- FR2: pedir la variable al usuario ---------------- */
    /* Se repite la pregunta hasta que el usuario escriba un valor válido. */
    do {
        estado_lectura = leer_entero(&variable);
        if (estado_lectura == -1) {
            fprintf(stderr, "\n[ERROR] No se pudo leer la entrada (EOF). "
                            "Fin del programa.\n");
            return EXIT_FAILURE;
        }
    } while (estado_lectura != 0);

    printf("\n[PADRE  PID=%d] Valor original de la variable: %d\n",
           (int)getpid(), variable);
    printf("[PADRE  PID=%d] Dirección de la variable     : %p\n",
           (int)getpid(), (void *)&variable);

    /*
     * Vaciamos el búfer de salida ANTES de fork(). Si no, el texto que aún
     * esté pendiente de imprimir se copiaría al hijo y saldría DOS veces
     * (muy típico cuando la salida se redirige a un fichero).
     */
    fflush(stdout);

    /* ---------------- FR1: crear el proceso hijo ---------------- */
    pid = fork();

    if (pid < 0) {
        /* Control de errores: fork() ha fallado (no se creó el hijo). */
        perror("[ERROR] fork");
        return EXIT_FAILURE;
    }

    if (pid == 0) {
        /* ======================= PROCESO HIJO ======================= */
        /* fork() devuelve 0 en el hijo. */
        printf("[HIJO   PID=%d, PPID=%d] He recibido la variable con valor: %d\n",
               (int)getpid(), (int)getppid(), variable);

        /* FR4: el hijo le suma 4 a SU copia de la variable. */
        variable += SUMA_HIJO;

        /* FR5: mostrar el valor */
        printf("[HIJO   PID=%d] Tras sumar %d -> variable = %d   (dir. %p)\n",
               (int)getpid(), SUMA_HIJO, variable, (void *)&variable);
        fflush(stdout);

        /* El hijo termina aquí y nunca ejecuta el código del padre. */
        exit(EXIT_SUCCESS);
    }

    /* ======================= PROCESO PADRE ======================= */
    /* fork() devuelve al padre el PID del hijo (un número > 0). */
    printf("[PADRE  PID=%d] He creado un hijo con PID=%d\n",
           (int)getpid(), (int)pid);

    /* FR3: el padre le resta 5 a SU copia de la variable. */
    variable -= RESTA_PADRE;

    /* FR5: mostrar el valor */
    printf("[PADRE  PID=%d] Tras restar %d -> variable = %d   (dir. %p)\n",
           (int)getpid(), RESTA_PADRE, variable, (void *)&variable);
    fflush(stdout);

    /*
     * El padre espera a que el hijo termine. Así:
     *   - evitamos que el hijo se quede como proceso "zombie",
     *   - el resumen final sale cuando el hijo ya ha terminado.
     */
    int estado_hijo;
    pid_t terminado;
    do {
        terminado = waitpid(pid, &estado_hijo, 0);
    } while (terminado == -1 && errno == EINTR);   /* reintentar si una señal interrumpe */

    if (terminado == -1) {
        perror("[ERROR] waitpid");
        return EXIT_FAILURE;
    }

    if (WIFEXITED(estado_hijo)) {
        printf("[PADRE  PID=%d] El hijo %d terminó con código %d\n",
               (int)getpid(), (int)pid, WEXITSTATUS(estado_hijo));
        if (WEXITSTATUS(estado_hijo) != EXIT_SUCCESS) {
            fprintf(stderr, "[ERROR] El hijo terminó con errores.\n");
            return EXIT_FAILURE;
        }
    } else if (WIFSIGNALED(estado_hijo)) {
        fprintf(stderr, "[ERROR] El hijo fue terminado por la señal %d\n",
                WTERMSIG(estado_hijo));
        return EXIT_FAILURE;
    }

    /* ---------------- FR5: resumen de todos los valores ---------------- */
    /*
     * OJO: el padre NO puede leer la variable del hijo (son memorias
     * distintas). El valor del hijo que se muestra aquí lo CALCULA el padre;
     * el valor real del hijo es el que el propio hijo imprimió arriba.
     */
    printf("\n=== RESUMEN ===\n");
    printf("Valor original           : %d\n", variable + RESTA_PADRE);
    printf("Valor en el padre (-%d)   : %d\n", RESTA_PADRE, variable);
    printf("Valor en el hijo  (+%d)   : %d\n", SUMA_HIJO, variable + RESTA_PADRE + SUMA_HIJO);
    printf("\nEl padre sigue teniendo %d: el +%d del hijo NO le afectó,\n"
           "porque cada proceso tiene su propia copia de la memoria.\n",
           variable, SUMA_HIJO);

    return EXIT_SUCCESS;
}
