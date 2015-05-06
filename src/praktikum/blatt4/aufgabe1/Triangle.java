package praktikum.blatt4.aufgabe1;

/**
 * Triangle
 * 
 * @author Lukas Potthast, Hendrik Schmieding
 * @version 0.0.0.1
 * @time 05.05.2015 01:34
 */

public class Triangle extends Simplex {
	
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
		System.out.println("Triangle created:");
	}
	
	/**
	 * Checks whether the current construct is a triangle.
	 */
	@Override
	public boolean validate() {
		//Pruefe, ob 3 Punkte definiert sind (Dreieck).
		if(getSize() != 3)
			return false;
		
		//Wenn ja, prruefe ob alle die Dimension 2 aufweisen
		for(int i = 0; i < getSize(); i++)
			if(getPoints()[i].getDimension() != 2)
				return false;
		
		//hier: 3 Punkte der Dimension 2 vorhanden ->
		return true;
	}
	
}