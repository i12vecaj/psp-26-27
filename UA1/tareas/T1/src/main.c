#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include "utils.h"

/**
 * Función principal del programa.
 * Cumple con los requisitos de crear dos procesos (padre e hijo), 
 * pedir una variable al usuario, realizar operaciones matemáticas 
 * con dicha variable en cada proceso y mostrar los resultados.
 */
int main(int argc, char *argv[]) {
    // Si recibimos más de un argumento, actuamos como el proceso HIJO (FR1)
    if (argc > 1) {
        // Obtenemos la variable pasada como argumento por el padre
        int variable = atoi(argv[1]);
        
        // FR4: El proceso hijo le suma 4
        int resultado_hijo = variable + 4;
        
        // FR5: Mostrar todos los valores por pantalla
        printf("[Hijo] PID: %lu | Valor recibido: %d | Resultado (valor + 4): %d\n", 
               GetCurrentProcessId(), variable, resultado_hijo);
               
        return 0; // El hijo termina con éxito
    }

    // Proceso PADRE
    int variable = 0;
    
    // FR2: Pedir al usuario una variable
    printf("Introduce un numero entero: ");
    if (scanf("%d", &variable) != 1) {
        // Control de errores al leer la variable
        fprintf(stderr, "Error: No se ha introducido un numero entero valido.\n");
        return 1;
    }

    STARTUPINFO si;
    PROCESS_INFORMATION pi;

    // Inicializamos las estructuras de memoria necesarias a cero
    ZeroMemory(&si, sizeof(si));
    si.cb = sizeof(si);
    ZeroMemory(&pi, sizeof(pi));

    // Preparamos el comando: ejecutamos el mismo programa (argv[0]) y pasamos la variable como argumento
    char cmdLine[256];
    // Control de errores: snprintf asegura que no haya desbordamiento de búfer
    if (snprintf(cmdLine, sizeof(cmdLine), "\"%s\" %d", argv[0], variable) < 0) {
        fprintf(stderr, "Error al formatear la linea de comandos.\n");
        return 1;
    }

    // FR1: Creamos el proceso hijo
    if (!CreateProcess(
        NULL,           // Nombre del módulo (usamos la línea de comandos)
        cmdLine,        // Línea de comandos a ejecutar (programa + variable)
        NULL,           // Atributos de seguridad del proceso
        NULL,           // Atributos de seguridad del hilo
        FALSE,          // No heredar handles
        0,              // Flags de creación
        NULL,           // Usar el bloque de entorno del padre
        NULL,           // Usar el directorio actual del padre
        &si,            // Puntero a STARTUPINFO
        &pi             // Puntero a PROCESS_INFORMATION
    )) {
        // Control de errores si falla la creación del proceso
        fprintf(stderr, "Fallo al crear el proceso hijo (Error: %lu).\n", GetLastError());
        return 1;
    }

    // FR3: El proceso padre restará 5 a dicha variable
    int resultado_padre = variable - 5;
    
    // FR5: Mostrar todos los valores por pantalla
    printf("[Padre] PID: %lu | Valor inicial: %d | Resultado (valor - 5): %d\n", 
           GetCurrentProcessId(), variable, resultado_padre);

    // Esperamos a que el proceso hijo finalice (equivalente a wait() en POSIX) para mantener el orden
    if (WaitForSingleObject(pi.hProcess, INFINITE) == WAIT_FAILED) {
        fprintf(stderr, "Error al esperar al proceso hijo (Error: %lu).\n", GetLastError());
    }

    // Control de errores: Es fundamental cerrar los handles para liberar recursos en Windows
    CloseHandle(pi.hProcess);
    CloseHandle(pi.hThread);

    // Evitamos que la consola se cierre inmediatamente si se ejecuta con doble clic
    printf("\n");
    system("pause");

    return 0;
}