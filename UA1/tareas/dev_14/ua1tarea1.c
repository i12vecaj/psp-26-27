/**
 * @file      ua1tarea1.c
 * @brief     Crea un proceso hijo con fork: pide un número, el padre le resta 5 y el hijo le suma 4
 * @date      07/10/2026
 * @author    Federico Talgat Lora Ortiz - 76391038+Fedes10@users.noreply.github.com (dev_14)
 * @copyright Copyright (c) 2026 Federico Talgat Lora Ortiz (Fedes10). Todos los derechos reservados.
 */

#include <ctype.h>
#include <errno.h>
#include <limits.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#define RESTA_PADRE 5
#define SUMA_HIJO   4

// el número tiene que caber en un int después de restar 5 o sumar 4
#define MINIMO ((long) INT_MIN + RESTA_PADRE)
#define MAXIMO ((long) INT_MAX - SUMA_HIJO)

// lee una línea del teclado y la convierte en un número entero
// devuelve 1 si es válido, 0 si no lo es y -1 si ya no se puede leer nada
static int leer_entero(int *valor)
{
    char linea[64];
    char *fin;
    long numero;

    if (fgets(linea, sizeof(linea), stdin) == NULL) {
        return -1;
    }

    // si la línea es demasiado larga, tiro el resto para que no se lea en el siguiente intento
    if (strchr(linea, '\n') == NULL && !feof(stdin)) {
        int c;
        while ((c = getchar()) != '\n' && c != EOF) {
        }
        return 0;
    }

    errno = 0;
    numero = strtol(linea, &fin, 10);

    // no había ningún número
    if (fin == linea) {
        return 0;
    }

    // detrás del número solo puede haber espacios o el salto de línea
    while (isspace((unsigned char) *fin)) {
        fin++;
    }
    if (*fin != '\0') {
        return 0;
    }

    if (errno == ERANGE || numero < MINIMO || numero > MAXIMO) {
        return 0;
    }

    *valor = (int) numero;
    return 1;
}

int main(void)
{
    int variable;
    int inicial;
    int leido;
    int estado;
    pid_t pid;

    // FR2: pido la variable antes del fork para que padre e hijo empiecen con el mismo valor
    printf("Introduce un número entero: ");
    fflush(stdout);
    while ((leido = leer_entero(&variable)) == 0) {
        printf("No es un número entero válido (de %ld a %ld). Prueba otra vez: ", MINIMO, MAXIMO);
        fflush(stdout);
    }
    if (leido == -1) {
        fprintf(stderr, "Error: no se ha podido leer ningún número\n");
        return EXIT_FAILURE;
    }

    inicial = variable;
    printf("Valor inicial de la variable: %d\n", inicial);

    // vacío la salida antes del fork
    // si quedara texto pendiente, el hijo se llevaría una copia y saldría repetido
    fflush(stdout);

    // FR1: fork crea el proceso hijo, que es una copia del padre con su propia memoria
    pid = fork();

    if (pid == -1) {
        // no se ha podido crear el hijo, por ejemplo porque hay demasiados procesos
        perror("Error al crear el proceso hijo");
        return EXIT_FAILURE;
    }

    if (pid == 0) {
        // aquí solo entra el hijo, porque a él fork le devuelve 0
        // FR4: el hijo suma 4 a su copia de la variable
        variable = variable + SUMA_HIJO;
        printf("[Hijo]  PID %ld, su padre es %ld: %d + %d = %d\n",
               (long) getpid(), (long) getppid(), inicial, SUMA_HIJO, variable);
        // el hijo termina aquí y no sigue con el código del padre
        exit(EXIT_SUCCESS);
    }

    // aquí solo sigue el padre, porque a él fork le devuelve el PID del hijo
    // espero a que acabe el hijo para que los mensajes no salgan mezclados
    if (waitpid(pid, &estado, 0) == -1) {
        perror("Error al esperar al proceso hijo");
        return EXIT_FAILURE;
    }
    if (!WIFEXITED(estado) || WEXITSTATUS(estado) != EXIT_SUCCESS) {
        fprintf(stderr, "Error: el proceso hijo no ha terminado bien\n");
        return EXIT_FAILURE;
    }

    // el hijo ya sumó 4, pero en su copia: la variable del padre no ha cambiado
    printf("[Padre] el hijo ya ha terminado y mi variable sigue valiendo %d\n", variable);

    // FR3: el padre resta 5 a su copia de la variable
    variable = variable - RESTA_PADRE;
    printf("[Padre] PID %ld, su hijo era %ld: %d - %d = %d\n",
           (long) getpid(), (long) pid, inicial, RESTA_PADRE, variable);

    // FR5: resumen con todos los valores
    printf("Resumen: valor inicial %d, hijo %d, padre %d\n", inicial, inicial + SUMA_HIJO, variable);

    return EXIT_SUCCESS;
}
