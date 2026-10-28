public class Espera {
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(Integer.parseInt(args[0]) * 1000L);
		System.out.println("Espera " + args[0] + " s terminada");
	}
}
