#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main()
{
    int numero;
    pid_t pid;

    //FR2: pedir una variable al usuario
    printf("Introduce un numero entero: ");
    if (scanf("%d", &numero) != 1)
    {
        //Control de errores: el usuario no escribio un numero valido
        fprintf(stderr, "Error: no has introducido un numero entero valido.\n");
        return 1;
    }

    printf("Valor inicial: %d\n", numero);

    //Vaciamos el buffer para que el hijo no repita lo ya escrito
    fflush(stdout);

    //FR1: crear un proceso hijo
    pid = fork();

    if (pid < 0)
    {
        //Control de errores: fork ha fallado
        perror("Error al crear el proceso hijo");
        return 1;
    }
    else if (pid == 0)
    {
        //Codigo del proceso HIJO
        //FR4: el hijo suma 4
        numero = numero + 4;

        //FR5: mostrar el valor
        printf("[HIJO]  Valor despues de sumar 4: %d\n", numero);
    }
    else
    {
        //Codigo del proceso PADRE
        //FR3: el padre resta 5
        numero = numero - 5;

        //FR5: mostrar el valor
        printf("[PADRE] Valor despues de restar 5: %d\n", numero);

        //El padre espera a que termine el hijo
        if (wait(NULL) == -1)
        {
            //Control de errores: wait ha fallado
            perror("Error al esperar al proceso hijo");
            return 1;
        }

        printf("[PADRE] El hijo ha terminado.\n");
    }

    return 0;
}
