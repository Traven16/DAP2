package praktikum.blatt4.aufgabe1;

/**
 * Point -DP-
 * 
 * @author Lukas Potthast, Hendrik Schmieding
 * @version 0.0.0.1
 * @time 05.05.2015 00:14
 */

public class Point {
	
	/* Attributes */
	private double[] data;
	
	/**
	 * Constructor
	 * @param dimension Dimension as R^d. Has to be > 0.
	 * @param values Array of axis-values. Needs amount of 'dimension' values. If no values are passed, all axis-values will get initialized with 0.0d.
	 */
	public Point(final int dimension, final double... values) {
		//Auf fehlerhafte Attribute pruefen
		if(dimension < 1 || (values.length > 0 & values.length != dimension))
			throw new IllegalArgumentException("\nPoint: Constructor: Dimension is less than 1 or given number of axis-values do not match dimension.");
		
		//Erzeuge Datenarray (repraesantation der Achsen)
		data = new double[dimension];
		
		//Wenn Werte uebergeben wurden (gewiss korrekte Laenge), dann
		if(values.length != 0)
			//weise den Eintraegen im Datenarray (Achse) den jeweils zugehoerigen Wert (aus values) zu
			data = values;
		
		printCreationSucceeded(dimension);
	}
	
	/**
	 * Constructor
	 * @param values Array of axis-values. Amount of values determines dimension. At least 1 value is required.
	 */
	public Point(final double... values) {
		//Auf fehlerhafte Attribute pruefen
		if(values.length < 1)
			throw new IllegalArgumentException("\nPoint: Constructor: Dimension is less than 1.");
		
		//Erzeuge Datenarray (repraesantation der Achsen)
		data = new double[values.length];
		
		//weise den Eintraegen im Datenarray (Achse) den jeweils zugehoerigen Wert (aus values) zu
		data = values;
		
		printCreationSucceeded(values.length);
	}
	
	private void printCreationSucceeded(final int dimension) {
		System.out.print("Point created: d(" + dimension + ") v(");
		for(int i = 0; i < data.length - 1; i++)
			System.out.print(data[i] + ", ");
		System.out.print(data[data.length-1] + ")\n");
	}
	
	public int getDimension() {
		return data.length;
	}
	
	public double getValueOfDimension(final int axis) {
		return data[axis];
	}
	
	/* Overrides */
	
	@Override
	public boolean equals(final Object obj) {
		//Uebergebenes Objekt muss vom Typ Point sein
		if(!(obj instanceof Point))
			return false;
		
		//Cast nach Point
		Point p = (Point)obj;
		
		//Beide Punkte muessen gleicher Dimension sein
		if(getDimension() != p.getDimension())
			return false;
		
		//Vergleiche einzeln Dimensionswerte
		boolean equal = true;
		for(int i = 0; i < getDimension(); i++)
			if(getValueOfDimension(i) != p.getValueOfDimension(i))
				equal = false;
		
		return equal;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Point: (");
		
		for(int i = 0; i < getDimension()-1; i++) {
			sb.append(data[i] + ", ");
		}
		
		sb.append(data[getDimension()-1] + ")\n");
		
		return sb.toString();
	}
	
}