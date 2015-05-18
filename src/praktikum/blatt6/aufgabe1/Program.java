package praktikum.blatt6.aufgabe1;

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
	
	/**
	 * Entrance point.
	 * @param args Program arguments
	 */
	public static void main(String[] args) {
		//Set startup arguments for testing purposes
		args = new String[1];
		args[0] = "res/blatt6/datenBsp1.zahlen";
		
		//Create File object out of input string 
		File dataFile = null;
		try {
			dataFile = new File(args[0]);
		} catch(NullPointerException npe) {
			System.err.println("File creation failed. Path argument was null.\nProgram terminates...");
			return;
		}
		
		//Provide information of the created file object to the user
		System.out.println("Bearbeite Datei \"" + dataFile.getPath() + "\":\n");
		
		//Load the intervals into an array list
		ArrayList<Interval> intervals = loadIntervals(dataFile);
		
		//Check wheter an error occured during the loading phase
		if(intervals == null) {
			System.err.println("Error during interval loading!\nProgram terminates...");
			return;
		}
		
		//Check whether intervls were loaded
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
		System.out.println("---------------------------------------------------\n");
	}
	
	/**
	 * Implements the interval scheduling algorithm.<br>
	 * Returns null if a null reference was given or if the given array list held no elements.
	 * @param intervals A sorted array list of intervals to schedule.
	 * @return Scheduling results in a new ArrayList object holding references to the used intervals.
	 */
	public static ArrayList<Interval> intervalScheduling(final ArrayList<Interval> intervals) {
		//Create a new arra list that will hold the scheduling results
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
	 * Loads the interval data from the given File-object into an ArrayList<Interval> object.<br>
	 * Immediately returns null if an error occurs!
	 * @param file The File-object to read from.
	 * @return ArrayList of intervals.
	 */
	private static ArrayList<Interval> loadIntervals(final File file) {
		//Create the ArrayList object that gets returned after the file got read
		final ArrayList<Interval> intervals = new ArrayList<>();
		int lineCount = 0;
		
		//Create a BufferedReder to read the text file (try-with-resources)
		try(BufferedReader intervalReader = new BufferedReader(new FileReader(file))) {
			
			String line;
			//Read line by line and add interval to array list
			while((line = intervalReader.readLine()) != null) {
				
				//Split line by comma to seperate start and end point
				String[] splittedLine = line.split(",");
				
				//Check whether only two arguments were in the line. Abort when not.
				if(splittedLine.length != 2) {
					System.err.println("loadIntervals: Error in line: \"" + line + "\"(" + lineCount + "). Too many arguments.");
					return null;
				}
				
				int start = 0, end = 0;
				//Try to convert string data to integer data
				try {
					start = Integer.valueOf(splittedLine[0]);
					end = Integer.valueOf(splittedLine[1]);
				} catch(NumberFormatException nfe) {
					System.err.println("loadIntervals: A NumberFormatException occured. Seems that there is corrupted data in \"" + file.getPath() +  "\" in line \"" + line + "\"(" + lineCount + ")");
					return null;
				}
				
				//Add read interval data to array list
				intervals.add(new Interval(start, end));
				
				//Successfully processed the line -> increase line count
				lineCount++;
			}
		
		//Catch possible exceptions
		} catch(final FileNotFoundException fnfe) {
			System.err.println("loadIntervals: Error: Given path does not corrspond to a valid file!");
			return null;
		} catch (IOException e) {
			System.err.println("loadIntervals: An I/O error occured in line " + (lineCount + 1) + "!");
			return null;
		}
		
		//Print out read data
		System.out.println("Es wurden " + lineCount + " Zeilen mit folgendem Inhalt gelesen:");
		printArrayList(intervals);
		System.out.println();
		
		//Return the read intervals in an array list
		return intervals;
	}
	
	/**
	 * Prints all elements seperated by commas in one line into the standard output strem.
	 * toString() method of T need to be overriden!
	 * @param arrayList ArrayList object whose elements get printed .
	 */
	private static <T> void printArrayList(final ArrayList<T> arrayList) {
		for(int i = 0; i < arrayList.size() - 1; i++) {
			System.out.print(arrayList.get(i) + ", ");
		}
		System.out.println(arrayList.get(arrayList.size() - 1));
	}
	
}