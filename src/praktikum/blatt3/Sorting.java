package praktikum.blatt3;

import java.util.ArrayList;
import java.util.Random;

/**
 * Sorting - expanded
 * 
 * @author Lukas Potthast, Hendrik Schmieding
 * @version 0.0.0.5
 * @time 28.04.2015 10:53
 */

public class Sorting {
	
	/* Attributes */
	private static final byte INSERTIONSORT = 0, MERGESORT = 1, BUBBLESORT = 2;
	private static final Random random = new Random();
	private static final String infoMsg = "\nINFO:\nAufruf lediglich mit 1, 2, 3 oder 4 Parametern gestattet:\n"
										+ "\t1. Parameter: Groesse des zu initialisierenden Feldes (positiver int Wert!)\n"
										+ "\t2. Parameter: Fuellart(\"auf\", \"ab\", \"rand\")\n"
										+ "\t3. Parameter: Zu verwendender Sortieralagorithmus(\"insertion\", \"merge\", \"bubble\", \"all\")\n"
										+ "\t4. Parameter: Ziellaufzeit als float.\n"
										+ "Sollte kein zweiter Parameter definiert werden, wird das Feld mit zufaelligen Werten (\"rand\") gefuellt.\n"
										+ "Sollte kein dritter Parameter definiert werden, werden alle Sortieralgorithmen (\"all\") nacheinander ausgefuehrt.\n"
										+ "Sollte kein vierter Parameter definiert werden, wird normal, unter Verwendung von Parametern 1-3, sortiert.";
	
	/**
	 * Entrance point
	 * No use of err stream due to output delay...
	 * @param args Program arguments
	 */
	public static void main(String[] args) {
		new Sorting(args);
	}
	
	/**
	 * Constructor
	 * @param args Program arguments
	 */
	public Sorting(String[] args) {
		args = new String[4];
		args[0] = "50000";
		args[1] = "ab";
		args[2] = "merge";
		args[3] = "1.7";
		
		boolean printError = false;
		
		//----- Parameter ausgeben -----
		System.out.print("Uebergebene Parameter: (");
		
		if(args.length != 0) {
			for(int i = 0; i < args.length-1; i++)
				System.out.print("\"" + args[i] + "\", ");
			
			System.out.print("\"" + args[args.length-1] + "\"");
		}
		System.out.println(")\n");
		
		//----- Auf ungueltige Parameterzahl testen -----
		if(args.length > 4 || args.length < 1) {
			if(args.length < 1) {
				System.out.println("ERROR: Ungueltige Anzahl an Parametern!\n");
				printError = true;
				return;
			}
			
			if(args.length > 4) {
				System.out.println("ERROR: Ungueltige Anzahl an Parametern!\nVersuche trotzdem mit Programm fortzufahren...\n");
				printError = true;
			}
		}
		
		//----- Programmargumente verarbeiten -----
		//Variablen zum Halten der Argumentdaten anlegen
		int initialArrayLength = 0;
		String initializationMode = "rand", algorithmToUse = "all";
		float targetTime = -1;
		
		//1. Parameter auslesen
		if(isPositiveInt(args[0]))
			try {
				initialArrayLength = Integer.valueOf(args[0]);
			} catch(NumberFormatException nfe) {
				System.out.println("ERROR: 1. Parameter ist positive Zahl, konnte jedoch trotzdem nicht verarbeitet werden. Zahl muss zwischen 0 und " + Integer.MAX_VALUE + " liegen!\n");
				System.out.println(infoMsg);
				return;
			}
			
		else {
			System.out.println("ERROR: 1. Parameter ist kein positiver int-Wert. Eingabe beinhaltet unzulaessige Zeichen!\n");
			System.out.println(infoMsg);
			return;
		}
		
		//2. Parameter auslesen
		if(args.length > 1)
			if(args[1].equals("auf") || args[1].equals("ab") || args[1].equals("rand"))
				initializationMode = args[1];
			else {
				System.out.println("ERROR: 2. Parameter konnte nicht verarbeitet werden!\nEs wird mit zufaelliger Initialisierung \"rand\" fortgefahren.\n");
				printError = true;
			}
		else {
			System.out.println("ERROR: Es wurde kein Initialisierungstyp (2. Parameter) angegeben!\nEs wird mit zufaelliger Initialisierung \"rand\" fortgefahren.\n");
			printError = true;
		}
		
		//3.Parameter auslesen
		if(args.length > 2)
			if(args[2].equals("insertion") || args[2].equals("merge") || args[2].equals("bubble") || args[2].equals("all"))
				algorithmToUse = args[2];
			else {
				System.out.println("ERROR: 3. Parameter konnte nicht verarbeitet werden!\nEs wird mit allen Algorithmen sortiert (\"all\").");
				printError = true;
			}
		else {
			System.out.println("ERROR: Es wurde kein Sortieralgorithmus (3. Parameter) angegeben!\nEs wird mit allen Algorithmen sortiert (\"all\").\n");
			printError = true;
		}
		
		//4. Parameter auslesen
		if(args.length > 3) {
			try {
				targetTime = Float.valueOf(args[3]) * 1000.0f;
			} catch(NumberFormatException nfe) {
				System.out.println("Hinweis: 4. Parameter ist kein float Wert!\n");
			}
		} else {
			System.out.println("Hinweis: Es wurde keine Ziellaufzeit (4. Parameter) angegeben!\n");
			printError = true;
		}
		
		//Wenn Fehler aufgetreten ist -> Zeige InfoMsg
		if(printError)
			System.out.println(infoMsg);
		
		//----- SORTIERUNG -----
		//Array anlegen...
		int[] data = new int[initialArrayLength];
		
		//und mit Werten abhaengig des 2. Parameters initialisieren
		switch(initializationMode) {
			case "rand":
				initRandom(data, 0, 2*data.length);
				break;
			case "auf":
				for(int i = 0; i < data.length; i++)
					data[i] = i+1;
				break;
			case "ab":
				for(int i = 0; i < data.length; i++)
					data[i] = data.length-i;
				break;
		}
		
		//Kopien des Datenarrays mit angegebenem Algorithmus sortieren (ggf. mit allen)
		switch(algorithmToUse) {
			case "insertion":
				if(targetTime >= 0)	findArraySize(targetTime, data, INSERTIONSORT);
				else				sortArrayWith(createCopy(data), INSERTIONSORT);
				break;
			case "merge":
				if(targetTime >= 0)	findArraySize(targetTime, data, MERGESORT);
				else				sortArrayWith(createCopy(data), MERGESORT);
				break;
			case "bubble":
				if(targetTime >= 0)	findArraySize(targetTime, data, BUBBLESORT);
				else				sortArrayWith(createCopy(data), BUBBLESORT);
				break;
			case "all":
				if(targetTime >= 0)	{
					findArraySize(targetTime, data, INSERTIONSORT);
					findArraySize(targetTime, data, MERGESORT);
					findArraySize(targetTime, data, BUBBLESORT);
				} else {
					sortArrayWith(createCopy(data), INSERTIONSORT);
					sortArrayWith(createCopy(data), MERGESORT);
					sortArrayWith(createCopy(data), BUBBLESORT);
				}
				break;
		}
	}
	
