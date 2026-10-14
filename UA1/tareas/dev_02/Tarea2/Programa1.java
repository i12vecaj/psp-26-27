//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public static void main(String[] args) {
    StringBuilder contenidoLeido = new StringBuilder();
   // PROCESOS. Crea un programa Java que implemente las siguientes Funcionalidades Requeridas (FRs):

   // FR1: lea una cadena de caracteres desde la entrada estándar hasta recibir un carácter de terminación, en concreto, un asterisco * - 2 puntos
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
        int c;
        while ((c = reader.read()) != -1) {
            char caracter = (char) c;
            // FR2: una vez recibido el caracter de terminación, muestre por pantalla toda la información leída - 2 puntos
            if (caracter == '*') {
                break;
            }
            contenidoLeido.append(caracter);
        }
        System.out.println("Carácter de terminación recibido");
        System.out.println("Información total leída:");
        System.out.println(contenidoLeido.toString());
    }catch (IOException e){
        System.err.println(e.getMessage());
         }
   // FR3: Crea después otro programa que ejecute el anterior - 2 puntos
   // Implementa el control de errores - 2 puntos
   // Documenta el código - 2 puntos
}
