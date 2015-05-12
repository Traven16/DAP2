package praktikum.blatt4.aufgabe2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import praktikum.blatt4.aufgabe1.Calculator;
import praktikum.blatt4.aufgabe1.Point;

/**
 * ConvexHull
 * 
 * @author Lukas Potthast, Hendrik Schmieding
 * @version 0.0.0.2
 * @time 05.05.2015 22:12
 */

public class ConvexHull {
	
	/* Attributes */
	private JFrame frame;
	private JPanel panel;
	
	private Point[] points;
	private ArrayList<Point> convexHull;
	
	private int pointAmount = 60, pointAmountIncreaseFactor = 1;
	private int minWidth = -1000, maxWidth = 1000, minHeight = -1000, maxHeight = 1000;
	private final int pointSize = 4;
	
	/**
	 * Entrance point
	 * @param args Program arguments
	 */
	public static void main(String[] args) {
		new ConvexHull();
	}
	
	/**
	 * Constructor
	 */
	public ConvexHull() {
		frame = new JFrame("ConvexHull");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension((int)(1920/1.5f), (int)(1080/1.5f)));
		
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				//Fenster aufraeumen
				g.clearRect(0, 0, panel.getWidth(), panel.getHeight());
				
				//Punkte zeichnen
				if(points != null) {
					for(int i = 0; i < points.length; i++) {
						double pointPosX, pointPosY;
						
						if(points[i].getValueOfDimension(0) >= 0)	pointPosX = Math.abs(minWidth) + points[i].getValueOfDimension(0);
						else										pointPosX = Math.abs(minWidth - points[i].getValueOfDimension(0));
						if(points[i].getValueOfDimension(1) >= 0)	pointPosY = Math.abs(minHeight) + points[i].getValueOfDimension(1);
						else										pointPosY = Math.abs(minHeight - points[i].getValueOfDimension(1));
						
						g.fillOval((int)(getWidth() * (pointPosX / (Math.abs(maxWidth)+Math.abs(minWidth)))),
								   (int)(getHeight() * (pointPosY / (Math.abs(maxHeight)+Math.abs(minHeight)))),
								   pointSize, pointSize);
					}
				}
				
