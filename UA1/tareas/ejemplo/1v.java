public class Ejemplo1 {
    public static void main(String[] args) throws IOException  {
        ProcessBuilder pb = new ProcessBuilder("EXPLORER.exe");
        for(int i=0; i<1;i++)
        {
            Process p = pb.start();
            System.out.println("El pid del hijo es "+p.pid());
        }
        long current = ProcessHandle.current().pid();
        System.out.println("El pid del proceso es "+current);
    }
}