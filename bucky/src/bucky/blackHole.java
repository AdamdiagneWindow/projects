package bucky;

public class blackHole extends prop{

	protected double mass;
	
	public blackHole(int x, int y, double m, int delay) {
		
		x_Coord = x;
		y_Coord = y;
		mass = m;
		frameDelay = delay;
		
		addSprite("src/resources/blackHole.png");
		addSprite("src/resources/blackHole1.png");
		addSprite("src/resources/blackHole2.png");
		addSprite("src/resources/blackHole3.png");
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
