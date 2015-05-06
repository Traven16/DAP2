package praktikum.blatt4.aufgabe1;

/**
 * Simplex
 * 
 * @author Lukas Potthast
 * @version 0.0.0.1
 * @time 05.05.2015 01:11
 */

public abstract class Simplex {
	
	/* Attributes */
	private Point[] points;
	
	/**
	 * Constructor
	 */
	public Simplex(Point... points) {
		this.points = points;
	}
	
	/**
	 * Calculates the perimeter of the actual structure.
	 * @return Perimeter
	 */
	public double perimeter() {
		EuclidDistance ed = new EuclidDistance();
		double perimeter = 0;
		
		//Berechnet die Summe der Distanzen zwischen allen Punkten. Jede moegliche Strecke wird nur 1 mal behandelt.
		for(int i = 0; i < points.length; i++) {
			for(int j = 1; j < points.length; j++) {
				if(i < j)
					perimeter += ed.distance(points[i], points[j]);
			}
		}
		
		//Gebe das Perimeter zurueck
		return perimeter;
	}
	
	public abstract boolean validate();
	
	/* Getter and Setter */
	
	public int getSize() {
		return points.length;
	}
	
	public Point[] getPoints() {
		return points;
	}
	
}