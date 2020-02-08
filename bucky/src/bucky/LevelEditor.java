
/*package bucky;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import bucky.Level.HandlerClass;

public class LevelEditor extends Level{
	
	int currentX, currentY, blackHoleX = 0, blackHoleY = 0, goalX, goalY, starX, starY, satX, satY, wallX, wallY, pres2X, pres2Y, drag2X, drag2Y;
	
	private boolean  settingSatVel = false;
	private BooleanClass addingBlackHole = new BooleanClass() , addingGoal = new BooleanClass(), addingStar = new BooleanClass(), addingSatellite = new BooleanClass(), addingWall = new BooleanClass();
	
	private JPanel inputPanel;
	private JButton  saveLevel, clear, cancel;
	private JTextField inputLevel;
	
	String levString;
	
	public LevelEditor() {
		initLevel();
	}
	
	private void initLevel() {
		
		Vec2 gravity = new Vec2(0.0f, 0.0f);
		world = new World(gravity);	
		lev = 1;
		starInitX = new ArrayList<Integer>();
		starInitY = new ArrayList<Integer>();
		blackInitX = new ArrayList<Integer>();
		blackInitY = new ArrayList<Integer>();
		satInitX = new ArrayList<Integer>();
		satInitY = new ArrayList<Integer>();
		satInitXVel = new ArrayList<Double>();
		satInitYVel = new ArrayList<Double>();
		
		starList = new ArrayList<Prop>();
		wallList = new ArrayList<Prop>();
		satelliteList = new ArrayList<Satellite>();
		gravityField = new GravitationalField(width, height);		
		
		setProps();	   //Read props data from level file and initializes props
		setCanvasAppearance(); //Sets JPanel general appearance
		setDisplayPanel(); // Sets stat display panel at top of screen
		setInputPanel(); // Sets input buttons at button of panel
		setEventHandlers(); //Sets event handlers
		addElements();  //Adds panels to screen		
		
	}

	public class HandlerClass2 implements MouseListener, MouseMotionListener{
		public void mouseClicked(MouseEvent event) {
			
			long time = 0;
			currentX = event.getX();
			currentY = event.getY();
			label.setText("x = " + currentX + "     y = " + currentY);								
			
		    
		    if(addingBlackHole.getBool() == false && addingGoal.getBool() == false && addingStar.getBool() == false 
		    		&& addingWall.getBool() == false && addingSatellite.getBool() ==  false && settingSatVel == false) {
		    	
		    	cat1.reset(currentX, currentY);
				
		    }
		    
		    if (addingBlackHole.getBool() == true) {
		    	
		    	BlackHole b = new BlackHole(event.getX(), event.getY(), 5, 50);      // write black hole position
		    	gravityField.addBlackHole(b);
		    	
		    	addingBlackHole.setBool(false);
		    
		    	
		    }
		    
		    if (addingGoal.getBool() == true) {
		    	
		    	if(gol == null) {
		    		
		    		gol = new Goal(event.getX(), event.getY(), 50);
		    	}
		    	else {
		    		gol.setPosition(event.getX(), event.getY());		    		 // write goal position
		    	}
		    	
		    	addingGoal.setBool(false);
		    }
		    
		    if (addingStar.getBool() == true) {
		    	
		    	Star s = new Star(event.getX(), event.getY(), 50);
		    	starList.add(s);
		    	addingStar.setBool(false);
		    	
		    }
		    
		    if(addingWall.getBool() == true) {
		    	
		    	if(space[wallX][wallY] == false) {
		    		Wall w = new Wall(wallX, wallY, 10);
		    		wallList.add(w);
		    		space[wallX][wallY] = true;
		    		
		    	}

		    	
		    }
		    
		    
		    
		    if (addingSatellite.getBool() == true) {
		    	time = System.currentTimeMillis();;
		    	settingSatVel = true;
		    	pres2X = event.getX();
		    	pres2Y = event.getY();
		    	addingSatellite.setBool(false);
		    }
		    
		    
		    
		    else if (settingSatVel == true && System.currentTimeMillis() - time > 10) {
		    	Satellite s = new Satellite(pres2X, pres2Y, 0.1*(drag2X - pres2X), 0.1*(drag2Y - pres2Y),3, 50);
		    	satelliteList.add(s);
		    	settingSatVel = false;
		    	pres2X = 0;
		    	pres2Y = 0;
		    	drag2X = 0;
		    	drag2Y = 0;
		    	label.setText("xVel " + s.getX_Vel() + "yVel " + s.getY_Vel());
		    	
		    }
		    
		    

		}
		
		

		
		public void mousePressed(MouseEvent event) {
			
			presX = event.getX();
			presY = event.getY();
			
			Rectangle r = cat1.getBounds();
			if(r.contains(presX, presY)) {
				drag = true;
			}
			

		}
		
		public void mouseReleased(MouseEvent event) {
			
			
			if(cat1.getStationary() == true) {
				cat1.setVelocity(0.1*(presX - dragX), 0.1*(presY - dragY) );
			}
			
			presX = 0;
			presY = 0;
			dragX = 0;
			dragY = 0;
			
			//repaint();
					
			if(addingBlackHole.getBool() == false && addingGoal.getBool() == false && drag == true) {    //potential source of error
				cat1.setStationary(false);
				drag = false;
			}

	
		}
		
		public void mouseEntered(MouseEvent event) {

		}
		
		public void mouseExited(MouseEvent event) {

		}
		
		public void mouseDragged(MouseEvent event) {
			
			if(drag == true && cat1.getStationary() == true) {
				dragX = event.getX();
				dragY = event.getY();
		
				cat1.setPosition(event.getX(), event.getY());
				
				//repaint();
			}
			
			if(addingWall.getBool() == true) {
				wallX = Math.round(event.getX()/20) * 20;
				wallY = Math.round(event.getY()/20) * 20;	
		    	if(space[wallX][wallY] == false) {
		    		Wall w = new Wall(wallX, wallY, 10);
		    		wallList.add(w);
		    		space[wallX][wallY] = true;
		    		
		    	}

			}
			

			
						
			
		}
		
		public void mouseMoved(MouseEvent event) {
			
			if(addingSatellite.getBool() == true) {
				if(event.getX() < 20) {
					satX = 20;
					satY = event.getY();
				}
				else if(event.getX() > width - 20) {
					satX = width - 20;
					satY = event.getY();
				}
				else if(event.getY() < 40) {
					satX = event.getX();
					satY = 40;
				}
				else if(event.getY() > height - 40) {
					satX = event.getX();
					satY = height - 40;
				}
				
			}
			
			if(settingSatVel == true) {
				drag2X = event.getX();
				drag2Y = event.getY();
			}

			
			if(addingBlackHole.getBool() == true) {
				
				blackHoleX = event.getX();
				blackHoleY = event.getY();

			}
			
			if(addingGoal.getBool() == true) {
				
				goalX = event.getX();
			    goalY = event.getY();
				
			}
			
			if(addingStar.getBool() == true) {
				
				starX = event.getX();
				starY = event.getY();
			}
			
			if(addingWall.getBool() == true) {
				
				wallX = Math.round(event.getX()/20) * 20;
				wallY = Math.round(event.getY()/20) * 20;
				
			}
			

		}
		
		
	}
	
	   public void doDrawing(Graphics g) {
		    
	    	Image image = null ;
	    	
	    	Graphics2D g2d = (Graphics2D) g;
	    	
	    	g2d.setPaint(new Color(150, 150, 150));
	    	
	        RenderingHints rh = new RenderingHints(
	        		RenderingHints.KEY_ANTIALIASING,
	        		RenderingHints.VALUE_ANTIALIAS_ON);
	    	
	        rh.put(RenderingHints.KEY_RENDERING,
	                RenderingHints.VALUE_RENDER_QUALITY);
	        
	        g2d.setRenderingHints(rh);   
	  
	        
	        drawCat(image, g2d);
	        drawGoal(image, g2d);
	        drawSatellite(image, g2d);	     
	        drawFromObjectList(image, g2d, gravityField.getBlackHoleList());
	        drawFromObjectList(image, g2d, starList);	        
	        drawFromObjectList(image, g2d, wallList);	
	        
	        drawLine(g);
	        drawLine2(g);
	        drawAddingMarkers(image, g2d);
	        
	    }
	
    private void setInputPanel() {
    	
		inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(width, 20));
		inputPanel.setLayout(null);		
		setAdderButton(0, 0, addingBlackHole);
		setAdderButton(20, 0, addingGoal);
		setAdderButton(40, 0, addingStar);
		setAdderButton(60, 0, addingSatellite);
		setAdderButton(80,0, addingWall);
		setSaveLevelButton();
		setCancelButton();
		setClearButton();
		setInputLevelField();
		
    }
	
    public void addElements() {
    	
	    this.add(displayPanel, BorderLayout.NORTH);
	    this.add(inputPanel, BorderLayout.SOUTH);
    }    
    
    protected void setEventHandlers() {
    	
		HandlerClass2 handler = new HandlerClass2();
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
    }
    
    private void setAdderButton(int x, int y, final BooleanClass addingBoolean) {
    	
    	JButton addButton = new JButton();
    	addButton.setBounds(x, y, 20, 20);
    	addButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			
    			addingBoolean.setBool(true);	
    		}
    	});
    	inputPanel.add(addButton);
    }    
    
    private void setSaveLevelButton() {
    	
 		saveLevel = new JButton();
 		saveLevel.setBounds(width - 20, 0, 20, 20);
 		saveLevel.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				
                 try {
                 	System.out.println("level Written");
                	FileWriter fw = new FileWriter("src/resources/levelParameters/level" + levString + ".txt");
                 	BufferedWriter writeFileBuffer = new BufferedWriter(fw);
                 	
                 	List <Prop> blackHoleList = gravityField.getBlackHoleList();
                 	int maxListSize = starList.size();
                 	if(blackHoleList.size() > maxListSize) {
                 		maxListSize = blackHoleList.size();
                 	}
                 	if(satelliteList.size() > maxListSize) {
                 		maxListSize = satelliteList.size();
                 	}
                 	if(maxListSize == 0) {
                 		maxListSize = 1;
                 	}
                 	System.out.println(maxListSize);
                 	
                 	for(int i = 0; i < maxListSize; i++) {
                 		
                 		if(i == 0) {
                 			writeFileBuffer.write(cat1.getX_Coord() + " ");
                 		    writeFileBuffer.write(cat1.getY_Coord() + " ");		
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}
                  		if(i == 0) {
                 			writeFileBuffer.write(gol.getX_Coord() + " ");
                 			writeFileBuffer.write(gol.getY_Coord() + " ");		
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}               		
                 		
                 		if(starList.size() > i) {
                 			writeFileBuffer.write(starList.get(i).getX_Coord() + " ");
                 			writeFileBuffer.write(starList.get(i).getY_Coord() + " ");
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}
                 		
                 		if(blackHoleList.size() > i) {
                 			writeFileBuffer.write(blackHoleList.get(i).getX_Coord() + " ");
                 			writeFileBuffer.write(blackHoleList.get(i).getY_Coord() + " ");
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}
                 		
                 		if(satelliteList.size() > i) {
                 			writeFileBuffer.write(satelliteList.get(i).getX_Init() + " ");
                 			writeFileBuffer.write(satelliteList.get(i).getY_Init() + " ");
                 			System.out.println("satelliteSaved");
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}
                 		
                 		if(satelliteList.size() > i) {
                 			writeFileBuffer.write(satelliteList.get(i).getX_Vel_Init() + " ");
                 			writeFileBuffer.write(satelliteList.get(i).getY_Vel_Init() + " ");
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}
                 		



                 		writeFileBuffer.newLine();
 	
                 	}
                 	
                 	writeFileBuffer.close();
                 	
                 }
                 
                 catch (IOException io){
                 	io.printStackTrace();
                 }						
                 System.out.println("Level Saved");
 			}		
 		});  
 		inputPanel.add(saveLevel);
     }
    
    private void setCancelButton() {
    	
    	cancel = new JButton();
    	cancel.setBounds(width - 60, 0, 20, 20);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				addingBlackHole.setBool(false);
				addingStar.setBool(false);
				addingGoal.setBool(false);
				addingSatellite.setBool(false);
				addingWall.setBool(false);
	   
			}

		});	
		inputPanel.add(cancel);
    }
    
    private void setClearButton() {             //Clears all game props except for cat and goal
		clear = new JButton();
		clear.setBounds(width - 40, 0, 20, 20);
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				clearLevel();
	               System.out.println("Level Reset");
			}
		
			
			
		});	
		inputPanel.add(clear);
    }
    
    private void setInputLevelField() {
		inputLevel = new JTextField();
		inputLevel.setBounds(100, 0, 20,20);
		inputLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				levString = inputLevel.getText();
				System.out.println("level: " + levString);
			}

		});
		inputPanel.add(inputLevel);
    }

    private void drawAddingMarkers(Image image, Graphics2D g2d) {
    	
    	if(addingBlackHole.getBool() == true) {
    		g2d.drawOval(blackHoleX, blackHoleY, 10, 10);
    	}
    	
    	if(addingStar.getBool() == true) {
    		g2d.drawOval(starX, starY, 10, 10);
    	}
    	
        if(addingGoal.getBool() == true) {
        	
        	g2d.drawRect(goalX, goalY, 10, 10);
        }
        
        if(addingSatellite.getBool() == true) {
        	
        	g2d.drawRect(satX, satY, 10, 10);
        	
        }
        
        if(addingWall.getBool() == true) {
        	g2d.drawRect(wallX, wallY, 10, 10);
        }
    	
 
        
    }
    
    private void drawLine2(Graphics g) {
    	
        if(drag2X == 0 && drag2Y == 0) {
        	
        	drag2X = pres2X;
        	drag2Y = pres2Y;
        }
        
        g.drawLine(pres2X, pres2Y, drag2X, drag2Y);    	
    }    
 
    private void clearLevel() {
    	starList.clear();
    	wallList.clear();
    	satelliteList.clear();
    	gravityField.clearBlackHoles();
    	gravityField.resetPotentialMatrix();
    	space = null;
    	space = new boolean[width][height];
    
    }
    
    public void endLevel() {
    	cat1.reset(catInitX, catInitY);
	
    }
   
}*/


