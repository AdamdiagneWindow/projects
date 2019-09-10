package bucky;

public class star extends prop{

	public star(int x, int y, int delay){
		
		addSprite("src/resources/star.png");
		addSprite("src/resources/star1.png");
		addSprite("src/resources/star2.png");
		addSprite("src/resources/star3.png");
		
		x_Coord = x;
		y_Coord = y;
		frameDelay = delay;
		
	}
	
	
}
