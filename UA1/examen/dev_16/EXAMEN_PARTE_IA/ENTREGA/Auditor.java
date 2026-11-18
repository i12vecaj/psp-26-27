import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Proceso auditor independiente.
 * Lee por su entrada estándar dos líneas (aforo y total vendido), escribe un único
 * veredicto por su salida estándar y termina con un código de salida que indica el
 * resultado de la auditoría.
 */
public class Auditor {

    private static final int CODIGO_COINCIDE = 0;
    private static final int CODIGO_EXCESO = 1;
    private static final int CODIGO_DEFECTO = 2;
    private static final int CODIGO_DATOS_INVALIDOS = 3;

    // Misma codificación que usa SistemaVentas al leer la tubería (P3). System.out usaría
    // la codificación nativa del sistema y alteraría cualquier carácter no ASCII.
    private static final PrintStream SALIDA = new PrintStream(
            new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8);

    public static void main(String[] args) {
        BufferedReader entrada = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));

        int aforo;
        int vendidas;
        try {
            aforo = leerEnteroNoNegativo(entrada);
            vendidas = leerEnteroNoNegativo(entrada);
            if (entrada.readLine() != null) {
                throw new IllegalArgumentException("Se esperaban exactamente dos lineas");
            }
        } catch (IOException | IllegalArgumentException e) {
            // Línea ausente, sobrante, no numérica o negativa: nunca una excepción sin capturar (P5)
            terminar("Auditoria fallida: datos no validos", CODIGO_DATOS_INVALIDOS);
            return;
        }

        if (vendidas == aforo) {
            terminar("Auditoria correcta: vendidas " + vendidas + " de " + aforo, CODIGO_COINCIDE);
        } else if (vendidas > aforo) {
            terminar("Auditoria incorrecta: vendidas " + vendidas + " superan el aforo de " + aforo,
                    CODIGO_EXCESO);
        } else {
            terminar("Auditoria incorrecta: vendidas " + vendidas + " no completan el aforo de " + aforo,
                    CODIGO_DEFECTO);
        }
    }

    /**
     * Lee una línea y la convierte en entero no negativo.
     * NumberFormatException es subclase de IllegalArgumentException.
     */
    private static int leerEnteroNoNegativo(BufferedReader entrada) throws IOException {
        String linea = entrada.readLine();
        if (linea == null) {
            throw new IllegalArgumentException("Falta un dato");
        }
        int valor = Integer.parseInt(linea.trim());
        if (valor < 0) {
            throw new IllegalArgumentException("Valor negativo: " + valor);
        }
        return valor;
    }

    /** Escribe el único veredicto por la salida estándar y termina con el código indicado. */
    private static void terminar(String veredicto, int codigo) {
        SALIDA.println(veredicto);
        System.exit(codigo);
    }
}