package bucky;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import bucky.Level.HandlerClass;

public class LevelEditor extends Level{
	
	int currentX, currentY, blackHoleX = 0, blackHoleY = 0, goalX, goalY, starX, starY, satX, satY, wallX, wallY, pres2X, pres2Y, drag2X, drag2Y;
	
	private boolean  settingSatVel = false;
	private BooleanClass addingBlackHole = new BooleanClass() , addingGoal = new BooleanClass(), addingStar = new BooleanClass(), addingSatellite = new BooleanClass(), addingWall = new BooleanClass();
	
	private JPanel inputPanel;
	private JButton  saveLevel, clear, cancel;
	private JTextField inputLevel;
	
	String levString;
	
	public LevelEditor() {
		initLevel();
	}
	
	private void initLevel() {
		
		Vec2 gravity = new Vec2(0.0f, 0.0f);
		myContactListenerInstance = new MyContactListener();
		world = new World(gravity);
		world.setContactListener(myContactListenerInstance);       //set contact listener to custom contact listene
		
		lev = 7;
		starInitX = new ArrayList<Integer>();
		starInitY = new ArrayList<Integer>();
		blackInitX = new ArrayList<Integer>();
		blackInitY = new ArrayList<Integer>();
		satInitX = new ArrayList<Integer>();
		satInitY = new ArrayList<Integer>();
		wallInitX = new ArrayList<Integer>();
		wallInitY = new ArrayList<Integer>();
		satInitXVel = new ArrayList<Double>();
		satInitYVel = new ArrayList<Double>();
		
		starList = new ArrayList<Prop>();
		wallList = new ArrayList<Prop>();
		satelliteList = new ArrayList<Satellite>();
		gravityField = new GravitationalField(width, height);		
		
		setProps();	   //Read props data from level file and initializes props
		setCanvasAppearance(); //Sets JPanel general appearance
		setDisplayPanel(); // Sets stat display panel at top of screen
		setInputPanel(); // Sets input buttons at bottom of panel
		setEventHandlers(); //Sets event handlers
		addElements();  //Adds panels to screen		
		
	}

