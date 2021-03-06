
/*
package bucky;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public abstract class ProjectileNew extends Prop{

	protected int x_Prev = 0, y_Prev = 0, x_2Prev = 0, y_2Prev = 0, x_3Prev = 0, y_3Prev = 0, x_4Prev = 0, y_4Prev = 0;
	protected double x_Vel, y_Vel, x_Acc, y_Acc;
	protected ImageRotate rotator;
	protected boolean stationary;
	
	public ProjectileNew() {
		
		sprites = new ArrayList<Image>();
		rotator = new ImageRotate();
		stationary = true;	
	}
	
	public ProjectileNew (int x, int y, double vx, double vy, double ax, double ay, int delay) {
		
		
		
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
		
		
		x_4Prev = x_3Prev;
		y_4Prev = y_3Prev;
		x_3Prev = x_2Prev;
		y_3Prev = y_2Prev;
		x_2Prev = x_Prev;
		y_2Prev = y_Prev;
		x_Prev = x_Coord;
		y_Prev = y_Coord;
		

		x_Coord += x_Vel;
		y_Coord += y_Vel;		
		
	}
	
	public void accelerate() {
		
		x_Vel += x_Acc;
		y_Vel += y_Acc;	
		
	}
	

	
	public void setVelocity(double vx, double vy) {
		
		if(Math.abs(vx) < 1) {
			
			if(vx > 0) {
				vx = Math.ceil(vx);
			}
			else if(vx < 0) {
				vx = Math.floor(vx);
			}
		}
		if(Math.abs(vy) < 1) {
			if(vy > 0) {
				vy = Math.ceil(vy);
			}
			else if(vy < 0) {
				vy = Math.floor(vy);
			}
			
		}
		
		
		
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
	
	public int getX_Prev(int i) {
		if(i == 0) {
			return x_Prev;
		}
		
		if(i == 1) {
			return x_2Prev;
		}
		
		if(i ==2) {
			return x_3Prev;
		}
		if(i == 3) {
			return y_4Prev;
		}
		else {
			return 0;
		}
		
	}
	
	public int getY_Prev(int i) {
		
		if(i == 0) {
			return y_Prev;
		}
		
		if(i == 1) {
			return y_2Prev;
		}
		if(i == 2) {
			return y_3Prev;
		}
		if(i == 3) {
			return y_4Prev;
		}
		else {
			return 0;
		}
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
	
	public void reset(int x, int y) {
		
		setPosition(x, y);
		setVelocity(0,0);
		setAcceleration(0,0);
		setStationary(true);
		resetPropAnimation();
		
	}
	
	
	
}*/


