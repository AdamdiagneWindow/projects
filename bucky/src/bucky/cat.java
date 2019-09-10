package bucky;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class cat extends projectileNew {

	private List<Image>  pulledSprites, flyingSprites;
	
	public cat (int x, int y, double vx, double vy, double ax, double ay, int delay) {
		
		
		pulledSprites = new ArrayList<Image>(); 
		flyingSprites = new ArrayList<Image>(); 
		
		x_Coord = x;
		y_Coord = y;
		
		x_Vel = vx;
		y_Vel = vy;
		
		x_Acc = ax;
		y_Acc = ay;
		
		frameDelay = delay;
		
 
		addRotationSprites(9, pulledSprites, "src/resources/catPulled45.png" );
		addRotationSprites(9, flyingSprites, "src/resources/catFlying1.png" );
		
		addSprite("src/resources/cat.png");
		addSprite("src/resources/cat1.png");
		addSprite("src/resources/cat2.png");
		addSprite("src/resources/cat3.png");
	}
	
	public Image getFlyingSprite(int x) {
		
		return flyingSprites.get(x);
	}
	

	
	public Image getPulledSprite(int x) {
		
		return pulledSprites.get(x);
	}	
	
}
