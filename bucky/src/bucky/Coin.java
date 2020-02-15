package bucky;

import org.jbox2d.dynamics.World;

public class Coin extends Prop{

	public Coin(int x, int y, int delay, World world) {
		
		super(x, y, delay, world);
		
		addSprite("src/resources/coin.png");
		addSprite("src/resources/coin2.png");
		addSprite("src/resources/coin3.png");
		addSprite("src/resources/coin4.png");
		
	}
	
}
