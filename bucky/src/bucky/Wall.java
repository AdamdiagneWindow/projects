package bucky;

public class Wall extends Prop{

	public Wall(int x, int y, int delay) {
		
		addSprite("src/resources/wall.png");
		
		x_Coord = x;
		y_Coord = y;
		frameDelay = delay;		
	}
	
	
}
