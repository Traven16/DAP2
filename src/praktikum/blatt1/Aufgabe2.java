package praktikum.blatt1;

public class Aufgabe2 {
	
	/**
	 * Einstiegspunkt
	 * @param args Programmargumente
	 */
	public static void main(String[] args) {
		new Aufgabe2(args);
	}
	
	/**
	 * Konstruktor
	 */
	public Aufgabe2(String[] args) {
		if(args.length < 3 & args.length > 0) {
			int[] primes = calcPrimes(Integer.valueOf(args[0]));
			System.out.println("Anzahl gefundener Primzahlen: " + primes.length);
			if(args.length > 1 && args[1].equals("-o"))
				printArray(primes);
		} else {
			System.err.println("Ungueltige Parameterzahl!\n 1. Parameter bestimmt grenze der zu berechnenden Primzahlen.\n 2. Parameter (-o) ermoeglicht ausgabe der gefundenen Primzahlen");
		}
	}
	
	/**
	 * Berechne die Primzahlen bis zur angegebenen Obergrenze
	 */
	private int[] calcPrimes(final int cap) {
		//Booleanarray anlegen und alle Werte mit true initialisieren
		boolean[] isPrime = new boolean[cap];
		for(int i = 0; i < isPrime.length; i++)
			isPrime[i] = true;
		isPrime[0] = false;
		isPrime[1] = false;
		
		//Primzahlen finden
		for(int i = 2; i < cap; i++) {
			//Pruefe, ob aktuelle Zahl eine Primzahl ist
			if(isPrime[i] == true) {
				//Setze alle Vielfachen auf false
				for(int k = i+i; k < cap; k+=i) {
					isPrime[k] = false;
				}
			}
		}

		for(int i = 0; i < isPrime.length-1; i++)
			System.out.print(isPrime[i] + ", ");
		
		//Erzeuge int-Array mit tatsaechlichen Primzahlen aus boolean-Array und gebe dieses aus
		return getPrimes(isPrime);
	}
	
	private int[] getPrimes(final boolean array[]) {
		//Zaehle Anzahl gefundener Primzahlen
		int count = 0;
		for(boolean b : array) {
			if(b)
				count++;
		}
		
		//Erzeuge int-Array
		int[] primes = new int[count];

		//Fuelle int-Array
		count = 0;
		for(int i = 0; i < array.length; i++)
			if(array[i]) {
				primes[count] = i;
				count++;
			}
		
		return primes;
	}

	private void printArray(int[] array) {
		for(int i = 0; i < array.length-1; i++)
			System.out.print(array[i] + ", ");
		System.out.println(array[array.length-1]);
	}
	
}