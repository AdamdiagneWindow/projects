package bucky;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import java.awt.BorderLayout;



public class Board extends JPanel 
		implements Runnable{

	
	private final int width = 600;
	private final int height = 600;
	private final int delay = 25;
	private Thread animator;
	private int imageCount = 0;
	private int animationDelay = 0;
	private int animationCount = 0;

	private int currentX = 0, currentY = 0, presX = 0, presY = 0, dragX = 0, dragY = 0, blackHoleX = 0, blackHoleY = 0;

	private boolean drag = false;
	private boolean addingBlackHole = false;
	
    private JLabel label, label2;
    
    private JPanel displayPanel;
    private JPanel inputPanel;
    
    private JButton addBlackHole;
    
	
	private gravitationalField gravityField;
	
	private projectile cat;
	
	public Board( ) {
		
		initBoard();
	}
	
	private void initBoard() {
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		setLayout(new BorderLayout());
		
		
		gravityField = new gravitationalField(width, height);
		
		cat = new projectile(300, 300, 0, 0, 0, 0);
		
		displayPanel = new JPanel();
		displayPanel.setPreferredSize(new Dimension(width, 50));
		

		label = new JLabel(".................");
		label.setFont(new Font("Tahoma", Font.BOLD, 10));
		label.setBounds(10,10, 500, 20);  
		displayPanel.add(label);		
		
		label2 = new JLabel(".................");
		label2.setFont(new Font("Tahoma", Font.BOLD, 10));
		label2.setBounds(10,30, 500, 20);  
		displayPanel.add(label2);		
		
		inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(width, 20));
		inputPanel.setLayout(null);
		
		
		addBlackHole = new JButton();
		addBlackHole.setBounds(0, 0, 20, 20);
		
		addBlackHole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				addingBlackHole = true;
				
			}
			
		});
		
		inputPanel.add(addBlackHole);
		
		
		
	    this.add(displayPanel, BorderLayout.NORTH);
	    this.add(inputPanel, BorderLayout.SOUTH);
		
		HandlerClass handler = new HandlerClass();
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);

		
	}
	
	
	
	private class HandlerClass implements MouseListener, MouseMotionListener{
		public void mouseClicked(MouseEvent event) {
			
			
			currentX = event.getX();
			currentY = event.getY();
			label.setText("x = " + currentX + "     y = " + currentY);								
			
		    
		    if(addingBlackHole == false ) {
		    	
		    	/*
		    	x = currentX;
		    	y = currentY;
		    	*/
		    	
		    	cat.setPosition(currentX, currentY);
		    	cat.setVelocity(0, 0);
			    cat.setAcceleration(0, 0);
			
			    cat.setStationary(true);
			
			    repaint();	
		    }
		    
		    if (addingBlackHole == true) {
		    	
		    	blackHole b = new blackHole(event.getX(), event.getY(), 1.5);
		    	gravityField.addBlackHole(b);
		    	
		    	addingBlackHole = false;
		    
		    	
		    }
		    
	
			
		
		}
		
		public void mousePressed(MouseEvent event) {
			
			int x = cat.getX_Coord();
			int y = cat.getY_Coord();
			int w = cat.getWidth();
			int h = cat.getHeight();
			
			
			presX = event.getX();
			presY = event.getY();
			
			if (presX >= x - w && presX <= x + w && presY >= y - h && presY <= y + h) {
				drag = true;
			}
		}
		
		public void mouseReleased(MouseEvent event) {
			
			cat.setVelocity( 0.1 * (presX - dragX), 0.1 * (presY - dragY) );
			
			presX = 0;
			presY = 0;
			dragX = 0;
			dragY = 0;
			
			repaint();
			

			
			
			
			
			if(addingBlackHole == false) {
				cat.setStationary(false);
			}

			
			drag = false;
			

	
		}
		
		public void mouseEntered(MouseEvent event) {

		}
		
		public void mouseExited(MouseEvent event) {

		}
		
		public void mouseDragged(MouseEvent event) {
			
			if(drag == true) {
				dragX = event.getX();
				dragY = event.getY();
				/*
				x = event.getX();
				y = event.getY();*/
				
				cat.setPosition(event.getX(), event.getY());
				
				repaint();
			}
			

			
						
			
		}
		
		public void mouseMoved(MouseEvent event) {
			
			if(addingBlackHole == true) {
				
				blackHoleX = event.getX();
				blackHoleY = event.getY();
			
				repaint();				
				
			}

		}
		
		
	}
	
	
	
	
	
	
	@Override
	public void addNotify() {
		super.addNotify();
		
		animator = new Thread(this);
		animator.start();
		
	}
	

	
	
	
	
    private void doDrawing(Graphics g) {
    	
    	Image image = null ;
    	
    	if(cat.getStationary() == true) {
    		
    		image = cat.getStationarySprite(0);
    		
    	}
    	
    	if(cat.getStationary() == false) {
    		
    		
    			
    		image = cat.getFlyingSprite(imageCount);
    			
        	label2.setText(String.valueOf(cat.getMag_Vel()));
        	
        	int magVel = (int)cat.getMag_Vel();
        	if(magVel > 10) {
        		magVel = 10;
        	}
        	
    		if(animationCount >= animationDelay ) {
    			
    			imageCount++;
    			animationCount = 0;
    			animationDelay = 4 - (magVel/10)*4; 
    		}
    		
    			
    		if(imageCount == 9) {
    			imageCount = 0;
    		}
    			
    			
    		animationCount++;
    	
    		
    	}
    	
    	
    	
    	
    	
    	Graphics2D g2d = (Graphics2D) g;
    	
    	g2d.setPaint(new Color(150, 150, 150));
    	
        RenderingHints rh = new RenderingHints(
        		RenderingHints.KEY_ANTIALIASING,
        		RenderingHints.VALUE_ANTIALIAS_ON);
    	
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        
        g2d.setRenderingHints(rh);      	
                
        
        g2d.drawImage(image, cat.getX_Coord(), cat.getY_Coord(), this);
        
        
        for (blackHole b : gravityField.getBlackHoleList()) {
        	
            g2d.drawOval(b.getX_Coord(), b.getY_Coord(), 10, 10);
        	
        }
        
        if(addingBlackHole == true) {
        	
        	g2d.drawOval(blackHoleX, blackHoleY, 10, 10);
        	
        }
        
        
        if(dragX == 0 && dragY == 0) {
        	
        	dragX = presX;
        	dragY = presY;
        }
        
        g.drawLine(presX, presY, dragX, dragY);
        
        		
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	
    	super.paintComponent(g);
    	doDrawing(g);
    	
    }
    
    private void cycle() {
    	
    	int x = cat.getX_Coord();
    	int y = cat.getY_Coord();
    	
    	double ax = 0;
    	double ay = 0;
    	if(x < width && y < height && x > 0 && y > 0) {
    		ax = gravityField.getAcc_Vector(x,y).getAcc_H();
    		ay = gravityField.getAcc_Vector(x,y).getAcc_V();  		
    	}
    	
    	
    	if (cat.getStationary() == false) {
    		
    		cat.setAcceleration(ax, ay);
    		cat.accelerate();
    		cat.move();
    		
    	}
    	
    	
    	
    	
    	if(x > width) {
    		cat.setPosition(0, cat.getY_Coord());
    	}
    	
    	if(x < 0) {
    		cat.setPosition(width, cat.getY_Coord());
    	}
    	
    	if(y > height ) {
    		cat.setPosition(cat.getX_Coord(), 0);
    	}
    	
    	if(y < 0) {
    		cat.setPosition(cat.getX_Coord(), height);
    	}
    	
    	
   
    	
    }
    
    
    
    
    @Override
    public void run() {
    	
    	long beforeTime, timeDiff, sleep;
    	
    	beforeTime = System.currentTimeMillis();
    	
    	while (true) {
    		
    		cycle();
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
    		
    		
    		
    	}
    	
    	
    	
    }
    
    

 
}