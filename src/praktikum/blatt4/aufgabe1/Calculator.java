package praktikum.blatt4.aufgabe1;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

/**
 * Calculator - Class
 * 
 * @author Lukas Potthast
 * @version 0.0.1.5
 * @time 10.05.2014 14:50
 */

public final class Calculator {
	
	/* Attributes */
	private static Random random;
	
	static {
		random = new Random();
	}
	
	/**
	 * Berechnet die Fakultaet der uebergebenen Zahl
	 * @param factorial ZU berechnender Wert
	 * @return Fakultaet
	 */
	public static long calcFactorial(final int factorial) {
		return calcFactorial(factorial, factorial);
	}
	
	/**
	 * Interne Fkt. zur Berechnung der Fakultaet
	 */
	private static long calcFactorial(int factorial, int result) {
		result *= --factorial;
		if(factorial == 1)
			return result;
		return calcFactorial(factorial, result);
	}
	
	/**
	 * Wandelt ein Signed-Byte per Bit-Opertator zu einem Unsigned-Byte
	 * @param signedByte  Signed-Byte
	 * @return Unsigned-Byte
	 */
	public static int toUnsignedByte(final byte signedByte) {
		return signedByte & 0xFF;
	}
	
	/**
	 * Vertauscht die Byte-Reihenfolge eines 32bit Integer-Wertes
	 * @param bigEndian Eingangswert
	 * @return Integer (gedreht)
	 */
	public static int bigToLittleEndian(final int bigEndian) {  
		ByteBuffer buf = ByteBuffer.allocate(4);  
	    
		buf.order(ByteOrder.BIG_ENDIAN);  
		buf.putInt(bigEndian);  
		buf.order(ByteOrder.LITTLE_ENDIAN);  
		
	    return buf.getInt(0);  
	}
	
	/**
	 * Berechnung des "Satz des Pythagoras".
	 * Diese Berechnung gilt nur bei Dreiecken, die einen rechten Winkel aufweisen!
	 * @param valueA Ankathete
	 * @param valueB Gegenkathete
	 * @return valueC Hypotenuse
	 */
	public static final double calculatePythagoras(final double valueA, final double valueB) {
		return Math.sqrt(Math.pow(valueA, 2) + Math.pow(valueB, 2));
	}
	
	/**
	 * Errechnet, ob ein Punkt innerhalb eines Kreises liegt.
	 * @param point Zu ueberpruefender Punkt
	 * @param radius Radius des Kreises
	 * @return Wahrheitswert
	 */
	public static final boolean pointInCircle(final Point point, final float radius) {
		return calculatePythagoras(point.getX(), point.getY()) <= radius;
	}
	
	/**
	 * Errechnet, ob ein Punkt innerhalb eines Rechteckes liegt.
	 * @param point Zu ueberpruefender Punkt
	 * @param rectangle Rechteck
	 * @return Wahrheitswert
	 */
	public static final boolean pointInRectangle(final Point point, final Rectangle rectangle) {
		return point.getX() >= rectangle.getX() &&
			   point.getY() >= rectangle.getY() &&
			   point.getX() <= rectangle.getWidth() + rectangle.getX() &&
			   point.getY() <= rectangle.getHeight() + rectangle.getY();
	}
	
	/**
	 * Gibt eine zufaellige Farbe in einem {@link java.awt.Color} Objekt zurueck.
	 * @return Color-Objekt mit einzelnen RGB-Werten (jeweils: 0...255)
	 */
	public static final Color randomRGBColor() {
		return new Color(randomInt(0, 256),  //R
						 randomInt(0, 256),  //G
						 randomInt(0, 256)); //B
	}
	
	/**
	 * Gibt eine zufaellige Farbe in einem {@link de.lupoproductions.fluxengine.util.math.Vector3f} Objekt zurueck.
	 * @return Vector3f-Objekt mit einzelnen RGB-Werten (jeweils: 0...1)
	 */
//	public static final Vector3f randomRGBVector() {
//		return new Vector3f(random.nextFloat(),  //R
//							random.nextFloat(),  //G
//						 	random.nextFloat()); //B
//	}
	
	/**
	 * Gibt zufaellig true oder false zurueck.
	 * @return Wahrheitswert
	 */
	public static final boolean randomBoolean() {
		return random.nextBoolean();
	}
	
	/**
	 * Returns a random double int between min(inclusive) and max(inclusive).
	 * @param min lower limit
	 * @param max upper limit
	 * @return random int
	 */
	public static final int randomInt(final int min, final int max) {
		return min + random.nextInt((max - min) + 1);
	}
	
	/**
	 * Returns a random float value between min(inclusive) and max(inclusive).
	 * @param min lower limit
	 * @param max upper limit
	 * @return random float
	 */
	public static final float randomFloat(final float min, final float max) {
		return min + random.nextFloat() * (max - min);
	}
	
	/**
	 * Returns a random double value between min(inclusive) and max(inclusive).
	 * @param min lower limit
	 * @param max upper limit
	 * @return random double
	 */
	public static final double randomDouble(final double min, final double max) {
		return min + random.nextDouble() * (max - min);
	}
	
	/**
	 * Kuerzt die uebergebene Fließkommazahl auf drei Nachkommastellen
	 * @param value	Fließkommazahl
	 * @return 'value' in der Form 0.000
	 */
	public static final double shortenedToThreeDecimalPlaces(final double value) {
		return Math.floor(value * 1000.0d) / 1000.0d;
	}
	
	/**
	 * Kuerzt die uebergebene Fließkommazahl auf drei Nachkommastellen
	 * @param value	Fließkommazahl
	 * @return 'value' in der Form 0.000
	 */
	public static final float shortenedToThreeDecimalPlaces(final float value) {
		return (float)(Math.floor(value * 1000.0f) / 1000.0f);
	}
	
	/**
	 * Kuerzt die uebergebene Fließkommazahl auf zwei Nachkommastellen
	 * @param value	Fließkommazahl
	 * @return 'value' in der Form 0.00
	 */
	public static final double shortenedToTwoDecimalPlaces(final double value) {
		return Math.floor(value * 100.0d) / 100.0d;
	}
	
	/**
	 * Kuerzt die uebergebene Fließkommazahl auf zwei Nachkommastellen
	 * @param value	Fließkommazahl
	 * @return 'value' in der Form 0.00
	 */
	public static final float shortenedToTwoDecimalPlaces(final float value) {
		return (float)(Math.floor(value * 100.0f) / 100.0f);
	}
	
}