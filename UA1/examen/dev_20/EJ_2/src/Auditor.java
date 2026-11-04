import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase encargada de comprobar que las entradas vendidas
 * coinciden con el aforo total del teatro.
 *
 * Se ejecuta como un proceso independiente iniciado desde
 * SistemaVentas mediante ProcessBuilder.
 *
 * Recibe dos valores por la entrada estandar: el aforo
 * y el total de entradas vendidas.
 *
 * Comprueba que los datos sean validos y devuelve un
 * mensaje con el resultado de la auditoria.
 *
 * Codigos de salida:
 * 0 - Las entradas vendidas coinciden con el aforo.
 * 1 - Se han vendido mas entradas que el aforo.
 * 2 - Se han vendido menos entradas que el aforo.
 * 3 - Los datos recibidos no son validos.
 *
 * El proceso principal puede recuperar este codigo
 * utilizando el metodo waitFor().
 */

public class Auditor {
    public static void main(String[] args) {
        int codigo = 3;
        String mensaje = "Auditor: datos no validos";

        try {
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            String textoAforo = lector.readLine();
            String textoVendido = lector.readLine();

            if (textoAforo != null && textoVendido != null) {
                int aforo = Integer.parseInt(textoAforo.trim());
                int vendido = Integer.parseInt(textoVendido.trim());

                if (aforo >= 0 && vendido >= 0) {
                    if (vendido == aforo) {
                        codigo = 0;
                        mensaje = "Auditor: ventas correctas";
                    } else if (vendido > aforo) {
                        codigo = 1;
                        mensaje = "Auditor: se ha superado el aforo";
                    } else {
                        codigo = 2;
                        mensaje = "Auditor: faltan entradas por vender";
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Se conserva el veredicto de datos no validos y el codigo 3.
        }

        System.out.println(mensaje);
        System.exit(codigo);
    }
}
