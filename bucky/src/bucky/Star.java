package bucky;

public class Star extends Prop{

	public Star(int x, int y, int delay){
		
		addSprite("src/resources/starNew.png");
		addSprite("src/resources/starNew1.png");
		addSprite("src/resources/starNew2.png");
		addSprite("src/resources/starNew3.png");
		
		x_Coord = x;
		y_Coord = y;
		frameDelay = delay;
		
	}
	
	
}