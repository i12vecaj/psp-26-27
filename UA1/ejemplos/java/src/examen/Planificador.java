package examen;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Planificador {

    // Guarda los datos de cada proceso lanzado.
    private static class TareaLanzada {
        String nombre;
        Process proceso;
        String errorInicio;

        TareaLanzada(String nombre, Process proceso, String errorInicio) {
            this.nombre = nombre;
            this.proceso = proceso;
            this.errorInicio = errorInicio;
        }
    }

    public static void main(String[] args) {
        String[] nombres = {"A", "B", "C", "falla", "D"};
        String[] segundos = {"2", "1", "8", "1", "-1"};

        List<TareaLanzada> tareas = new ArrayList<>();

        // Se usa el comando java para iniciar los procesos hijos.
        String java = "java";
        String classpath = System.getProperty("java.class.path");

        /*
         * FR2: se lanzan A LA VEZ las cinco tareas antes de esperar a ninguna.
         * A cada una se le envía un mensaje y se cierra su entrada.
         */
        for (int i = 0; i < nombres.length; i++) {
            String nombre = nombres[i];

            ProcessBuilder constructor = new ProcessBuilder(
                    java,
                    "-cp",
                    classpath,
                    "examen.Tarea",
                    nombre,
                    segundos[i]
            );

            // FR3: añade la salida de error al final de errores.log.
            constructor.redirectError(
                    ProcessBuilder.Redirect.appendTo(new File("errores.log"))
            );

            try {
                Process proceso = constructor.start();

                // Se escribe en la entrada estándar del proceso hijo.
                PrintWriter entradaHijo =
                        new PrintWriter(proceso.getOutputStream(), true);

                entradaHijo.println("Hola desde el padre a " + nombre);
                entradaHijo.close();

                tareas.add(new TareaLanzada(nombre, proceso, null));

            } catch (IOException e) {
                // Guarda el error si no se pudo iniciar el proceso.
                tareas.add(new TareaLanzada(nombre, null, e.getMessage()));
            }
        }

        // FR5: contadores para el resumen final.
        int correctas = 0;
        int errores = 0;
        int tiemposAgotados = 0;

        /*
         * FR4: espera como máximo tres segundos por cada tarea.
         * Si no termina a tiempo, la cancela y no muestra su salida.
         */
        for (TareaLanzada tarea : tareas) {
            if (tarea.proceso == null) {
                System.out.println(tarea.nombre
                        + ": ERROR (no se pudo iniciar: "
                        + tarea.errorInicio + ")");
                errores++;
                continue;
            }

            try {
                boolean haTerminado =
                        tarea.proceso.waitFor(3, TimeUnit.SECONDS);

                if (!haTerminado) {
                    tarea.proceso.destroyForcibly();
                    System.out.println("Tarea " + tarea.nombre + ": TIMEOUT");
                    tiemposAgotados++;
                    continue;
                }

                int codigo = tarea.proceso.exitValue();

                if (codigo == 0) {
                    System.out.println("Tarea " + tarea.nombre + ": OK (código 0)");
                    correctas++;
                } else {
                    System.out.println(tarea.nombre
                            + ": ERROR (código " + codigo + ")");
                    errores++;
                }

                // FR3: lee desde Java la salida normal de la tarea.
                BufferedReader salidaHijo = new BufferedReader(
                        new InputStreamReader(tarea.proceso.getInputStream())
                );

                String linea;
                while ((linea = salidaHijo.readLine()) != null) {
                    System.out.println(linea);
                }

                salidaHijo.close();

            } catch (InterruptedException e) {
                tarea.proceso.destroyForcibly();
                Thread.currentThread().interrupt();

                System.out.println(tarea.nombre
                        + ": ERROR (espera interrumpida)");
                errores++;

            } catch (IOException e) {
                System.out.println("No se pudo leer la salida de "
                        + tarea.nombre + ": " + e.getMessage());
                errores++;
            }
        }

        // FR5: muestra el resumen de tareas OK, ERROR y TIMEOUT.
        System.out.println();
        System.out.println("Resumen:");
        System.out.println("OK: " + correctas);
        System.out.println("ERROR: " + errores);
        System.out.println("TIMEOUT: " + tiemposAgotados);
    }
}