package praktikum.blatt4.aufgabe1;

public class EuclidDistance implements Distance {
	
	/**
	 * Computes the euclidean distance between the two given points.
	 * @param p1 Point A
	 * @param p2 Point B
	 * @return Euclidean distance
	 */
	@Override
	public double distance(Point p1, Point p2) {
		//Pruefe auf Dimensionsgleichheit
		if(p1.getDimension() != p2.getDimension())
			throw new IllegalArgumentException("EuclidDistance: distance(Point, Point): Given point-dimensions dont match!");
		
		//Lege Hilfsvariable zur Speicherung der summierten Quadrate an
		double sum = 0;
		
		//Quadriere einzelne Dimensionen und summiere diese auf
		for(int i = 0; i < p1.getDimension(); i++)
			sum += Math.pow(p1.getValueOfDimension(i) - p2.getValueOfDimension(i), 2);
		
		//Gebe die Wurzel der Summe, die aktuelle Dinstanz, zurueck
		return Math.sqrt(sum);
	}
	
}