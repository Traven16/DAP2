package praktikum.blatt6.aufgabe1;

/**
 * Represents in interval
 * 
 * @author Lukas Potthast
 * @version 0.0.0.1
 * @time 18.05.2015 22:08
 */

public class Interval implements Comparable<Interval> {
	
	/* Attributes */
	private int start, end;
	
	/**
	 * Creates an interval object using the given satrt and end values.
	 * @param start Staring point of the interval
	 * @param end Ending point of the interval
	 */
	public Interval(final int start, final int end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Get the start point.
	 * @return The start point
	 */
	public int getStart() {
		return start;
	}

	/**
	 * Set the start point.
	 * @param start New start value
	 */
	public void setStart(final int start) {
		this.start = start;
	}

	/**
	 * Get the end point.
	 * @return The end point
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * Set the end point.
	 * @param start New end value
	 */
	public void setEnd(final int end) {
		this.end = end;
	}
	
	
	/* Overrides */
	
	@Override
	public String toString() {
		return "[" + start + ", " + end + "]";
	}
	
	@Override
	public int compareTo(Interval other) {
		if(end < other.getEnd())
			return -1;
		
		else if(end > other.getEnd())
			return 1;
		
		//Objects are equal
		return 0;
	}
	
}