	private void findArraySize(final float targetTime, int[] array, final byte algorithmDeclaration) {
		//Bis zu welcher Genauigkeit soll die Feldgroesse bestimmt werden? Angabe in ms.
		final float maxTimeDifference = 100;
		
		if(targetTime == 0) {
			System.out.println("Schritt:\tLaenge:\t\tZeit:");
			System.out.println("Zeit 0:\t\t0\t\t0ms");
			return;
		}
		
		//Lege ArrayList zum Halten der Laufzeiten an
		ArrayList<Integer[]> times = new ArrayList<Integer[]>();
		Integer[] zeroData = {0, 0};
		times.add(zeroData);
		
		//Berechne erste Laufzeit
		float timeNeeded = sortArrayWith(array, algorithmDeclaration);
		
		while(timeNeeded < targetTime - 0.1f) {
			Integer[] data = {array.length, (int)timeNeeded};
			times.add(data);
			
			//Laenge des Arrays verdoppeln
			array = new int[array.length * 2];
			//Neu absteigend initialisieren
			for(int i = 0; i < array.length; i++)
				array[i] = array.length-i;
			
			//Sortieren und Laufzeit sichern
			timeNeeded = sortArrayWith(array, algorithmDeclaration);
			
		}
		Integer[] data = {array.length, (int)timeNeeded};
		times.add(data);
		
		//-------------------------
		
		int left = times.get(times.size()-2)[0];
		int right = times.get(times.size()-1)[0];
		
		while(!(timeNeeded > targetTime - maxTimeDifference && timeNeeded < targetTime + maxTimeDifference)) {
			//Array mit mittlerer Groesse der letzten beiden Durchlaeufe initialisieren
			int newSize = (left + right) / 2;
			
			array = new int[newSize];
			//Neu absteigend initialisieren
			for(int i = 0; i < array.length; i++)
				array[i] = array.length-i;
			
			//Sortieren und Laufzeit sichern
			timeNeeded = sortArrayWith(array, algorithmDeclaration);
			Integer[] data2 = {array.length, (int)timeNeeded};
			times.add(data2);
			
			//Grenzen neu festlegen
			if(timeNeeded > targetTime)	right = newSize;
			else						left  = newSize;
		}
		
		//Zeiten ausgeben
		System.out.println("Schritt:\tLaenge:\t\tZeit:");
		for(int i = 0; i < times.size(); i++) {
			System.out.println("Zeit " + i + ":\t" + times.get(i)[0] + "\t" + times.get(i)[1] + "ms");
		}
	}
	
	
	/**
	 * Sorts the given int array with the specified algorirthm.
	 * @param array int array
	 * @param algorithmDeclaration Use the constants defined in the "Sort" class.
	 */
	private long sortArrayWith(int[] array, final byte algorithmDeclaration) {
		System.out.print("\n");
		
		//Variablen definieren
		long timeStart = 0, timeEnd = 0;
		String usedAlgorithm = "-";
				
		//Sortierverfahren bestimmen
		switch(algorithmDeclaration) {
			case INSERTIONSORT:
				usedAlgorithm = "Insertionsort";
				break;
			
			case MERGESORT:
				usedAlgorithm = "Mergesort";
				break;
				
			case BUBBLESORT:
				usedAlgorithm = "Bubblesort";
				break;
				
			default:
				System.err.println("Incorrect definition of the algorithmDeclaration parameter!");
				System.exit(1);
		}
		
		System.out.println(usedAlgorithm + ":\n");
		
		//Array vor Sortierung ausgeben, wenn groesse 100 nicht ueberschreitet
		if(array.length <= 100) {
			System.out.print("Aktuelle Sortierung:  ");
			printArray(array);
		}
		
		//Array mit Zeitmessung sortieren
		switch(algorithmDeclaration) {
			case INSERTIONSORT:
				timeStart = System.currentTimeMillis();
				insertionSort(array);
				timeEnd = System.currentTimeMillis();
				break;
			
			case MERGESORT:
				timeStart = System.currentTimeMillis();
				mergeSort(array);
				timeEnd = System.currentTimeMillis();
				break;
				
			case BUBBLESORT:
				timeStart = System.currentTimeMillis();
				bubbleSort2(array);
				timeEnd = System.currentTimeMillis();
				break;
		}
		
		//Array nach Sortierung ausgeben, wenn groesse 100 nicht ueberschreitet
		if(array.length <= 100) {
			System.out.print("Aktuelle Sortierung:  ");
			printArray(array);
			System.out.println();
		}
		
		//Zeitdifferenz ausgeben
		System.out.println("Die Sortierung durch " + usedAlgorithm + " dauerte: " + (timeEnd - timeStart) + " ms / " + (timeEnd - timeStart)/1000f + " s");
		
		//Array auf korrekte Sortierung pruefen
		if(isSorted(array))	System.out.println("Sortierung: erfolgreich!");
		else				System.out.println("Sortierung: fehlgeschlagen!");
		
		System.out.print("\n");
		
		return timeEnd - timeStart;
	}
	