	public class HandlerClass2 implements MouseListener, MouseMotionListener{
		public void mouseClicked(MouseEvent event) {
			
			long time = 0;
			currentX = event.getX();
			currentY = event.getY();
			timeLabel.setText("x = " + currentX + "     y = " + currentY);								
			
		    
		    if(addingBlackHole.getBool() == false && addingGoal.getBool() == false && addingStar.getBool() == false 
		    		&& addingWall.getBool() == false && addingSatellite.getBool() ==  false && settingSatVel == false) {
		    	
		    	cat1.reset(currentX, currentY);
				
		    }
		    
		    if (addingBlackHole.getBool() == true) {
		    	
		    	BlackHole b = new BlackHole(event.getX(), event.getY(), 2.5, 50, world);      // write black hole position
		    	gravityField.addBlackHole(b);
		    	
		    	addingBlackHole.setBool(false);
		    
		    	
		    }
		    
		    if (addingGoal.getBool() == true) {
		    	
		    	if(gol == null) {
		    		
		    		gol = new Goal(event.getX(), event.getY(), 50, world);
		    	}
		    	else {
		    		gol.setPosition(event.getX(), event.getY());		    		 // write goal position
		    	}
		    	
		    	addingGoal.setBool(false);
		    }
		    
		    if (addingStar.getBool() == true) {
		    	
		    	Star s = new Star(event.getX(), event.getY(), 50, world);
		    	starList.add(s);
		    	addingStar.setBool(false);
		    	
		    }
		    
		    if(addingWall.getBool() == true) {
		    	
		    	if(space[wallX][wallY] == false) {
		    		Wall w = new Wall(wallX, wallY, 10, world);
		    		wallList.add(w);
		    		space[wallX][wallY] = true;
		    		
		    	}

		    	
		    }
		    
		    
		    
		    if (addingSatellite.getBool() == true) {
		    	time = System.currentTimeMillis();;
		    	settingSatVel = true;
		    	pres2X = event.getX();
		    	pres2Y = event.getY();
		    	addingSatellite.setBool(false);
		    }
		    
		    
		    
		    else if (settingSatVel == true && System.currentTimeMillis() - time > 10) {
		    	Satellite s = new Satellite(pres2X, pres2Y, (drag2X - pres2X), (drag2Y - pres2Y),3, 50, world);
		    	satelliteList.add(s);
		    	settingSatVel = false;
		    	pres2X = 0;
		    	pres2Y = 0;
		    	drag2X = 0;
		    	drag2Y = 0;
		    	timeLabel.setText("xVel " + s.getX_Vel() + "yVel " + s.getY_Vel());
		    	
		    }
		    
		    

		}
		
		

		
		public void mousePressed(MouseEvent event) {
			
			presX = event.getX();
			presY = event.getY();
			
			Rectangle r = cat1.getBounds();
			if(r.contains(presX, presY)) {
				drag = true;
			}
			

		}
		
