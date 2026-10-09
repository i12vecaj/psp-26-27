#include <stdio.h>
#include "utils.h"

void saludar(const char *nombre) {
    if (nombre != NULL) {
        printf("Hola, %s! Bienvenido a tu proyecto en C.\n", nombre);
    } else {
        printf("Hola! Bienvenido a tu proyecto en C.\n");
    }
}

int sumar(int a, int b) {
    return a + b;
}
