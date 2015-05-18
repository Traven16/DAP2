package praktikum.blatt6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Implements the scheduling algorithms.
 * 
 * @author Lukas Potthast
 * @version 0.0.0.1
 * @time 18.05.2015 22:17
 */

public class Program {
	
	/* Arguments */
	public static final byte LOADER_INTERPRET_AS_INTERVAL = 0;
	public static final byte LOADER_INTERPRET_AS_JOB = 1;
	
	/**
	 * Entrance point.
	 * @param args Program arguments
	 */
	public static void main(String[] args) {
		//Set startup arguments for testing purposes
		args = new String[2];
		args[0] = "lateness";
		args[1] = "res/blatt6/datenMittel2.zahlen";
		
		//Check for corret amount of program arguments
		if(args.length != 2) {
			System.err.println("Incorrect amount of startup arguments given!\nProgram terminates...");
		}
		
		//Create File object out of input string 
		File dataFile = null;
		try {
			dataFile = new File(args[1]);
		} catch(NullPointerException npe) {
			System.err.println("File creation failed. Path argument was null.\nProgram terminates...");
			return;
		}
		
		//Provide information of the created file object to the user
		System.out.println("Bearbeite Datei \"" + dataFile.getPath() + "\":\n");
		
		//Chech whether the intervalScheduling or the latenessScheduling should be used
		String algorithmDeclaration = args[0];
		
		//Process intervalScheduling request
		if(algorithmDeclaration.equals("Interval") || algorithmDeclaration.equals("interval")) {
			//Load the intervals into an array list
			ArrayList<Interval> intervals = loadData(LOADER_INTERPRET_AS_INTERVAL, dataFile);
			
			//Check wheter an error occured during the loading phase
			if(intervals == null) {
				System.err.println("Error during interval loading!\nProgram terminates...");
				return;
			}
			
			//Check whether any intervls were loaded
			if(intervals.size() == 0) {
				System.err.println("Zero intervals were properly loaded :D\nProgram terminates...");
				return;
			}
			
			// ----- Intervals loaded! -----
			
			//Sort intervals
			Collections.sort(intervals);
			
			//Print sorted intervals
			System.out.println("Sortiert:");
			printArrayList(intervals);
			System.out.println();
			
			//Calculate and print the scheduling of the loaded intervals
			ArrayList<Interval> scheduling = intervalScheduling(intervals);
			System.out.println("Berechnetes Intervallscheduling:");
			printArrayList(scheduling);
		}
		
		//Process latenessScheduling request
		else if(algorithmDeclaration.equals("Lateness") || algorithmDeclaration.equals("lateness")) {
			//Load the intervals into an array list
			ArrayList<Job> jobs = loadData(LOADER_INTERPRET_AS_JOB, dataFile);
			
			//Check wheter an error occured during the loading phase
			if(jobs == null) {
				System.err.println("Error during job loading!\nProgram terminates...");
				return;
			}
			
			//Check whether any jobs were loaded
			if(jobs.size() == 0) {
				System.err.println("Zero jobs were properly loaded :D\nProgram terminates...");
				return;
			}
			
			// ----- Jobs loaded! -----
			
			//Sort jobs
			Collections.sort(jobs);
			
			//Print sorted jobs
			System.out.println("Sortiert:");
			printArrayList(jobs);
			System.out.println();
			
			//Calculate and print the lateness scheduling of the loaded jobs
			int[] scheduling = latenessScheduling(jobs);
			System.out.println("Berechnetes Latenessscheduling:");
			printArray(scheduling, 0, scheduling.length - 2);
			System.out.println();
			
			System.out.println("Maximale Verspaetung: " + scheduling[scheduling.length - 1]);
		}
		
		System.out.println("---------------------------------------------------\n");
	}
	
	/**
	 * Implements the interval scheduling algorithm.<br>
	 * Returns null if a null reference was given or if the given array list held no elements.
	 * @param intervals A sorted array list of intervals to schedule.
	 * @return Scheduling results in a new ArrayList object holding references to the used intervals.
	 */
	public static ArrayList<Interval> intervalScheduling(final ArrayList<Interval> intervals) {
		//Create a new array list that will hold the scheduling results
		ArrayList<Interval> scheduling = new ArrayList<>();
		
		//Check if given array list was empty or null
		if(intervals == null || intervals.size() == 0) {
			System.err.println("intervalScheduling: Given array list was null or held 0 elements!");
			return null;
		}
		
		//Add the first interval to the results. Its guaranteed taht the first element of a properly sorted list ends first.
		scheduling.add(intervals.get(0));
		
		//Add every next interval that starts after or at the same time the previous interval that got added to the scheduling ended
		for(int i = 0; i < intervals.size() - 1; i++)
			if(intervals.get(i + 1).getStart() >= intervals.get(i).getEnd())
				scheduling.add(intervals.get(i + 1));
		
		//Return the processed scheduling
		return scheduling;
	}
	