		public void mouseReleased(MouseEvent event) {
			
			
			if(cat1.getStationary() == true) {
				cat1.setVelocity((presX - dragX), (presY - dragY) );
			}
			
			presX = 0;
			presY = 0;
			dragX = 0;
			dragY = 0;
			
			//repaint();
					
			if(addingBlackHole.getBool() == false && addingGoal.getBool() == false && drag == true) {    //potential source of error
				cat1.setStationary(false);
				drag = false;
			}

	
		}
		
		public void mouseEntered(MouseEvent event) {

		}
		
		public void mouseExited(MouseEvent event) {

		}
		
		public void mouseDragged(MouseEvent event) {
			
			if(drag == true && cat1.getStationary() == true) {
				dragX = event.getX();
				dragY = event.getY();
		
				cat1.setPosition(event.getX(), event.getY());
				
				//repaint();
			}
			
			if(addingWall.getBool() == true) {
				wallX = Math.round(event.getX()/20) * 20;
				wallY = Math.round(event.getY()/20) * 20;	
		    	if(space[wallX][wallY] == false) {
		    		Wall w = new Wall(wallX, wallY, 10, world);
		    		wallList.add(w);
		    		space[wallX][wallY] = true;
		    		
		    	}

			}
			

			
						
			
		}
		
