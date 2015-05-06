package praktikum.blatt1;

public class Aufgabe1 {
	
	/**
	 * Einstiegspunkt
	 * @param args Programmargumente
	 */
	public static void main(String[] args) {
		new Aufgabe1(args);
	}
	
	/**
	 * Konstruktor
	 */
	public Aufgabe1(String[] args) {
		int argsAmount = args.length;
		//Sind Parameter uebergeben worden?
		if(argsAmount != 0) {
			System.out.println("Parameterauswertung:");
			//Nur verarbeiten, wenn Parameteranzahl grade...
			if(argsAmount % 2 == 0 && argsAmount > 0)
				//Gehe Paarweise ueber Parameterarray,
				for(int i = 0; i < argsAmount; i+=2) {
					//pruefe Parameter,
					//args[i] = 
					//und gebe Berechnung des ggTs aus
					System.out.println("ggT(" + args[i] + ", " + args[i+1] + ") ist: " +
							   ggT(Integer.valueOf(args[i]), Integer.valueOf(args[i+1])));
				}
			//sonst Error ausgeben
			else
				System.err.println("Anzahl Parametern ungerade! Verarbeitung abgebrochen.");
		} else {
			System.err.println("Keine Parameter angegeben! Verarbeitung abgebrochen.");
		}
	}

	/**
	 * Berechnet den groessten gemeinsamen Teiler der Zahlen a und b.
	 * Beide Parameter muessen positiv sein!
	 */
	private int ggT(final int a, final int b) throws IllegalArgumentException {
		//Abbruchkriterium
		if((a < 0) || (b < 0)) {
			System.out.println("Die Methode muss mit zwei ganzzahligen positiven Parametern aufgerufen werden.");
			throw new IllegalArgumentException("Ein Argument ist negativ!");
		}
		
		//Algorithmus von Euklid
		if(b == 0)
			return a;
		return ggT(b, a % b);
	}

}
