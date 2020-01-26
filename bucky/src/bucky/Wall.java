package bucky;

import org.jbox2d.dynamics.World;

public class Wall extends Prop{

	public Wall(int x, int y, int delay, World world) {
		
		
		super(x, y, delay, world);
		
		body.getFixtureList().setSensor(false);
		addSprite("src/resources/wall.png");
			
	}
	
	
}
