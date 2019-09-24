package bucky;

public class goal extends prop{

	public goal(int x, int y, int delay) {
		
		addSprite("src/resources/goal.png");
		addSprite("src/resources/goal1.png");
		addSprite("src/resources/goal2.png");
		addSprite("src/resources/goal3.png");
		
		x_Coord = x;
		y_Coord = y;
		frameDelay = delay;		
		
	}
	
}
