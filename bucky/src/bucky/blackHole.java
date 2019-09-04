package bucky;

public class blackHole {
	
	private int x_Coord;
	private int y_Coord;
	private double mass;
	
	
	
	public blackHole(int x, int y, double m) {
		
		x_Coord = x;
		y_Coord = y;
		mass = m;
		
	}
	
	public int getX_Coord() {
		
		return x_Coord;
	}
	
	public int getY_Coord() {
		
		return y_Coord;
	}
	
	public double getMass() {
		
		return mass;	
	}
	
	public void updateProperties(int x, int y, double m) {
		
		x_Coord = x;
		y_Coord = y;
		mass = m;		
		
	}
	
	
	
}