	/**
	 * Implements the lateness scheduling algorithm.<br>
	 * Remember: Last element in the array represents the maximum delay!<br>
	 * Returns null if a null reference was given or if the given array list held no elements.
	 * @param jobs A sorted array list of jobs to schedule.
	 * @return Scheduling results in a new ArrayList object holding references to the used jobs.
	 */
	public static int[] latenessScheduling(final ArrayList<Job> jobs) {
		//Create a new array that will hold the scheduling results
		int[] scheduling = new int[jobs.size() + 1];
		
		//Create helper variables
		int totalDuration = 0, maxDelay = 0, delay = 0;
		
		//Perform the scheduling
		for(int i = 0; i < jobs.size(); i++) {
			scheduling[i] = totalDuration;
			totalDuration += jobs.get(i).getDuration();
			
			delay = totalDuration - jobs.get(i).getDeadline();
			if(delay > maxDelay)
				maxDelay = delay;
		}
		
		//Store the maximum delay in an additional array slot
		scheduling[scheduling.length - 1] = maxDelay;
		
		//Return the processed scheduling
		return scheduling;
	}
	
	/**
	 * Loads the interval data from the given File-object into an ArrayList<Interval> object.<br>
	 * Immediately returns null if an error occurs!
	 * @param interpretAs Use LOADER_INTERPRET_AS_* to specify the interpretation of the data.
	 * @param file The File-object to read from.
	 * @return ArrayList of intervals.
	 */
	@SuppressWarnings("unchecked")
	private static <T> ArrayList<T> loadData(final byte interpretAs, final File file) {
		//Abort if there is a wrong argument
		if(interpretAs != LOADER_INTERPRET_AS_INTERVAL && interpretAs != LOADER_INTERPRET_AS_JOB) {
			System.err.println("loadData: Illegal argument \"interpretAs\". Use LOADER_INTERPRET_AS_* to specify the interpretation of the data.");
			return null;
		}
		
		//Create the ArrayList object that gets returned after the file got read
		ArrayList<T> intervals = null, jobs = null;
		if(interpretAs == LOADER_INTERPRET_AS_INTERVAL)	intervals = new ArrayList<>();
		else											jobs = new ArrayList<>();
		int lineCount = 0;
		
		//Create a BufferedReder to read the text file (try-with-resources)
		try(BufferedReader intervalReader = new BufferedReader(new FileReader(file))) {
			
			String line;
			//Read line by line and add the data to an array list
			while((line = intervalReader.readLine()) != null) {
				
				//Split line by comma to seperate start and end point
				String[] splittedLine = line.split(",");
				
				//Check whether only two arguments were in the line. Abort when not.
				if(splittedLine.length != 2) {
					System.err.println("loadData: Error in line: \"" + line + "\"(" + lineCount + "). Too many arguments.");
					return null;
				}
				
				int start = 0, end = 0;
				//Try to convert string data to integer data
				try {
					start = Integer.valueOf(splittedLine[0]);
					end = Integer.valueOf(splittedLine[1]);
				} catch(NumberFormatException nfe) {
					System.err.println("loadData: A NumberFormatException occured. Seems that there is corrupted data in \"" + file.getPath() +  "\" in line \"" + line + "\"(" + lineCount + ")");
					return null;
				}
				
				//Add read data to an array list
				if(interpretAs == LOADER_INTERPRET_AS_INTERVAL)	intervals.add((T) new Interval(start, end));
				else											jobs.add((T) new Job(start, end));
				
				//Successfully processed the line -> increase line count
				lineCount++;
			}
		
		//Catch possible exceptions
		} catch(final FileNotFoundException fnfe) {
			System.err.println("loadData: Error: Given path does not corrspond to a valid file!");
			return null;
		} catch (IOException e) {
			System.err.println("loadData: An I/O error occured in line " + (lineCount + 1) + "!");
			return null;
		}
		
		//Print out read data
		System.out.println("Es wurden " + lineCount + " Zeilen mit folgendem Inhalt gelesen:");
		if(interpretAs == LOADER_INTERPRET_AS_INTERVAL)	printArrayList(intervals);
		else											printArrayList(jobs);
		System.out.println();
		
		//Return the read intervals in an array list
		if(interpretAs == LOADER_INTERPRET_AS_INTERVAL)	return intervals;
		else											return jobs;
	}
	
	/**
	 * Prints all elements seperated by commas in one line into the standard output strem.
	 * toString() method of T need to be overriden!
	 * @param arrayList ArrayList object whose elements get printed.
	 */
	private static <T> void printArrayList(final ArrayList<T> arrayList) {
		for(int i = 0; i < arrayList.size() - 1; i++) {
			System.out.print(arrayList.get(i) + ", ");
		}
		System.out.println(arrayList.get(arrayList.size() - 1));
	}
	
	/**
	 * Prints all int elements seperated by commas in one line into the standard output strem.
	 * @param array Array object whose elements get printed.
	 */
	private static void printArray(final int[] array, final int startIndex, final int endIndex) {
		for(int i = startIndex; i < endIndex; i++) {
			System.out.print(array[i] + ", ");
		}
		System.out.println(array[endIndex]);
	}
	
}