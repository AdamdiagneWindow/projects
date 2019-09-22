package bucky;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class projectileNew extends prop{

	protected double x_Vel, y_Vel, x_Acc, y_Acc;
	protected imageRotate rotator;
	protected boolean stationary;
	
	public projectileNew() {
		
		sprites = new ArrayList<Image>();
		rotator = new imageRotate();
		stationary = true;	
	}
	
	public projectileNew (int x, int y, double vx, double vy, double ax, double ay, int delay) {
		
		x_Coord = x;
		y_Coord = y;
		
		x_Vel = vx;
		y_Vel = vy;
		
		x_Acc = ax;
		y_Acc = ay;
		
		frameDelay = delay;
		
	}
	
	public void addRotationSprites(int numOfFrames, List<Image> spriteList, String spriteDirectory) {
		
		Image image;
		ImageIcon icon  = new ImageIcon(spriteDirectory);
		image = icon.getImage();
		
		for(int i = 0; i < numOfFrames; i++) {
			
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
