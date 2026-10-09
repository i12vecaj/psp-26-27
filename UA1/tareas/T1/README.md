# Proyecto C

Estructura modular básica para un proyecto en lenguaje C (estándar C11).

## Estructura de Carpetas

```text
Proyecto C/
├── include/           # Archivos de cabecera (.h)
│   └── utils.h
├── src/               # Código fuente (.c)
│   ├── main.c        # Punto de entrada principal
│   └── utils.c       # Implementación de utilidades
├── build/             # Archivos objeto y ejecutables generados (ignorado en git)
├── CMakeLists.txt     # Configuración para compilar con CMake
├── Makefile           # Script de compilación para Make / MinGW
├── .gitignore
└── README.md
```

## Formas de Compilar y Ejecutar

### Opción 1: Usando Makefile (Recomendado)

Compilar el proyecto:
```bash
mingw32-make
```

Ejecutar la aplicación:
```bash
mingw32-make run
```
O directamente:
```bash
.\build\app.exe
```

Limpiar archivos compilados:
```bash
mingw32-make clean
```

---

### Opción 2: Compilación directa con GCC

```bash
gcc -Wall -Wextra -std=c11 -Iinclude src/main.c src/utils.c -o app.exe
.\app.exe
```

---

### Opción 3: Usando CMake

```bash
cmake -B build
cmake --build build
.\build\app.exe
```
