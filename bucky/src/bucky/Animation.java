package bucky;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Animation {
	
	private int  width = 20, height = 20, imageIndex = 0, count = 0, frameDelay,
				   x_Coord, y_Coord;
	private List<Image> sprites;
	private boolean endAnimation = false;
	
	public Animation(int x, int y, int delay) {
		
		x_Coord = x;
		y_Coord = y;
		frameDelay = delay;
		sprites = new ArrayList<Image>();
		
	}
	
	
	public void addSprite(String directory) {
		
		Image im;
		ImageIcon icon = new ImageIcon(directory);
		im = icon.getImage();
		sprites.add(im);
		
		
	}
	
	public void animate() {
		
		if(count >= frameDelay) {
			imageIndex++;
			count = 0;
		}
			
		if(imageIndex == sprites.size()) {
			imageIndex = 0;
			System.out.println("sizeReached");
		}
		count++;
	}
	
	public void setDelay(int delay) {
		
		frameDelay = delay;
		
	}
	
	public Image getSprite() {
		
		return sprites.get(imageIndex);
		
	}
	
	public Image getNextSprite() {
		
		animate();
		return getSprite();
		
	}
	
	public Image getSpecificSprite(int x) {
		
		return sprites.get(x);
		
	}
	
	public List<Image> getSpriteList(){
		
		return sprites;
	}
	
	public int getX_Coord() {
		
		return x_Coord;
	}
	
	public int getY_Coord() {
		
		return y_Coord;
	}
	
	public void drawAnimation(GameOverScreen screen ,Image image, Graphics2D g2d, 
								int counter, int minCount, int maxCount, int maxIndex) { 
		
		if(counter < minCount) {
			
			image = getSpecificSprite(0);
		}
		if(counter > maxCount || endAnimation == true) {
			
			image = getSpecificSprite(sprites.size()-1);
		}
		if(counter >= minCount && counter < maxCount && endAnimation == false) {
			
		    image = getNextSprite();

			if(imageIndex == sprites.size() - 1) {
				
				endAnimation = true;
			}
		}
		g2d.drawImage(image, x_Coord, y_Coord, screen);
	}
	
}
