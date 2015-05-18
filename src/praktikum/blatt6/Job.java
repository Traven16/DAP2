package praktikum.blatt6;

/**
 * Represents a Job
 * 
 * @author Lukas Potthast
 * @version 0.0.0.1
 * @time 19.05.2015 00:56
 */

public class Job implements Comparable<Job> {
	
	/* Attributes */
	private int duration, deadline;
	
	/**
	 * Creates a Job object using the given duration and deadline values.
	 * @param duration Duration of the job.
	 * @param deadline Deadlin of the job.
	 */
	public Job(final int duration, final int deadline) {
		this.duration = duration;
		this.deadline = deadline;
	}
	
	/**
	 * Get the duration of the job.
	 * @return The duration
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Get the deadline of the job.
	 * @return The deadline
	 */
	public int getDeadline() {
		return deadline;
	}
	
	
	/* Overrides */
	
	@Override
	public String toString() {
		return "[" + duration + ", " + deadline + "]";
	}
	
	@Override
	public int compareTo(Job other) {
		if(deadline < other.getDeadline())
			return -1;
		
		else if(deadline > other.getDeadline())
			return 1;
		
		//Objects are equal
		return 0;
	}
	
}