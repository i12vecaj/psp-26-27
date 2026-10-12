/*
 * ============================================================================
 *  Archivo     : entrada.h
 *  Módulo      : Entrada de datos (FR2 + control de errores)
 *  Descripción : Interfaz pública del módulo que pide al usuario un número
 *                entero y lo valida.
 * ============================================================================
 */

#ifndef ENTRADA_H
#define ENTRADA_H

/* Posibles resultados de leer_entero(). */
typedef enum {
    LECTURA_OK       =  0,  /* Se leyó un entero válido                    */
    LECTURA_INVALIDA =  1,  /* Lo escrito no es válido: hay que repetir    */
    LECTURA_EOF      = -1   /* Fin de la entrada (Ctrl+D) o error de lectura */
} ResultadoLectura;

/*
 * leer_entero
 *   Pide UNA vez un número entero por teclado y lo valida.
 *   Parámetros: resultado -> dónde se guarda el número si es válido.
 *   Devuelve  : LECTURA_OK, LECTURA_INVALIDA o LECTURA_EOF.
 */
ResultadoLectura leer_entero(int *resultado);

/*
 * pedir_entero_hasta_valido
 *   Repite la pregunta hasta que el usuario escriba un entero válido.
 *   Parámetros: resultado -> dónde se guarda el número.
 *   Devuelve  : 0 si se obtuvo un número, -1 si la entrada se cerró (EOF).
 */
int pedir_entero_hasta_valido(int *resultado);

#endif /* ENTRADA_H */
