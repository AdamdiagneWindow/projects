package bucky;

public class Goal extends Prop{

	public Goal(int x, int y, int delay) {
		
		addSprite("src/resources/goal.png");
		addSprite("src/resources/goal1.png");
		addSprite("src/resources/goal2.png");
		addSprite("src/resources/goal3.png");
		
		x_Coord = x;
		y_Coord = y;
		frameDelay = delay;		
		
	}
	
}
