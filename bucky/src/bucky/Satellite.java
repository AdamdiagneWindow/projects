/*package bucky;

import javax.swing.JOptionPane;

public class Satellite extends ProjectileNew 
		implements Runnable{

	private int spawnTime;
	private int x_Init;
	private int y_Init;
	private double x_Vel_Init;
	private double y_Vel_Init;
	
	private int delay;
	private Thread timer;
	private boolean exit;
	
	public Satellite (int x, int y, double xVel, double yVel, int sDelay, int delay) {
		
		x_Coord = x;
		y_Coord = y;
		x_Init = x;
		y_Init = y;
		x_Vel = xVel;
		y_Vel = yVel;
		x_Vel_Init = xVel;
		y_Vel_Init = yVel;
		
		spawnTime = sDelay;
		frameDelay = delay;
		stationary = false;
		timer = new Thread(this);
	}
	
	
    @Override
    public void run() {

    	long beforeTime;
    	beforeTime = System.currentTimeMillis();
    	while (exit != true) {
    		
    		
    		if(System.currentTimeMillis() - beforeTime > 1000) {
    			
    			setPosition(x_Init, y_Init);
    			setVelocity(x_Vel_Init, y_Vel_Init);
    			setAcceleration(0,0);
    			resetPropAnimation();
    			exit = true;
    					
    		}
    		
    		  
    	}
    	exit = false;
    	
    	
    	
    }
    
    public int getX_Init() {
    	
    	return x_Init;
    }
    
    public int getY_Init() {
    	
    	return y_Init;
    }
    
    public double getX_Vel_Init() {
    	
    	
    	return x_Vel_Init;
    }
    
    public double getY_Vel_Init() {
    	
    	return y_Vel_Init;
    }
	
	public void respawn() {
		timer = new Thread(this);
		timer.start();
		System.out.println("Satellite respawned");

	}
	
	public Thread getTimer() {
		
		return timer;
	}
	
	
}*/


package bucky;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import org.jbox2d.dynamics.World;

public class Satellite extends ProjectileNew 
		implements Runnable{

	private int spawnTime;
	private int x_Init;
	private int y_Init;
	private double x_Vel_Init;
	private double y_Vel_Init;
	
	private int delay;
	private Thread timer;
	private boolean exit;
	
	public Satellite (int x, int y, double xVel, double yVel, int sDelay, int delay, World world) {
		
		super(x, y, xVel, yVel, delay, world);
		
		flyingSpritesDir = "src/resources/Penguin.png";
		flyingSprites = new ArrayList<Image>();
		
		addRotationSprites(rotationFrames, flyingSprites, flyingSpritesDir);
		
		
		x_Init = x;
		y_Init = y;
		x_Vel_Init = xVel;
		y_Vel_Init = yVel;
		
		spawnTime = sDelay;
		stationary = false;
		timer = new Thread(this);
	}
	
	
    @Override
    public void run() {

    	long beforeTime;
    	beforeTime = System.currentTimeMillis();
    	while (exit != true) {
    		
    		if(System.currentTimeMillis() - beforeTime > 1000) {
    			
    			
    			setPosition(x_Init, y_Init);
    			setVelocity(x_Vel_Init, y_Vel_Init);
    			setStationary(false);
    			resetPropAnimation();
    			exit = true;
    					
    		}
    		
    		  
    	}
    	exit = false;
    	
    	
    }
    
    
    public int getX_Init() {
    	
    	return x_Init;
    }
    
    public int getY_Init() {
    	
    	return y_Init;
    }
    
    public double getX_Vel_Init() {
    	
    	
    	return x_Vel_Init;
    }
    
    public double getY_Vel_Init() {
    	
    	return y_Vel_Init;
    }
	
	public void respawn() {
		timer = new Thread(this);
		timer.start();
		setStationary(true);
		setPosition(getX_Coord() + 800, getY_Coord() + 800);
		//System.out.println("satellite respawned..");

	}
	
	public Thread getTimer() {
		
		return timer;
	}
	
	
}

