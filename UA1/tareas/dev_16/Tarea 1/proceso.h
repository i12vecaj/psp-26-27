/*
 * ============================================================================
 *  Archivo     : proceso.h
 *  Módulo      : Gestión de procesos (FR1, FR3, FR4, FR5)
 *  Descripción : Interfaz pública del módulo que crea el proceso hijo,
 *                define lo que hace cada proceso y espera al hijo.
 *
 *  Idea clave  : tras fork() el hijo recibe una COPIA de la memoria del
 *                padre. Cada proceso trabaja con SU PROPIA variable, por lo
 *                que lo que hace uno NO afecta al otro.
 * ============================================================================
 */

#ifndef PROCESO_H
#define PROCESO_H

#include <sys/types.h>  /* pid_t */

/*
 * crear_proceso_hijo  (FR1)
 *   Vacía el búfer de salida y llama a fork().
 *   Devuelve: 0 en el hijo, el PID del hijo en el padre, -1 si hubo error
 *             (en ese caso ya se ha mostrado el mensaje con perror).
 */
pid_t crear_proceso_hijo(void);

/*
 * ejecutar_hijo  (FR4 + FR5)
 *   Código del proceso hijo: suma SUMA_HIJO a su copia de la variable,
 *   muestra el resultado y TERMINA el proceso (no vuelve nunca).
 *   Parámetros: variable -> puntero a la variable de main() (la copia
 *               del hijo, porque estamos ya en el proceso hijo).
 */
void ejecutar_hijo(int *variable);

/*
 * ejecutar_padre  (FR3 + FR5)
 *   Código del proceso padre: resta RESTA_PADRE a su copia de la variable
 *   y muestra el resultado.
 *   Parámetros: variable -> puntero a la variable de main() del padre.
 *               pid_hijo -> PID del hijo (lo que devolvió fork()).
 */
void ejecutar_padre(int *variable, pid_t pid_hijo);

/*
 * esperar_hijo
 *   El padre espera a que el hijo termine (evita procesos zombie) y
 *   comprueba cómo terminó.
 *   Devuelve: 0 si el hijo terminó correctamente, -1 en caso contrario.
 */
int esperar_hijo(pid_t pid_hijo);

/*
 * mostrar_resumen  (FR5)
 *   Muestra los valores original, del padre y del hijo.
 */
void mostrar_resumen(int valor_original, int valor_padre);

#endif /* PROCESO_H */
