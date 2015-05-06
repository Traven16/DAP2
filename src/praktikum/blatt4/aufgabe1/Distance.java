package praktikum.blatt4.aufgabe1;

/**
 * Distance
 * 
 * @author Lukas Potthast, Hendrik Schmieding
 * @version 0.0.0.1
 * @time 05.05.2015 01:48
 */

public interface Distance {
	
	/**
	 * Computes the distance between the two given points.
	 * @param p1 Point A
	 * @param p2 Point B
	 * @return Distance
	 */
	public double distance(final Point p1, final Point p2);
	
}