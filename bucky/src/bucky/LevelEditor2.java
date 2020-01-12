package bucky;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bucky.Level.HandlerClass;

import org.jbox2d.dynamics.World;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.common.Vec2;

public class LevelEditor2 extends Level{
	
	protected final int delay = 1;
	
	private World m_world;
	private int x, y;
	
	private float timeStep = 1.0f/60.0f;
	private int velocityIterations = 8;
	private int positionIterations = 3;
	
	private BodyDef bd, gbd;
	private Body body, groundBody;
	
	public LevelEditor2() {
		initLevel();
	}
	
	private void initLevel() {
			
		
		  Vec2 gravity = new Vec2(0.0f, 0.0f);
		  boolean doSleep = true;
		  m_world = new World(gravity);	
		  
		  gbd = new BodyDef();
		  gbd.position.set(-3.0f, 100f);
		  groundBody = m_world.createBody(gbd);
		  PolygonShape groundShape = new PolygonShape();
		  groundShape.setAsBox(50.0f, 10.0f);
		  groundBody.createFixture(groundShape, 0.0f);
		  
		  
		  bd = new BodyDef();
	      bd.position.set(-3.0f, 300f);
	      bd.type = BodyType.DYNAMIC;
	     // bd.fixedRotation = true;
	      //bd.allowSleep = false;
	      

	      
	      body = m_world.createBody(bd);
	      
	      PolygonShape shape = new PolygonShape();
	      shape.setAsBox(0.5f, 0.5f);
	      
	      FixtureDef fd = new FixtureDef();
	      fd.shape = shape;
	      fd.density = 20.0f;
	      fd.restitution = 1;
	      body.createFixture(fd);
	      
	      Vec2 initialVel = new Vec2(0f, -30f);
	      body.setLinearVelocity(initialVel);
	      
	      
	      setCanvasAppearance();
		
	}

	public class HandlerClass2 implements MouseListener, MouseMotionListener{
		public void mouseClicked(MouseEvent event) {
			
		    

		}
		
		

		
		public void mousePressed(MouseEvent event) {
			

			

		}
		
		public void mouseReleased(MouseEvent event) {
			
			

	
		}
		
		public void mouseEntered(MouseEvent event) {

		}
		
		public void mouseExited(MouseEvent event) {

		}
		
		public void mouseDragged(MouseEvent event) {
			

			

			
						
			
		}
		
		public void mouseMoved(MouseEvent event) {
			
			
			

		}
		
		
	}
	
    
    
    @Override
    public void run() {
    	
    	
    	long beforeTime, timeDiff, sleep;
    	
    	beforeTime = System.currentTimeMillis();
    	
    	while (exit != true) {
    	
    		repaint();
    		
    		timeDiff = System.currentTimeMillis() - beforeTime;
    		sleep = delay - timeDiff;
    		
    		if (sleep < 0) {
    			
    			sleep = 2;
    		}
    		
    		try {
    			Thread.sleep(sleep);
    			
    		}catch(InterruptedException e) {
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                
                JOptionPane.showMessageDialog(this, msg, "Error", 
                    JOptionPane.ERROR_MESSAGE);    			
    		}
    		
    		beforeTime = System.currentTimeMillis();
    		
    		m_world.step(timeStep, velocityIterations, positionIterations);
    		
    	}
    	
    	
    	
    	
    	
    }
	
   public void doDrawing(Graphics g) {
	   
	    Vec2 pos = body.getPosition();
	    Vec2 vel = body.getLinearVelocity();
	    x = (int)pos.x;
	    y = -1* (int)pos.y + height;
	    
	   System.out.println(java.lang.Math.sqrt(vel.x*vel.x + vel.y*vel.y));
	     
	    
    	Image image = null ;
    	
    	Graphics2D g2d = (Graphics2D) g;
    	
    	g2d.setPaint(new Color(150, 150, 150));
    	
        RenderingHints rh = new RenderingHints(
        		RenderingHints.KEY_ANTIALIASING,
        		RenderingHints.VALUE_ANTIALIAS_ON);
    	
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        
        g2d.setRenderingHints(rh); 		 
        
        g2d.drawOval(x,y,10, 10);

        
    }
	
	

    
	protected void setEventHandlers() {
		
		HandlerClass2 handler = new HandlerClass2();
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
	}
    
   

 

   
}