				//Graden zeichnen
				if(convexHull != null) {
					for(int i = 0; i < convexHull.size()-1; i++) {
						double pointPosX, pointPosY;
						
						if(convexHull.get(i).getValueOfDimension(0) >= 0)	pointPosX = Math.abs(minWidth) + convexHull.get(i).getValueOfDimension(0);
						else												pointPosX = Math.abs(minWidth - convexHull.get(i).getValueOfDimension(0));
						if(convexHull.get(i).getValueOfDimension(1) >= 0)	pointPosY = Math.abs(minHeight) + convexHull.get(i).getValueOfDimension(1);
						else												pointPosY = Math.abs(minHeight - convexHull.get(i).getValueOfDimension(1));
						
						double pointPosX2, pointPosY2;
						
						if(convexHull.get(i+1).getValueOfDimension(0) >= 0)	pointPosX2 = Math.abs(minWidth) + convexHull.get(i+1).getValueOfDimension(0);
						else												pointPosX2 = Math.abs(minWidth - convexHull.get(i+1).getValueOfDimension(0));
						if(convexHull.get(i+1).getValueOfDimension(1) >= 0)	pointPosY2 = Math.abs(minHeight) + convexHull.get(i+1).getValueOfDimension(1);
						else												pointPosY2 = Math.abs(minHeight - convexHull.get(i+1).getValueOfDimension(1));
						
						g.drawLine((int)(getWidth() * (pointPosX / (Math.abs(maxWidth)+Math.abs(minWidth)))),
								   (int)(getHeight() * (pointPosY / (Math.abs(maxHeight)+Math.abs(minHeight)))),
								   (int)(getWidth() * (pointPosX2 / (Math.abs(maxWidth)+Math.abs(minWidth)))),
								   (int)(getHeight() * (pointPosY2 / (Math.abs(maxHeight)+Math.abs(minHeight)))));
					}
				}
			}
		};
		
		panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3)
					pointAmountIncreaseFactor = 1;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1)
					initData();
				
				if(e.getButton() == MouseEvent.BUTTON3)
					pointAmountIncreaseFactor = 20;
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		panel.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				pointAmount += (e.getWheelRotation() * (-1d) * pointAmountIncreaseFactor);
				if(pointAmount < 0)
					pointAmount = 0;
				System.out.println("Point amount: " + pointAmount);
			}
		});
		
		frame.add(panel);
		
		frame.pack();
		frame.setVisible(true);
		
		render();
	}
	
	private void render() {
		while(frame != null) {
			panel.repaint();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Initializes the Point-object array
	 */
	private void initData() {
		points = new Point[pointAmount];
		
		for(int i = 0; i < points.length; i++)
			points[i] = new Point(Calculator.randomDouble(minWidth, maxWidth), Calculator.randomDouble(minHeight, maxHeight));
		
		convexHull = simpleConvex(points);
		
		System.out.println();
		System.out.println("Konvexe Huelle: " + convexHull.size());
		for(Point p : convexHull) {
			System.out.println(p);
		}
	}
	
	private ArrayList<Point> simpleConvex(Point[] points) {
		//Wenn nur ein Element in Liste enthalten ist, existiert keine konvexe Huelle
		if(points.length <= 1)
			return new ArrayList<Point>();
		
		//Finde am weitesten links stehenden Punnkt
		int pointWithLowestXValue = 0;
		
		//Finde Index des Punktes mit kleinstem x-Wert
		for(int i = 0; i < points.length; i++)
			if(points[i].getValueOfDimension(0) < points[pointWithLowestXValue].getValueOfDimension(0))
				pointWithLowestXValue = i;
		
		
		//Erzeuge Liste und fuege gefundenen Punkt ein
		ArrayList<Point> convexHull = new ArrayList<>();
		convexHull.add(points[pointWithLowestXValue]);
		
		
		//Solange Huelle noch nicht vollstaendig
		while(convexHull.size() == 1 || !convexHull.get(0).equals(convexHull.get(convexHull.size()-1))) {
			
			//Speichere Referenz zum letzten Huellenelement in last
			Point last = convexHull.get(convexHull.size()-1);
			
			//Suche naechsten Punkt, fuer welchen gilt: Alle anderen Punkte liegen rechts
			for(int i = 0; i < points.length; i++) {
				
				//Aktuell zu pruefender Punkt
				Point current = points[i];
				//Pruefe keinen Punkt mit sich selbst -> naechste Schleifeniteration ausfuehren
				if(current.equals(last))
					continue;
				
				//Hilfsflag: Wird auf false gesetzt, sobald ein Punkt links der Strecke (last -> current) liegt
				boolean allOtherRight = true;
				
				//Pruefe die Lage jedes Punktes in Relation zur Strecke: last -> current
				//Wenn alle Punkte rechts der Strecke liegen, so ist current das naechste Huellenelement
				for(int j = 0; j < points.length; j++) {
					Point actual = points[j];
					
					//Strecke: last -> actual
					double lastToActualX = actual.getValueOfDimension(0) - last.getValueOfDimension(0);
					double lastToActualY = actual.getValueOfDimension(1) - last.getValueOfDimension(1);
					
					//Senkrechte auf Strecke: last -> current
					double verticalX = (current.getValueOfDimension(1) - last.getValueOfDimension(1)) * -1.0d;	//Tausche Werte und invertiere einen
					double verticalY = current.getValueOfDimension(0) - last.getValueOfDimension(0);			//-> Senkrechte auf Ursprung
					
					double p = lastToActualX * verticalX + lastToActualY * verticalY;
					
					//p==0 -> Punkt liegt auf Strecke; p>0 -> Punkt liegt links von Strecke; p<0 -> Punkt liegt rechts von Strecke
					if(p > 0) {
						allOtherRight = false;
						//Sobald EIN Punkt links der Strecke liegt, haben die restlichen Punkte keinen weiteren Einfluss auf das Ergebnis der Schleife
						break;
					}
				}
				
				//Wenn alle Punkte rechts lagen:
				if(allOtherRight) {
					convexHull.add(current);
					last = current;
					break;
				}
			}
			
		}
		
		//Gebe Liste mit Punkten zurueck, welche die konvexe Huelle im Uhrzeigersinn repraesentieren
		//Erstes und letztes Element gleich! 
		return convexHull;
	}
	
}