#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    int variable;
    pid_t pid;

   
    printf("Introduce un valor entero: ");
    if (scanf("%d", &variable) != 1) {
        
        fprintf(stderr, "Error: Entrada no válida.\n");
        return 1;
    }

   
    printf("Valor original: %d\n\n", variable);

  
    pid = fork();


    if (pid < 0) {
        perror("Error al hacer fork");
        return 1;
    } 
    else if (pid == 0) {
        variable += 4;
       
        printf("Soy el proceso HIJO. Valor tras sumar 4: %d\n", variable);
    } 
    else {
        wait(NULL);
        
        variable -= 5;
        
        printf("Soy el proceso PADRE. Valor tras restar 5: %d\n", variable);
    }

    return 0;
}