package bucky;


import java.awt.Image;


import javax.swing.ImageIcon;

import java.util.List;
import java.util.ArrayList;


public class projectile  {

	private int x_Coord, y_Coord;
	private int width, height;
	private double x_Vel, y_Vel, x_Acc, y_Acc;
	private List<Image> stationarySprites, pulledSprites, flyingSprites;
	private imageRotate rotator;
	
	
	private boolean stationary;
	
	public projectile (int x, int y, double vx, double vy, double ax, double ay)  {
		
		stationarySprites = new ArrayList<Image>();
		pulledSprites = new ArrayList<Image>(); 
		flyingSprites = new ArrayList<Image>(); 
		
		
		rotator = new imageRotate();
		
		loadImage();
		
		x_Coord = x;
		y_Coord = y;
		
		x_Vel = vx;
		y_Vel = vy;
		
		x_Acc = ax;
		y_Acc = ay;
		
		stationary = true;
		           
		
		
	}
	
	private void loadImage(){
		
		
		loadRotationSprites(9, flyingSprites, "src/resources/catFlying1.png" );
		
		loadRotationSprites(9, pulledSprites, "src/resources/catPulled45.png" );		
		


		Image im;
		ImageIcon icon = new ImageIcon("src/resources/catAmyTrans.png");
		im = icon.getImage();
		stationarySprites.add(im);
		
		width = im.getWidth(null);
		height = im.getHeight(null);
		
		icon = new ImageIcon("src/resources/catAmyTrans1.png");
		im = icon.getImage();
		stationarySprites.add(im);
		
		icon = new ImageIcon("src/resources/catAmyTrans2.png");
		im = icon.getImage();
		stationarySprites.add(im);
		
		icon = new ImageIcon("src/resources/catAmyTrans3.png");
		im = icon.getImage();
		stationarySprites.add(im);
		
	}
	
	public void loadRotationSprites(int numOfFrames, List<Image> spriteList, String spriteDirectory) {
		
		Image image;
		ImageIcon icon  = new ImageIcon(spriteDirectory);
		image = icon.getImage();
		
		for(int i = 1; i <= numOfFrames; i++) {
			
			Image rotated = rotator.rotateImageByDegrees(image, (360/numOfFrames)*i);
				
			spriteList.add(rotated);
			
		}		
		
	}
	
	public void move() {
		
		x_Coord += x_Vel;
		y_Coord += y_Vel;		
		
	}
	
	public void accelerate() {
		
		x_Vel += x_Acc;
		y_Vel += y_Acc;
		
		
	}
	
	public void setPosition(int x, int y) {
		
		x_Coord = x;
		y_Coord = y;
		
	}
	
	public void setVelocity(double vx, double vy) {
		
		x_Vel = vx;
		y_Vel = vy;		
		
	}
	
	public void setAcceleration(double ax, double ay) {
		
		x_Acc = ax;
		y_Acc = ay;
		
	}
	
	public void setStationary(boolean state) {
		
		stationary = state;
	}
	
	public Image getFlyingSprite(int x) {
		
		return flyingSprites.get(x);
	}
	
	public Image getStationarySprite(int x) {
		
		return stationarySprites.get(x);
	}
	
	public Image getPulledSprite(int x) {
		
		return pulledSprites.get(x);
	}
	
	
	public int getWidth() {
		
		return width;
	}
	
	public int getHeight() {
		
		return height;
		
	}
	
	
	
	public int getX_Coord() {
		
		return x_Coord;
	}
	
	public int getY_Coord() {
		
		return y_Coord;
	}
	
	public double getX_Vel() {
		
		return x_Vel;
	}
	
	public double getY_Vel() {
		
		return y_Vel;
	}	
	
	public double getMag_Vel() {
		
		return java.lang.Math.sqrt(x_Vel*x_Vel + y_Vel*y_Vel);
	}
	
	
	public boolean getStationary() {
		return stationary;
	}
	
	
	
	
}