		public void mouseMoved(MouseEvent event) {
			
			if(addingSatellite.getBool() == true) {
				if(event.getX() < 20) {
					satX = 20;
					satY = event.getY();
				}
				else if(event.getX() > width - 20) {
					satX = width - 20;
					satY = event.getY();
				}
				else if(event.getY() < 40) {
					satX = event.getX();
					satY = 40;
				}
				else if(event.getY() > height - 40) {
					satX = event.getX();
					satY = height - 40;
				}
				
			}
			
			if(settingSatVel == true) {
				drag2X = event.getX();
				drag2Y = event.getY();
			}

			
			if(addingBlackHole.getBool() == true) {
				
				blackHoleX = event.getX();
				blackHoleY = event.getY();

			}
			
			if(addingGoal.getBool() == true) {
				
				goalX = event.getX();
			    goalY = event.getY();
				
			}
			
			if(addingStar.getBool() == true) {
				
				starX = event.getX();
				starY = event.getY();
			}
			
			if(addingWall.getBool() == true) {
				
				wallX = Math.round(event.getX()/20) * 20;
				wallY = Math.round(event.getY()/20) * 20;
				
			}
			

		}
		
		
	}
	
	   public void doDrawing(Graphics g) {
		    
	    	Image image = null ;
	    	
	    	Graphics2D g2d = (Graphics2D) g;
	    	
	    	g2d.setPaint(new Color(150, 150, 150));
	    	
	        RenderingHints rh = new RenderingHints(
	        		RenderingHints.KEY_ANTIALIASING,
	        		RenderingHints.VALUE_ANTIALIAS_ON);
	    	
	        rh.put(RenderingHints.KEY_RENDERING,
	                RenderingHints.VALUE_RENDER_QUALITY);
	        
	        g2d.setRenderingHints(rh);   
	  
	        
	        drawCat(image, g2d);
	        drawGoal(image, g2d);
	        drawSatellite(image, g2d);	     
	        drawFromObjectList(image, g2d, gravityField.getBlackHoleList());
	        drawFromObjectList(image, g2d, starList);	        
	        drawFromObjectList(image, g2d, wallList);	
	        
	        drawLine(g);
	        drawLine2(g);
	        drawAddingMarkers(image, g2d);
	        
	    }
	
