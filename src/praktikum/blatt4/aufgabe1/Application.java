package praktikum.blatt4.aufgabe1;

/**
 * Application
 * 
 * @author Lukas Potthast, Hendrik Schmieding
 * @version 0.0.0.1
 * @time 05.05.2015 01:57
 */

public class Application {
	
	/**
	 * Entrance point
	 * @param args Program arguments
	 */
	public static void main(String[] args) {
		//----- Parameter ausgeben -----
		System.out.print("Given parameters: (");
		
		if(args.length != 0) {
			for(int i = 0; i < args.length-1; i++)
				System.out.print("\"" + args[i] + "\", ");
			
			System.out.print("\"" + args[args.length-1] + "\"");
		}
		System.out.println(")\n");
		
		//Programmargumente vorhanden
		if(args.length == 6) {
			System.out.println("");
			//Parse Parameter
			double[] values = new double[6];
			int counter = 0;
			try {
				for(counter = 0; counter < 6; counter++)
					values[counter] = Double.valueOf(args[counter]);
			} catch(NumberFormatException nfe) {
				System.err.println("Application: main: Parameter " + (counter+1) + " was not a number!");
				nfe.printStackTrace();
				return;
			}
			
			//Dreieck erstellen
			Triangle t = new Triangle(new Point(values[0], values[1]), new Point(values[2], values[3]), new Point(values[4], values[5]));
			System.out.println("Perimeter: " + Calculator.shortenedToThreeDecimalPlaces(t.perimeter()));
			
		//Keine Argumente uebergeben
		} else if (args.length == 0) {
			
			//Dreieck erstellen
			Triangle t = new Triangle(new Point(Calculator.shortenedToThreeDecimalPlaces(Calculator.randomDouble(-1000, 1000)), Calculator.shortenedToThreeDecimalPlaces(Calculator.randomDouble(-1000, 1000))),
									  new Point(Calculator.shortenedToThreeDecimalPlaces(Calculator.randomDouble(-1000, 1000)), Calculator.shortenedToThreeDecimalPlaces(Calculator.randomDouble(-1000, 1000))),
									  new Point(Calculator.shortenedToThreeDecimalPlaces(Calculator.randomDouble(-1000, 1000)), Calculator.shortenedToThreeDecimalPlaces(Calculator.randomDouble(-1000, 1000))));
			System.out.println("Perimeter: " + Calculator.shortenedToThreeDecimalPlaces(t.perimeter()));
			
		//Falsche Anzahl an Argumenten
		} else
			System.err.println("Application: main: Unacceptable amount of parameters given! 0 or 6 possible.");
	}
		
}