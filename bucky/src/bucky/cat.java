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
		addRotationSprites(9, flyingSprites, "src/resources/catFlying.png" );
		
		addSprite("src/resources/catNew.png");
		addSprite("src/resources/catNew1.png");
		addSprite("src/resources/catNew2.png");
		addSprite("src/resources/catNew3.png");
	}
	
	public void animateVarDelay() {
		
    	int magVel = (int)getMag_Vel();
    	if(magVel > 7) {
    		magVel = 7;
    	}
		if(count >= frameDelay) {
			imageIndex++;
			count = 0;
			frameDelay = 4 - (magVel/7)*4;
		}
			
		if(imageIndex == sprites.size()) {
			imageIndex = 0;
		}
		count++;		
		
	}
	
	public Image getFlyingSprite() {
		
		return flyingSprites.get(imageIndex);
	}
	

	
	public Image getPulledSprite() {
		
		return pulledSprites.get(imageIndex);
	}	
	
	public Image getNextFlyingSprite() {
		
		animateVarDelay();
		return getFlyingSprite();
	}
	
	
}
