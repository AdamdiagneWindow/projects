package bucky;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class cat extends projectileNew {
	
	private String flyingSpritesDir = "src/resources/catFlying.png", pulledSpritesDir = "src/resources/catPulled0.png";
	private List<Image>  pulledSprites, flyingSprites;
	private int rotationFrames = 9;
	
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
		frameDelayInit = delay;
		
 
		addRotationSprites(rotationFrames, pulledSprites, pulledSpritesDir );
		addRotationSprites(rotationFrames, flyingSprites, flyingSpritesDir);
		addSprite("src/resources/catBubble.png");
		addSprite("src/resources/catBubble1.png");
		addSprite("src/resources/catBubble2.png");
		addSprite("src/resources/catBubble3.png");
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
	

	
	public Image getPulledSprite(double angle) {
		
		int angleIndex = (int)(Math.round(angle/40));
		if(angleIndex == rotationFrames) {
			angleIndex = 0;
		}
		if(angleIndex < 0) {
			angleIndex = 0;
		}
		return pulledSprites.get(angleIndex);
	}	

	
	public Image getNextFlyingSprite() {
		
		animateVarDelay();
		return getFlyingSprite();
	}
	
	
}
