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
import java.io.*;
import java.util.*;
import java.util.List;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;





public class level extends JPanel 
		implements Runnable{


	private final int width = 800;
	private final int height = 800;
	private final int delay = 25;
	private Thread animator;

	
	private int currentX = 0, currentY = 0, presX = 0, presY = 0, dragX = 0, dragY = 0, blackHoleX = 0, blackHoleY = 0, goalX, goalY;
	private int catInitX, catInitY, goalInitX, goalInitY, catNum, starNum, blackNum, goalNum;
	private List<Integer> starInitX, starInitY, blackInitX, blackInitY;
	
	private double angle, tanAngle;

	private boolean drag = false, flipCat = false;
	private boolean addingBlackHole = false;
	private boolean addingGoal = false;
	private boolean exit = false;
	private boolean levelFinished;
	
	
    private JLabel label;
    
    private JPanel displayPanel;
    private JPanel inputPanel;
    
    private JButton addBlackHole;
    private JButton addGoal;
    
	
	private gravitationalField gravityField;
	private cat cat1, catCont;
	private List<star> starList;
	private goal gol;
	private int lev;
	
	public level(int level) {
		initLevel(level);
	}
	
	public level(int level, cat caT) {
		catCont = caT;
		initLevel(level);
	}
	
	
	private void initLevel(int level) {
		
		System.out.println("init");
		System.out.println(catInitX);
	    lev = level;
	    levelFinished = false;
	    exit = false;
		starInitX = new ArrayList<Integer>();
		starInitY = new ArrayList<Integer>();
		blackInitX = new ArrayList<Integer>();
		blackInitY = new ArrayList<Integer>();
		
		starList = new ArrayList<star>();
		gravityField = new gravitationalField(width, height);		
		
		setProps();	   //Read props data from level file and initializes props
		setCanvasAppearance(); //Sets JPanel general appearance
		setDisplayPanel(); // Sets stat display panel at top of screen
		//setInputPanel(); // Sets input buttons at button of panel
		setEventHandlers(); //Sets event handlers
		addElements();  //Adds panels to screen


		
	}
	
	
	
	private class HandlerClass implements MouseListener, MouseMotionListener{
		public void mouseClicked(MouseEvent event) {
			
		
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
			
			if(cat1.getStationary() == true) {
				cat1.setVelocity( 0.1 * (presX - dragX), 0.1 * (presY - dragY) );
			}

			presX = 0;
			presY = 0;
			dragX = 0;
			dragY = 0;
			
			if(drag == true) {
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
			}
			

			
						
			
		}
		
		public void mouseMoved(MouseEvent event) {
			

		}
		
		
	}
	
	
	private void setProps() {
		System.out.println("setting props");
		String dir = "src/resources/levelParameters/level" + lev + ".txt";
		try (FileReader reader = new FileReader(dir);
				BufferedReader br = new BufferedReader(reader)){
			readPropData(br);
			initProps();
		}
		catch(IOException e) {
    		e.printStackTrace();
		}
		
		
	}
	
	public void startTimer() {
		animator.start();
	}
	
	
	@Override
	public void addNotify() {
		super.addNotify();
		
		animator = new Thread(this);
	
		
	}
	

	
	
	
	
    private void doDrawing(Graphics g) {
    
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
        drawStar(image, g2d);
        drawBlackHole(image, g2d);
        drawGoal(image, g2d);
        drawLine(g);
        
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
    	
    	if(gol != null && cat1.getX_Coord() < gol.getX_Coord() + 20 && cat1.getX_Coord() > gol.getX_Coord() - 20 
    			&& cat1.getY_Coord() < gol.getY_Coord() + 20 && cat1.getY_Coord() > gol.getY_Coord() - 20) {
    		
    		
    		if(levelFinished == false) {
    			resetCat(cat1.getX_Coord(), cat1.getY_Coord());
    			endLevel();
    		}
			//System.out.println(f1.getString());
    		//setVisible(false);
    		
			
    	}
    	
    	
    	if(x > width || x < 0 || y > height || y < 0) {
    		System.out.println("out of bounds");
    		System.out.println("x " + catInitX + "y " + catInitY);
    		resetCat(catInitX, catInitY);

    	}
    
    	
    }
    
    
    
    
    @Override
    public void run() {
    	
    	
    	long beforeTime, timeDiff, sleep;
    	
    	beforeTime = System.currentTimeMillis();
    	
    	while (exit != true) {
    		/*
    		if(breakLoop == true) {
    			System.out.println("brokeOutOfLevel");
    			break;

    		}*/
    		System.out.println("before change: " + catInitX);
    		cycle();
    		
    		repaint();
    		
    		timeDiff = System.currentTimeMillis() - beforeTime;
    		sleep = delay - timeDiff;
    		
    		if (sleep < 0) {
    			
    			sleep = 2;
    		}
    		
    		try {
    			Thread.sleep(sleep);
    			System.out.println("after change: " + catInitX);
    		}catch(InterruptedException e) {
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                
                JOptionPane.showMessageDialog(this, msg, "Error", 
                    JOptionPane.ERROR_MESSAGE);    			
    		}
    		
    		beforeTime = System.currentTimeMillis();

    	}
    	
    	
    	
    }
    
    private int readStringAsInt(int integer, String string) {
    	int newInt = 0;
    	if(string.contentEquals("all")) {	
    	    	return integer;

    	    	
    	}
    	else {
    		    newInt = Integer.parseInt(string);	
    	    	return newInt;
    	}

    	
    }
    
    private void addStringToIntList(List<Integer> intList, String string) {
    	
    	if(string.contentEquals("all")) {
	    		
    	}
    	else {
    		intList.add(Integer.parseInt(string));
    	}
    	
    	
    }
    
    private void readPropData(BufferedReader br) {
    	

    	try {
    		String line;
    		while ((line = br.readLine()) != null) {
    			String[] parts = line.split(" ");
    			System.out.println("catInitBeforeSet " + catInitX);
    			catInitX = readStringAsInt(catInitX, parts[0]);
    			catInitY = readStringAsInt(catInitY, parts[1]);
    			System.out.println("catInitSet");
    			addStringToIntList(starInitX, parts[2]);
    			addStringToIntList(starInitY, parts[3]);
    			addStringToIntList(blackInitX, parts[4]);
    			addStringToIntList(blackInitY, parts[5]);  
    			goalInitX = readStringAsInt(goalInitX, parts[6]);
    			goalInitY = readStringAsInt(goalInitY, parts[7]);
    			

            
    		}	    		
    	} catch(IOException e) {
    		e.printStackTrace();
    	}

    	
    }
    
    private void initProps() {
    	if(catInitX != 0 && catInitY != 0 && lev == 1) {
    		cat1 = new cat(catInitX, catInitY, 0, 0, 0, 0, 10);
    	}
    	
    	if(catInitX != 0 && catInitY != 0 && lev > 1) {
    		cat1 = catCont;
    		cat1.setPosition(catInitX, catInitY);
    	}
		
		
		for(int i = 0; i < starInitX.size(); i++) {
			
			star s = new star(starInitX.get(i), starInitY.get(i), 10);
			starList.add(s);
			
		}
		
		for(int i = 0; i < blackInitX.size(); i++) {
			
	    	blackHole b = new blackHole(blackInitX.get(i), blackInitY.get(i), 5, 10);
	    	gravityField.addBlackHole(b);
			
		}
		if(goalInitX != 0 && goalInitY != 0) {
			gol = new goal(goalInitX, goalInitY, 10);	
		}

		//scan = new Scanner(new File("src/resources/levelParameters/level1.txt"));
    }
    
    private void setDisplayPanel() {
    	
		displayPanel = new JPanel();
		displayPanel.setPreferredSize(new Dimension(width, 20));
		

		label = new JLabel(".................");
		label.setFont(new Font("Tahoma", Font.BOLD, 10));
		label.setBounds(10,10, 500, 20);  
		displayPanel.add(label);
    }
    
    
    private void addElements() {
    	
	    this.add(displayPanel, BorderLayout.NORTH);
    }
    
    private void setEventHandlers() {
    	
		HandlerClass handler = new HandlerClass();
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
    }
    
    private void setCanvasAppearance() {
    	
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(width, height));    //setCanvasAppearance
		setSize(width, height);
		setLayout(new BorderLayout());
    }
    
    
    private void resetCat(int x, int y) {
    	cat1.setPosition(x, y);       // write cat position
    	cat1.setVelocity(0, 0);
	    cat1.setAcceleration(0, 0);
	
	    cat1.setStationary(true);
	    cat1.resetPropAnimation(10);   	
    	
    }
    
    private void calculateDragAngle() {
    	
		tanAngle = ((double)dragY - (double)presY)/((double)dragX - (double)presX);
		angle = Math.atan(tanAngle)*180.00/Math.PI;
		
		if(presX < dragX && presY < dragY) {
			angle = angle + 90;
		}
		
		if(presX > dragX && presY > dragY) {
			angle = angle + 270;
		}
		
		if(presX > dragX && presY < dragY) {
			angle = angle + 270;
		}
			
		if(presX < dragX && presY > dragY) {
			angle = angle + 90;
		}
		
		if(angle < 180) {
			flipCat = false;
		}
		if(angle > 180) {
			flipCat = true;
			angle = 180 - (angle - 180);
		}

    	
    }
    
    private void drawCat(Image image, Graphics2D g2d) {
    	
    	
    	if(cat1.getStationary() == true) {
    		
    		image = cat1.getNextSprite();
    		
    	}
    	
    	if(drag == true) {
    		 
    		calculateDragAngle();
    		image = cat1.getPulledSprite(angle);
    		
    	}
    	
    	
    	if(cat1.getStationary() == false) {
    		
    		image = cat1.getNextFlyingSprite();
    		
    	}
    	
    	if(flipCat == true) {
    		g2d.drawImage(image, cat1.getX_Coord() + image.getWidth(null)/2, cat1.getY_Coord() - image.getHeight(null)/2, -image.getWidth(null), image.getHeight(null), this);
    	}
    	else {
    		g2d.drawImage(image, cat1.getX_Coord(), cat1.getY_Coord(), this);
    	}
    	
    }
    
    private void drawStar(Image image, Graphics2D g2d) {
        for (star s : starList) {
        	image = s.getNextSprite();
            g2d.drawImage(image, s.getX_Coord(), s.getY_Coord(), this);
        	
        }
    }
    
    private void drawBlackHole(Image image, Graphics2D g2d) {
        for (blackHole b : gravityField.getBlackHoleList()) {
        	
        	image = b.getNextSprite();
            g2d.drawImage(image, b.getX_Coord(), b.getY_Coord(), this);
        	
        }
        
    }
 
    private void drawGoal(Image image, Graphics2D g2d) {
    	
        if(gol != null) {
        	image = gol.getNextSprite();
        	g2d.drawImage(image, gol.getX_Coord(), gol.getY_Coord(), this);        	
        }
        
    	
    }
    
    private void drawLine(Graphics g) {
    	
        if(dragX == 0 && dragY == 0) {
        	
        	dragX = presX;
        	dragY = presY;
        }
        
        g.drawLine(presX, presY, dragX, dragY);    	
    }
    
    public Thread getAnimator() {
    	
    	return animator;
    }
    
    private void endLevel() {
    	levelFinished = true;
    	exit = true;
        System.out.println("I should be called only once");
		Donut2 f1 = (Donut2) SwingUtilities.windowForComponent(this);
		f1.addLevel(lev + 1, cat1);
		f1.addLevelTitle(lev + 1);
		exit = true;
		this.setVisible(false);
		f1.getLevelTitle().setVisible(true);
		f1.getLevelTitle().startTimer();
		System.out.println(lev);
	
    }
    
}