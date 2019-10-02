package bucky;

import javax.swing.JOptionPane;

public class satellite extends projectileNew 
		implements Runnable{

	private int spawnTime;
	private int x_Init;
	private int y_Init;
	private double x_Vel_Init;
	private double y_Vel_Init;
	
	private int delay;
	private Thread timer;
	private boolean exit;
	
	public satellite (int x, int y, double xVel, double yVel, int sDelay, int delay) {
		
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

	}
	
	public Thread getTimer() {
		
		return timer;
	}
	
	
}
