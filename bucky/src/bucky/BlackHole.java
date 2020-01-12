/*package bucky;

public class BlackHole extends Prop{

	protected double mass;
	
	public BlackHole(int x, int y, double m, int delay) {
		
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
	
}*/

package bucky;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class BlackHole extends Prop{

	protected double mass;
	
	public BlackHole(int x, int y, double m, int delay, World world) {
		
		super(x, y, delay, world);
		
		mass = m;
		addSprite("src/resources/blackHole.png");
		addSprite("src/resources/blackHole1.png");
		addSprite("src/resources/blackHole2.png");
		addSprite("src/resources/blackHole3.png");
	}
	
	public double getMass() {
		
		return mass;
		
	}
	
	public void updateProperties(int x, int y, double m) {
		
		Vec2 position = new Vec2((float)x, (float)y);
		body.setTransform( position, 0);
		mass = m;		
		
	}	
	
}