    private void setInputPanel() {
    	
		inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(width, 20));
		inputPanel.setLayout(null);		
		setAdderButton(0, 0, addingBlackHole);
		setAdderButton(20, 0, addingGoal);
		setAdderButton(40, 0, addingStar);
		setAdderButton(60, 0, addingSatellite);
		setAdderButton(80,0, addingWall);
		setSaveLevelButton();
		setCancelButton();
		setClearButton();
		setInputLevelField();
		
    }
	
    public void addElements() {
    	
	    this.add(displayPanel, BorderLayout.NORTH);
	    this.add(inputPanel, BorderLayout.SOUTH);
    }    
    
    protected void setEventHandlers() {
    	
		HandlerClass2 handler = new HandlerClass2();
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
    }
    
    private void setAdderButton(int x, int y, final BooleanClass addingBoolean) {
    	
    	JButton addButton = new JButton();
    	addButton.setBounds(x, y, 20, 20);
    	addButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			
    			addingBoolean.setBool(true);	
    		}
    	});
    	inputPanel.add(addButton);
    }    
    
    private void setSaveLevelButton() {
    	
 		saveLevel = new JButton();
 		saveLevel.setBounds(width - 20, 0, 20, 20);
 		saveLevel.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				
                 try {
                 	System.out.println("level Written");
                	FileWriter fw = new FileWriter("src/resources/levelParameters/level" + levString + ".txt");
                 	BufferedWriter writeFileBuffer = new BufferedWriter(fw);
                 	
                 	List <Prop> blackHoleList = gravityField.getBlackHoleList();
                 	int maxListSize = starList.size();
                 	if(blackHoleList.size() > maxListSize) {
                 		maxListSize = blackHoleList.size();
                 	}
                 	if(satelliteList.size() > maxListSize) {
                 		maxListSize = satelliteList.size();
                 	}
                 	if(wallList.size() > maxListSize) {
                 		maxListSize = wallList.size();
                 	}
                 	if(maxListSize == 0) {
                 		maxListSize = 1;
                 	}
                 	System.out.println(maxListSize);
                 	
                 	for(int i = 0; i < maxListSize; i++) {
                 		
                 		if(i == 0) {
                 			writeFileBuffer.write(cat1.getX_Coord() + " ");
                 		    writeFileBuffer.write(cat1.getY_Coord() + " ");		
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}
                  		if(i == 0) {
                 			writeFileBuffer.write(gol.getX_Coord() + " ");
                 			writeFileBuffer.write(gol.getY_Coord() + " ");		
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}               		
                 		
                 		if(starList.size() > i) {
                 			writeFileBuffer.write(starList.get(i).getX_Coord() + " ");
                 			writeFileBuffer.write(starList.get(i).getY_Coord() + " ");
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}
                 		
                 		if(blackHoleList.size() > i) {
                 			writeFileBuffer.write(blackHoleList.get(i).getX_Coord() + " ");
                 			writeFileBuffer.write(blackHoleList.get(i).getY_Coord() + " ");
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}
                 		
                 		if(satelliteList.size() > i) {
                 			writeFileBuffer.write(satelliteList.get(i).getX_Init() + " ");
                 			writeFileBuffer.write(satelliteList.get(i).getY_Init() + " ");
                 			System.out.println("satelliteSaved");
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}
                 		
                 		if(satelliteList.size() > i) {
                 			writeFileBuffer.write(satelliteList.get(i).getX_Vel_Init() + " ");
                 			writeFileBuffer.write(satelliteList.get(i).getY_Vel_Init() + " ");
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}
                 		
                 		if(wallList.size() > i) {
                 			writeFileBuffer.write(wallList.get(i).getX_Coord() + " ");
                 			writeFileBuffer.write(wallList.get(i).getY_Coord() + " ");
                 		}
                 		else {
                 			writeFileBuffer.write("all " + "all ");
                 		}
                 		
                 		



                 		writeFileBuffer.newLine();
 	
                 	}
                 	
                 	writeFileBuffer.close();
                 	
                 }
                 
                 catch (IOException io){
                 	io.printStackTrace();
                 }						
                 System.out.println("Level Saved");
 			}		
 		});  
 		inputPanel.add(saveLevel);
     }
    
    private void setCancelButton() {
    	
    	cancel = new JButton();
    	cancel.setBounds(width - 60, 0, 20, 20);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				addingBlackHole.setBool(false);
				addingStar.setBool(false);
				addingGoal.setBool(false);
				addingSatellite.setBool(false);
				addingWall.setBool(false);
	   
			}

		});	
		inputPanel.add(cancel);
    }
    
    private void setClearButton() {             //Clears all game props except for cat and goal
		clear = new JButton();
		clear.setBounds(width - 40, 0, 20, 20);
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				clearLevel();
	               System.out.println("Level Reset");
			}
		
			
			
		});	
		inputPanel.add(clear);
    }
    
    private void setInputLevelField() {
		inputLevel = new JTextField();
		inputLevel.setBounds(100, 0, 20,20);
		inputLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				levString = inputLevel.getText();
				System.out.println("level: " + levString);
			}

		});
		inputPanel.add(inputLevel);
    }

    private void drawAddingMarkers(Image image, Graphics2D g2d) {
    	
    	if(addingBlackHole.getBool() == true) {
    		g2d.drawOval(blackHoleX, blackHoleY, 10, 10);
    	}
    	
    	if(addingStar.getBool() == true) {
    		g2d.drawOval(starX, starY, 10, 10);
    	}
    	
        if(addingGoal.getBool() == true) {
        	
        	g2d.drawRect(goalX, goalY, 10, 10);
        }
        
        if(addingSatellite.getBool() == true) {
        	
        	g2d.drawRect(satX, satY, 10, 10);
        	
        }
        
        if(addingWall.getBool() == true) {
        	g2d.drawRect(wallX, wallY, 10, 10);
        }
    	
 
        
    }
    
    private void drawLine2(Graphics g) {
    	
        if(drag2X == 0 && drag2Y == 0) {
        	
        	drag2X = pres2X;
        	drag2Y = pres2Y;
        }
        
        g.drawLine(pres2X, pres2Y, drag2X, drag2Y);    	
    }    
 
    private void clearLevel() {
 
    	clearPropList(starList);
    	clearPropList(wallList);
    	clearPropList(gravityField.getBlackHoleList());
    	clearSatelliteList(satelliteList);
    	
    	//starList.clear();
    	//wallList.clear();
    	//satelliteList.clear();
    	//gravityField.clearBlackHoles();
    	gravityField.resetPotentialMatrix();
    	space = null;
    	space = new boolean[width][height];
    
    }
    
    private void clearPropList(List<Prop> propList) {
    	
    	for(Prop p : propList) {
    		p.destroyBody();
    	}
    	propList.clear();
    	
    }
    
    private void clearSatelliteList(List<Satellite> satelliteList) {
    	
    	for(Prop s : satelliteList) {
    		s.destroyBody();
    	}
    	satelliteList.clear();
    	
    }

    
    public void endLevel() {
    	cat1.reset(catInitX, catInitY);
	
    }
   
}