	/**
	 * Sorts the given int array using the insertionsort algorithm.
	 * Worst case time complexity: O(n^2)
	 * @param array
	 */
	private void insertionSort(int[] array) {
		int valueToInsert, j;
		
		for(int i = 1; i < array.length; i++) {				//Betrachte 1. Element als bereits sortiert -> Start bei Index 1
			valueToInsert = array[i];						//Sichern des aktuell einzufuegenden Wertes. Erlaubt "Schieben" der groesseren Werte weiter links
			j = i;											
			
			while(j > 0 && array[j-1] > valueToInsert) {	//Schieben aller Werte kleiner als valueToInsert nach rechts
				array[j] = array[j-1];
				j--;
			}
			
			array[j] = valueToInsert;						//Rueckschreiben des einzufuegenden Wertes an die nun freie Stelle
		}
	}
	
	/**
	 * Sorts the given int array using the mergesort algorithm.
	 * Worst case time complexity: O(n log n)
	 * @param array
	 */
	private void mergeSort(int[] array) {
		int[] temp = new int[array.length];
		mergeSort(array, temp, 0, array.length-1);
	}
	
	private void mergeSort(int[] array, int[] temp, final int left, final int right) {
		if(left < right) {									//Sobald linker und rechter Zeiger uebereinanderlaufen ist das Array sortiert
			int mid = left + (right - left) / 2;			//Index des mittleren Elementes berechnen
															//"left +", da Index unabhaengig des Teilfeldes im Array benoetigt ist
			
			mergeSort(array, temp, left, mid);				//Linken Teil sortieren
			mergeSort(array, temp, mid+1, right);			//Rechten Teil sortieren
			
			mergeSortMerge(array, temp, left, mid, right);	//Sortierte Teile zusammenfuegen
		}
	}
	
