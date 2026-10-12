# ============================================================================
#  Makefile - T1 Programación de Procesos en C
#
#  make         -> compila el programa (crea el ejecutable "procesos")
#  make run     -> compila y ejecuta
#  make clean   -> borra los archivos generados
# ============================================================================

CC      = gcc
CFLAGS  = -Wall -Wextra -std=c11 -D_POSIX_C_SOURCE=200809L
OBJS    = main.o entrada.o proceso.o
EXE     = procesos

$(EXE): $(OBJS)
	$(CC) $(CFLAGS) -o $@ $(OBJS)

main.o: main.c entrada.h proceso.h
	$(CC) $(CFLAGS) -c main.c

entrada.o: entrada.c entrada.h config.h
	$(CC) $(CFLAGS) -c entrada.c

proceso.o: proceso.c proceso.h config.h
	$(CC) $(CFLAGS) -c proceso.c

run: $(EXE)
	./$(EXE)

clean:
	rm -f $(OBJS) $(EXE)

.PHONY: run clean
