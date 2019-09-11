package bucky;

public class massObject extends prop{

	protected double mass;
	
	public massObject(int x, int y, double m) {
		
		x_Coord = x;
		y_Coord = y;
		mass = m;
		
	}
	
	public void updateProperties(int x, int y, double m) {
		
		x_Coord = x;
		y_Coord = y;
		mass = m;		
		
	}	
	
}
