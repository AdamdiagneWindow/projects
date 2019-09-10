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
	private int animationDelayFlying = 0;
	private int animationDelayStationary = 10;
	private int animationCount = 0;
	

	private int currentX = 0, currentY = 0, presX = 0, presY = 0, dragX = 0, dragY = 0, blackHoleX = 0, blackHoleY = 0;
	
	private double angle, tanAngle;

	private boolean drag = false;
	private boolean addingBlackHole = false;
	
    private JLabel label, label2;
    
    private JPanel displayPanel;
    private JPanel inputPanel;
    
    private JButton addBlackHole;
    
	
	private gravitationalField gravityField;
	
	private cat cat1;
	
	private star star1;
	
	public Board( ) {
		
		initBoard();
	}
	
	private void initBoard() {
		
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		setLayout(new BorderLayout());
		
		
		gravityField = new gravitationalField(width, height);
		
		cat1 = new cat(300, 300, 0, 0, 0, 0, 10);
		
		star1 = new star(100, 100, 10);

		

		
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
		    	
		    	cat1.setPosition(currentX, currentY);
		    	cat1.setVelocity(0, 0);
			    cat1.setAcceleration(0, 0);
			
			    cat1.setStationary(true);
			    imageCount = 0;
			
			    //repaint();	
		    }
		    
		    if (addingBlackHole == true) {
		    	
		    	blackHole b = new blackHole(event.getX(), event.getY(), 5);
		    	gravityField.addBlackHole(b);
		    	
		    	addingBlackHole = false;
		    
		    	
		    }
		    
	
			
		
		}
		
		public void mousePressed(MouseEvent event) {
			
			int x = cat1.getX_Coord();
			int y = cat1.getY_Coord();
			int w = cat1.getWidth();
			int h = cat1.getHeight();
			
			
			presX = event.getX();
			presY = event.getY();
			
			if (presX >= x - w && presX <= x + w && presY >= y - h && presY <= y + h) {
				drag = true;
			}
		}
		
		public void mouseReleased(MouseEvent event) {
			
			cat1.setVelocity( 0.1 * (presX - dragX), 0.1 * (presY - dragY) );
			
			presX = 0;
			presY = 0;
			dragX = 0;
			dragY = 0;
			
			//repaint();
			

			
			
			
			
			if(addingBlackHole == false) {
				cat1.setStationary(false);
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
				
				cat1.setPosition(event.getX(), event.getY());
				
				//repaint();
			}
			

			
						
			
		}
		
		public void mouseMoved(MouseEvent event) {
			
			if(addingBlackHole == true) {
				
				blackHoleX = event.getX();
				blackHoleY = event.getY();
			
				//repaint();				
				
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
    	Image propImage = null;
    	
    	propImage = star1.getNextSprite();
    	
    	if(cat1.getStationary() == true) {
    		
    		image = cat1.getNextSprite();
    		
    	}
    	
    	if(drag == true && dragX != 0 && dragY != 0 && presX != 0 && presY != 0 && presX != dragX) {
    		
    		tanAngle = ((double)dragY - (double)presY)/((double)dragX - (double)presX);
    		angle = Math.atan(tanAngle)*180.00/Math.PI;
    		//image = cat.getPulledSprite((int)(Math.round(angle/40)));
    		
    	}
    	
    	
    	if(cat1.getStationary() == false) {
    		
    		
    			
    		image = cat1.getFlyingSprite(imageCount);
    			
        	label2.setText(String.valueOf(cat1.getMag_Vel()));
        	
        	int magVel = (int)cat1.getMag_Vel();
        	if(magVel > 7) {
        		magVel = 7;
        	}
        	
    		if(animationCount >= animationDelayFlying ) {
    			
    			imageCount++;
    			animationCount = 0;
    			animationDelayFlying = 4 - (magVel/7)*4; 
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
                
        
        g2d.drawImage(image, cat1.getX_Coord(), cat1.getY_Coord(), this);
        
        g2d.drawImage(propImage, star1.getX_Coord(), star1.getY_Coord(), this);
        
        
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
    	
    	int x = cat1.getX_Coord();
    	int y = cat1.getY_Coord();
    	
    	double ax = 0;
    	double ay = 0;
    	if(x < width && y < height && x > 0 && y > 0) {
    		ax = gravityField.getAcc_Vector(x,y).getAcc_H();
    		ay = gravityField.getAcc_Vector(x,y).getAcc_V();  		
    	}
    	
    	
    	if (cat1.getStationary() == false) {
    		
    		cat1.setAcceleration(ax, ay);
    		cat1.accelerate();
    		cat1.move();
    		
    	}
    	
    	
    	
    	
    	if(x > width) {
    		cat1.setPosition(0, cat1.getY_Coord());
    	}
    	
    	if(x < 0) {
    		cat1.setPosition(width, cat1.getY_Coord());
    	}
    	
    	if(y > height ) {
    		cat1.setPosition(cat1.getX_Coord(), 0);
    	}
    	
    	if(y < 0) {
    		cat1.setPosition(cat1.getX_Coord(), height);
    	}
    	
    	//System.out.println(angle);
    	
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