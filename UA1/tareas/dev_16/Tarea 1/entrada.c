/*
 * ============================================================================
 *  Archivo     : entrada.c
 *  Módulo      : Entrada de datos (FR2 + control de errores)
 *  Descripción : Implementación de la lectura y validación del número.
 *
 *  Se usa fgets() + strtol() en lugar de scanf("%d") porque scanf no avisa
 *  si el número es demasiado grande y deja "basura" en la entrada cuando
 *  el usuario escribe letras.
 * ============================================================================
 */

#include <stdio.h>      /* printf, fprintf, fgets, getchar, fflush */
#include <stdlib.h>     /* strtol                                  */
#include <string.h>     /* strchr                                  */
#include <errno.h>      /* errno, ERANGE                           */
#include <limits.h>     /* INT_MIN, INT_MAX                        */

#include "entrada.h"
#include "config.h"

/* Tamaño del búfer donde se guarda lo que escribe el usuario.
 * "static" = solo visible dentro de este archivo (detalle interno). */
#define TAM_BUFFER 64

/* Descarta lo que quede en la línea actual de la entrada estándar. */
static void vaciar_linea(void)
{
    int c;
    while ((c = getchar()) != '\n' && c != EOF) {
        /* se descarta el carácter */
    }
}

ResultadoLectura leer_entero(int *resultado)
{
    char buffer[TAM_BUFFER];
    char *fin;          /* strtol nos dice aquí dónde dejó de leer */
    long valor;

    printf("Introduce un número entero: ");
    fflush(stdout);     /* Asegura que el mensaje salga antes de leer */

    /* Error 1: no se puede leer nada (EOF o fallo de lectura). */
    if (fgets(buffer, sizeof(buffer), stdin) == NULL) {
        return LECTURA_EOF;
    }

    /* Error 2: la línea es más larga que el búfer -> se descarta el resto. */
    if (strchr(buffer, '\n') == NULL && !feof(stdin)) {
        vaciar_linea();
        fprintf(stderr, "  [ERROR] Entrada demasiado larga.\n");
        return LECTURA_INVALIDA;
    }

    errno = 0;
    valor = strtol(buffer, &fin, 10);

    /* Error 3: no había ningún número (p. ej. "hola" o línea vacía). */
    if (fin == buffer) {
        fprintf(stderr, "  [ERROR] Eso no es un número entero.\n");
        return LECTURA_INVALIDA;
    }

    /* Error 4: hay caracteres extra después del número (p. ej. "12abc"). */
    while (*fin == ' ' || *fin == '\t') {
        fin++;
    }
    if (*fin != '\n' && *fin != '\0') {
        fprintf(stderr, "  [ERROR] Hay caracteres no válidos tras el número.\n");
        return LECTURA_INVALIDA;
    }

    /* Error 5: el número no cabe en un int. */
    if (errno == ERANGE || valor < INT_MIN || valor > INT_MAX) {
        fprintf(stderr, "  [ERROR] Número fuera de rango (%d .. %d).\n",
                INT_MIN, INT_MAX);
        return LECTURA_INVALIDA;
    }

    /*
     * Error 6: evitar desbordamiento al hacer las operaciones.
     * Si el número es muy pequeño, restar 5 se saldría de rango;
     * si es muy grande, sumar 4 también. Se comprueba ANTES de operar.
     */
    if (valor < (long)INT_MIN + RESTA_PADRE || valor > (long)INT_MAX - SUMA_HIJO) {
        fprintf(stderr, "  [ERROR] Con ese número las operaciones se "
                        "desbordarían. Prueba con otro.\n");
        return LECTURA_INVALIDA;
    }

    *resultado = (int)valor;
    return LECTURA_OK;
}

int pedir_entero_hasta_valido(int *resultado)
{
    ResultadoLectura estado;

    /* Se repite la pregunta hasta que el usuario escriba un valor válido. */
    do {
        estado = leer_entero(resultado);
        if (estado == LECTURA_EOF) {
            return -1;
        }
    } while (estado != LECTURA_OK);

    return 0;
}
