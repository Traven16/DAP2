package uebung.blatt5;

public class Aufgabe1 {
	
	public static void main(String[] args) {
		new Aufgabe1();
	}
	
	public Aufgabe1() {
		System.out.println("mult(6,7): " + mult(6, 7));
		System.out.println("mult(5,3): " + mult(5, 3));
	}
	
	private int mult(int n, int a) {
		if(n == 1)
			return a;
		
		return mult(n/2, a) + mult(n-n/2, a);
	}
	
}