	private void mergeSortMerge(int[] array, int[] temp, final int left, final int mid, final int right) {
		for(int i = left; i <= right; i++)					//Kopiere alle benoetigten Elemente in das temporaere Hilfsarray
			temp[i] = array[i];
		
		int i = left, j = mid+1, k = left;					//Hilfsvariablen (Laufindex von l. und r. Teilfeld + gesamt-Laufvariable) definieren
		
		while(i <= mid && j <= right) {						
			if(temp[i] <= temp[j]) {						//Schreibe Wert von linkem Zeiger in Datenarray
				array[k] = temp[i];
				i++;
			} else {										//Schreibe Wert von rechtem Zeiger in Datenarray
				array[k] = temp[j];
				j++;
			}
			
			k++;
		}
		
		while(i <= mid) {									//Restliche Variablen der verbliebenen Seite kopieren
			array[k] = temp[i];
			k++;
			i++;
		}
	}
	
	/**
	 * Sorts the given int array using the bubblesort algorithm.
	 * @param array int array
	 */
	@SuppressWarnings("unused")
	private void bubbleSort(int[] array) {
		boolean exchanged = true; //Hilfsvariable
		
		//Sobald bei vorherigem Durchlauf kein Element mehr getauscht wurde -> fertig sortiert!
		while(exchanged == true) {
			
			exchanged = false; //Setze auf falese
			//Laufe ueber gesamtes Array
			for(int i = 0; i < array.length-1; i++) {
				//Tausche aktuelles Element mit Nachfolger, falls dieser groesser ist
				if(array[i] > array[i + 1]) {
					swap(array, i, i + 1);
					exchanged = true;
				}
			}
		}
	}
	
	private void bubbleSort2(int[] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = array.length - 1; j > i; j--) {
				//Tausche aktuelles Element mit Vorgaenger, falls dieser kleiner ist
				if(array[j-1] > array[j]) {
					swap(array, j, j - 1);
				}
			}
		}
		assert isSorted(array);
	}
	
	/**
	 * Creates a copy of the given int array.
	 * @param array int array
	 * @return Reference to a new array, holding the same values
	 */
	private int[] createCopy(int[] array) {
		int[] copy = new int[array.length];
		for(int i = 0; i < copy.length; i++)
			copy[i] = array[i];
		return copy;
	}
	
	/**
	 * Fills the referenced array with random int values between minValue(inclusive) and maxValue(inclusive).
	 * @param minValue lower limit
	 * @param maxValue upper limit
	 */
	private void initRandom(int[] array, final int minValue, final int maxValue) {
		for(int i = 0; i < array.length; i++)
			array[i] = randomInt(minValue, maxValue);
	}
	
	/**
	 * Checks whether the values of the given int array are sorted in ascending order.
	 * @param array a
	 * @return sorted in ascending order?
	 */
	private boolean isSorted(int[] array) {
		for(int i = 0; i < array.length-1; i++)
			if(array[i+1] < array[i]) return false;
		
		return true;
	}
	
	/**
	 * Swaps the elements at the given indices in the given int array.
	 * @param array int array
	 * @param index1 Index of the first element
	 * @param index2 Index of the second element
	 */
	private void swap(int[] array, final int i, final int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	/**
	 * Prints the array to the console.
	 */
	private void printArray(int[] array) {
		for(int i = 0; i < array.length-1; i++)		//Nach letztem Element soll kein Komma aus der Schleife stehen!
			System.out.print(array[i] + ", ");
			
		System.out.println(array[array.length-1]);	//Letztes Element manuell ausgeben (inkl. Zeilenumbruch)
	}
	
	/**
	 * Returns a random int value between min(inclusive) and max(inclusive).
	 * @param min lower limit
	 * @param max upper limit
	 * @return random int
	 */
	public static final int randomInt(final int min, final int max) {
		return min + random.nextInt((max - min) + 1);
	}
	
	/**
	 * Checks whether the specified string represents a positive int value.
	 * For that, each character of the string must be a number(0,...,9).
	 * @param s String which has to be checked
	 * @return int?
	 */
	public static boolean isPositiveInt(String s) {
		for(int i = 0; i < s.length(); i++)
			if(!isNumber(s.charAt(i)))
				return false;
		
		return true;
	}
	
	/**
	 * Performs a check on the given char element.
	 * Returns true if the argument is a number (0,1,2,3,4,5,6,7,8,9). Otherwise false.
	 * @param c char parameter
	 * @return Number?
	 */
	public static boolean isNumber(final char c) {
//		switch(c) {
//			case '0' : return true; //48
//			case '1' : return true;	//49
//			...						//...
//			case '8' : return true; //56
//			case '9' : return true;	//57
//			default : return false;
//		}
		return (c > 47) & (c < 58) ? true : false;
	}
	
}