package bucky;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public abstract class ProjectileNew extends Prop{
	
	protected String flyingSpritesDir;
	protected List<Image> flyingSprites;
	protected int x_Prev = 0, y_Prev = 0, x_2Prev = 0, y_2Prev = 0, x_3Prev = 0, y_3Prev = 0, x_4Prev = 0, y_4Prev = 0;
	protected int rotationFrames = 9;
	protected double queuedX_Vel, queuedY_Vel, queuedX_Force, queuedY_Force;
	protected boolean doProcessVelocity, doProcessForce;
	protected ImageRotate rotator;
	protected boolean stationary;
	protected boolean allowReset;
	
	public ProjectileNew() {
		

	}
	
	public ProjectileNew (int x, int y, double vx, double vy,  int delay, World world) {
		
		System.out.println("new projectile");
		sprites = new ArrayList<Image>();
		doProcessVelocity = false;
		doProcessForce = false;
		doProcessPosition = false;
		
		m_world = world;
		
		bd = new BodyDef();                  //Initialize Body and Fixture		
		bd.position.set(x,y);
		bd.type = BodyType.DYNAMIC;
		body = m_world.createBody(bd);
		shape = new PolygonShape();
		shape.setAsBox((float)(width/2), (float)(height/2));
		fd = new FixtureDef();
		fd.shape = shape;                  
		fd.density = 0.5f;                   //Default Density. Change with setDensity function
		fd.restitution = 1;  //Default Restitution. Change with setRestitution function
	
		body.createFixture(fd);
		body.setUserData(this);	
				
		frameDelay = delay;
		frameDelayInit = delay;
		
		rotator = new ImageRotate();		
		stationary = true;	
		
		Vec2 initialVel = new Vec2((float)vx, (float)vy);
		body.setLinearVelocity(initialVel);
		
		
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
	
	public void animateVarDelay() {
		
    	int magVel = (int)getMag_Vel();
    	if(magVel > 120) {
    		magVel = 120;
    	}
    	if(count == frameDelay) {
    		count = 0;
    	}
		if(count == 0) {
			imageIndex++;
			count = 0;
			frameDelay = (int)(20 - ((double)magVel/120)*19);
			
		}
			
		if(imageIndex == flyingSprites.size()) {
			imageIndex = 0;
		}
		count++;		
		
	}
	
	public Image getFlyingSprite() {
		
		System.out.println(imageIndex);
		return flyingSprites.get(imageIndex);
	}
	
	public Image getNextFlyingSprite() {
		
		animateVarDelay();
		return getFlyingSprite();
	}
	
	public void setVelocity( double vx, double vy) {
		
		
		queuedX_Vel = vx;
		queuedY_Vel = vy;
		doProcessVelocity = true;
		
		//processVelocity();

	}
	
	
	public void setStationary(boolean state) {
		
		System.out.println("Setting stationary to: " + state);
		stationary = state;
	}
	
	public void setAllowReset(boolean state) {
		
		allowReset = state;
	}
	
	public void applyForce(double fx, double fy) {
		
		queuedX_Force = fx;
		queuedY_Force = fy;
		doProcessForce = true;
		
	}
	
	public int getX_Prev(int i) {
		if(i == 0) {
			return x_Prev;
		}
		
		if(i == 1) {
			return x_2Prev;
		}
		
		if(i ==2) {
			return x_3Prev;
		}
		if(i == 3) {
			return y_4Prev;
		}
		else {
			return 0;
		}
		
	}
	
	public int getY_Prev(int i) {
		
		if(i == 0) {
			return y_Prev;
		}
		
		if(i == 1) {
			return y_2Prev;
		}
		if(i == 2) {
			return y_3Prev;
		}
		if(i == 3) {
			return y_4Prev;
		}
		else {
			return 0;
		}
	}
	
	public double getX_Vel() {
	
		return (double)body.getLinearVelocity().x;
	}
	
	public double getY_Vel() {
		
		return (double)body.getLinearVelocity().y;
	}	
	
	public double getMag_Vel() {
		
		Vec2 vel = body.getLinearVelocity();
		return java.lang.Math.sqrt(vel.x*vel.x + vel.y*vel.y);
	}
	
	
	public boolean getStationary() {
		return stationary;
	}
	
	public boolean getAllowReset() {
		
		return allowReset;
	}
	
	public boolean getDoProcessPosition() {
		
		return doProcessPosition;
	}
	
	public void reset(int x, int y) {
		
		System.out.println("reset");
		setVelocity(0,0);
		setPosition(x, y);
		setStationary(true);
		resetPropAnimation();
		
	}
	
	public void processVelocity() {
		
		double vx = queuedX_Vel;
		double vy = queuedY_Vel; 
		
		//System.out.println("setting vel to: x: " + vx + " vy: " + vy);		
		
		if(Math.abs(vx) < 1) {
			
			if(vx > 0) {
				vx = Math.ceil(vx);
			}
			else if(vx < 0) {
				vx = Math.floor(vx);
			}
		}
		if(Math.abs(vy) < 1) {
			if(vy > 0) {
				vy = Math.ceil(vy);
			}
			else if(vy < 0) {
				vy = Math.floor(vy);
			}
			
		}
		

	    body.setLinearVelocity(new Vec2((float)vx, (float)vy));	
		
	}
	
	public void processForce() {
		
		body.applyForce(new Vec2((float)queuedX_Force, (float)queuedY_Force), body.getWorldCenter());
	}
	
	public void processOperations(){
		
		if(doProcessForce == true) {
			processForce();
			doProcessForce = false;
		}		
		
		if(doProcessPosition == true) {
			
			processPosition();
			doProcessPosition = false;
			
		}
		
		if(doProcessVelocity == true) {
			processVelocity();
			doProcessVelocity = false;
		}
			
	}
	
	
	
}
