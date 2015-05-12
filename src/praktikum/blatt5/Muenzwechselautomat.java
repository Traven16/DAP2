package praktikum.blatt5;

public class Muenzwechselautomat {
	
	/* Attribute */
	private static String errorMsg = "Aufruf muss mit 2 Argumenten erfolgen:\n"
								   + "1. Argument:\tWaehrung: \"Euro\" oder \"Alternative\"\n"
								   + "2. Argument:\tGeldmenge als positiver int-Wert. Angabe in Cent!";
	
	/**
	 * Einstiegspunkt
	 * @param args Argumente
	 */
	public static void main(String[] args) {
		args = new String[2];
		args[0] = "Alternative";
		args[1] = "455";
		
		//Pruefen, ob alle benoetigten Eingaben getaetigt wurden
		if(args.length != 2)
			System.out.println("errorMsg");
		
		//Parse 2. Argument
		int amount = -1;
		try {
			amount = Integer.valueOf(args[1]);
		} catch (NumberFormatException nfe) {
			System.out.println("2. Argument konnte nicht als int-Wert interpretiert werden!\n");
			System.out.println(errorMsg);
			return;
		}
		if(amount < 0) {
			System.out.println("2. Argument konnte als int-Wert interpretiert werden, ist jedoch negativ!\n");
			System.out.println(errorMsg);
			return;
		}
		
		//Parse 1. Argument
		if(args[0].equals("Euro")) {
			int[] euro = {200,100,50,20,10,5,2,1};
			System.out.println("\"Euro\" gewaehlt:");
			new Muenzwechselautomat(amount, euro);
			
		} else if(args[0].equals("Alternative")) {
			System.out.println("\"Alternative\" gewaehlt:");
			int[] alternative = {200,100,50,20,10,5,4,2,1};
			new Muenzwechselautomat(amount, alternative);
		}
	}
	
	/**
	 * Konstruktor
	 * @param w Waehrung
	 * @param amount Geldmenge
	 */
	public Muenzwechselautomat(final int amount, int[] w) {
//		for(int i = 0; i < 10; i++) {
//			int random = Calculator.randomInt(0, 2000);
//			System.out.print("Betrag [ct]:\t" + random + "\t->\t");
//			printArray(change(random, w));
//		}
		
		System.out.print("Betrag [ct]:\t" + amount + "\t->\t");
		printArray(change(amount, w));
	}
	
	/**
	 * Berechnet optimale Muenzausgabe aus eingehendem Centbetrag.
	 * @param amount Geldmenge in Cent
	 * @param w Waehrung
	 * @return
	 */
	private int[] change(int amount, int[] w) {
		int[] result = new int[w.length];
		
		for(int i = 0; i < result.length; i++) {
			result[i] = amount / w[i];
			amount -= result[i] * w[i];
		}
		
		return result;
	}
	
	/**
	 * Prints the array to the console.
	 */
	private void printArray(int[] array) {
		System.out.print("(");
		
		for(int i = 0; i < array.length-1; i++)		//Nach letztem Element soll kein Komma aus der Schleife stehen!
			System.out.print(array[i] + ", ");
			
		System.out.println(array[array.length-1] + ")");	//Letztes Element manuell ausgeben (inkl. Zeilenumbruch)
	}
	
}