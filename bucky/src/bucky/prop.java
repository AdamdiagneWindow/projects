package bucky;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.List;
import java.util.ArrayList;



public class prop {

	protected int x_Coord, y_Coord, width, height, imageIndex = 0, count = 0, frameDelay;
	protected List<Image> sprites;
	
	public prop() {
		
		sprites = new ArrayList<Image>();
		
	}
	
	public prop (int x, int y, int delay){
		
		x_Coord = x;
		y_Coord = y;
		frameDelay = delay;
		
	}
	
	public void addSprite(String directory) {
		
		Image im;
		ImageIcon icon = new ImageIcon(directory);
		im = icon.getImage();
		sprites.add(im);
		
		if(sprites.size() == 1) {
			width = im.getWidth(null);
			height = im.getHeight(null);
		}
		
	}
	
	
	public void animate() {
		
		if(count >= frameDelay) {
			imageIndex++;
			count = 0;
		}
			
		if(imageIndex == sprites.size()) {
			imageIndex = 0;
		}
		count++;
	}
	
	
	public void setPosition(int x, int y) {
		
		x_Coord = x;
		y_Coord = y;
		
	}
	
	public void setDelay(int delay) {
		
		frameDelay = delay;
		
	}
	
	public void resetPropAnimation(int delay) {
		
		imageIndex = 0;
		count = 0;
		frameDelay = delay;
		
		
	}
	
	public int getWidth() {
		
		return width;
		
	}
	
	public int getHeight() {
		
		return height;
		
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
	
	public int getX_Coord() {
		
		return x_Coord;
	}
	
	public int getY_Coord() {
		
		return y_Coord;
	}
	
	
	
	
	
	
}
