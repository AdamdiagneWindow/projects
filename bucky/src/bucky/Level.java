package bucky;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;




public class Level extends JPanel 
		implements Runnable{


	protected final int width = 800;
	protected final int height = 800;
	private final int delay = 25;
	protected Thread animator;

	
	protected int  presX = 0, presY = 0, dragX = 0, dragY = 0;
	protected int catInitX, catInitY, goalInitX, goalInitY;
	
	protected List<Integer> starInitX, starInitY, blackInitX, blackInitY, satInitX, satInitY;
	protected List<Double> satInitXVel, satInitYVel;
	
	private double angle, tanAngle;

	protected boolean drag = false, flipCat = false;
	
	private boolean exit = false;

    protected JLabel label;
    protected JPanel displayPanel;
	
	protected GravitationalField gravityField;
	protected Cat cat1, catCont;
	protected List<Prop> starList;
	protected List<Satellite> satelliteList;
	protected Goal gol;
	protected int lev;

	
	public Level() {
		
	}
	
	public Level(int level) {
		initLevel(level);
	}
	
	public Level(int level, Cat caT) {
		catCont = caT;
		initLevel(level);
	}

	private void initLevel(int level) {
		
	    lev = level;
	    exit = false;
		starInitX = new ArrayList<Integer>();
		starInitY = new ArrayList<Integer>();
		blackInitX = new ArrayList<Integer>();
		blackInitY = new ArrayList<Integer>();
		satInitX = new ArrayList<Integer>();
		satInitY = new ArrayList<Integer>();
		satInitXVel = new ArrayList<Double>();
		satInitYVel = new ArrayList<Double>();
		
		starList = new ArrayList<Prop>();
		satelliteList = new ArrayList<Satellite>();
		gravityField = new GravitationalField(width, height);
		
		setProps();	   //Read props data from level file and initializes props
		setCanvasAppearance(); //Sets JPanel general appearance
		setDisplayPanel(); // Sets stat display panel at top of screen
		//setInputPanel(); // Sets input buttons at button of panel
		setEventHandlers(); //Sets event handlers
		addElements();  //Adds panels to screen
		
		System.out.println(satInitX + " " + satInitY + " " + satInitXVel + " " + satInitYVel );


		
	}
	
	
	
	public class HandlerClass implements MouseListener, MouseMotionListener{
		public void mouseClicked(MouseEvent event) {
			
		
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
	
	
	protected void setProps() {
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
    	
    	
    	
    	Rectangle r = cat1.getBounds(); 
    	if(drag == false && r.contains(gol.getX_Coord(), gol.getY_Coord()) ) {
     			cat1.reset(cat1.getX_Coord(), cat1.getY_Coord());
    			endLevel();   		
    	}
    	
        for (Prop p : gravityField.getBlackHoleList()) {
        	Rectangle r2 = new Rectangle(p.getX_Coord() - p.getWidth()/4, p.getY_Coord() - p.getHeight()/4, 20, 20);
        	if(drag == false && r.intersects(r2)) {
        		cat1.reset(catInitX, catInitY);
        		System.out.println("sucked");
        	}
        	
        }
    	
    	 
    	
    	if(x > width || x < 0 || y > height || y < 0) {
    		System.out.println("out of bounds");
    		cat1.reset(catInitX, catInitY);

    	}
    	
    	for(Satellite s: satelliteList) {
    		x = s.getX_Coord();
    		y = s.getY_Coord();
    		
        	if(x < width && y < height && x > 0 && y > 0) {
        		ax = gravityField.getAcc_Vector(x,y).getAcc_H();
        		ay = gravityField.getAcc_Vector(x,y).getAcc_V();  		
        	}
    		
        	if (s.getStationary() == false) {
        		s.setAcceleration(ax, ay);
        		s.accelerate();
        		s.move();
        		
        	}
        	
        	Rectangle r2 = new Rectangle(s.getX_Coord(), s.getY_Coord(), 10, 10);
        	if(drag == false && r.intersects(r2)) {
        		cat1.reset(catInitX, catInitY);
        		System.out.println("collided");
        		
        	}
        	
    		if(x > width || x < 0 || y > height || y < 0) {
    			
    			if(s.getTimer().isAlive() == false) {
    				s.respawn();
    			}
    			
    			
    		}	
    	}
    	
    }
    
    
    
    @Override
    public void run() {
    	
    	
    	long beforeTime, timeDiff, sleep;
    	
    	beforeTime = System.currentTimeMillis();
    	
    	while (exit != true) {
    		
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
    
    private void addStringToDoubleList(List<Double> doubleList, String string) {
    	
    	if(string.contentEquals("all")) {
	    		
    	}
    	else {
    		doubleList.add(Double.parseDouble(string));
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
    			goalInitX = readStringAsInt(goalInitX, parts[2]);
    			goalInitY = readStringAsInt(goalInitY, parts[3]);
    			addStringToIntList(starInitX, parts[4]);
    			addStringToIntList(starInitY, parts[5]);
    			addStringToIntList(blackInitX, parts[6]);
    			addStringToIntList(blackInitY, parts[7]);
    			addStringToIntList(satInitX, parts[8]);
    			addStringToIntList(satInitY, parts[9]);
    			addStringToDoubleList(satInitXVel, parts[10]);
    			addStringToDoubleList(satInitYVel, parts[11]);
    			

    			

            
    		}	    		
    	} catch(IOException e) {
    		e.printStackTrace();
    	}

    	
    }
    
    private void initProps() {
    	if(catInitX != 0 && catInitY != 0 && lev == 1) {
    		cat1 = new Cat(catInitX, catInitY, 0, 0, 0, 0, 10);
    	}
    	
    	if(catInitX != 0 && catInitY != 0 && lev > 1) {
    		cat1 = catCont;
    		cat1.setPosition(catInitX, catInitY);
    	}
		if(goalInitX != 0 && goalInitY != 0) {
			gol = new Goal(goalInitX, goalInitY, 10);	
		}
		
		for(int i = 0; i < starInitX.size(); i++) {
			
			Star s = new Star(starInitX.get(i), starInitY.get(i), 10);
			starList.add(s);
			
		}
		
		for(int i = 0; i < blackInitX.size(); i++) {
			
	    	BlackHole b = new BlackHole(blackInitX.get(i), blackInitY.get(i), 5, 10);
	    	gravityField.addBlackHole(b);
			
		}
		
		for(int i = 0; i < satInitX.size(); i++) {
			
	    	Satellite s = new Satellite(satInitX.get(i), satInitY.get(i), satInitXVel.get(i), satInitYVel.get(i), 3, 10);
	    	satelliteList.add(s);	
		}
		//scan = new Scanner(new File("src/resources/levelParameters/level1.txt"));
    }
    
    protected void setDisplayPanel() {
    	
		displayPanel = new JPanel();
		displayPanel.setPreferredSize(new Dimension(width, 20));
		
		try {
			//pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("PixelMplus12.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("PixelMplus12.ttf")));
					
		}
		
		catch (IOException | FontFormatException e){
			
		}

		label = new JLabel("ExampleFont");
		label.setFont(new Font("PixelMplus12", Font.BOLD, 10));
		label.setBounds(10,10, 500, 20);  
		displayPanel.add(label);
    }
    
    
    public void addElements() {
    	
	    this.add(displayPanel, BorderLayout.NORTH);
    }
    
    protected void setEventHandlers() {
    	
		HandlerClass handler = new HandlerClass();
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
    }
    
    protected void setCanvasAppearance() {
    	
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(width, height));    //setCanvasAppearance
		setSize(width, height);
		setLayout(new BorderLayout());
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
    
    protected void drawCat(Image image, Graphics2D g2d) {
    	
    	
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
    		g2d.drawImage(image, cat1.getX_Coord() + image.getWidth(null), cat1.getY_Coord() /*- image.getHeight(null)/2*/, -image.getWidth(null), image.getHeight(null), this);
    	}
    	else {
    		g2d.drawImage(image, cat1.getX_Coord(), cat1.getY_Coord(), this);
    	}
    	
    	
    }
 
 
    protected void drawGoal(Image image, Graphics2D g2d) {
    	
        if(gol != null) {
        	image = gol.getNextSprite();
        	g2d.drawImage(image, gol.getX_Coord(), gol.getY_Coord(), this);        	
        }
        
    	
    }
    
    protected void drawSatellite(Image image, Graphics2D g2d) {
    	
    	for(Satellite s : satelliteList) {
    		g2d.drawOval(s.getX_Coord(), s.getY_Coord(), 10, 10);
    		
    	}
    	
  
    }
    
    protected void drawFromObjectList(Image image, Graphics2D g2d, List<Prop >objectList) {
    	
        for (Prop p : objectList) {
        	
        	image = p.getNextSprite();
        	g2d.drawImage(image, p.getX_Coord() - p.getWidth()/2, p.getY_Coord() - p.getHeight()/2, this);
         
     
        }
 	
    	
    }
    
    protected void drawLine(Graphics g) {
    	
        if(dragX == 0 && dragY == 0) {
        	
        	dragX = presX;
        	dragY = presY;
        }
        
        g.drawLine(presX, presY, dragX, dragY);    	
    }
    
    public Thread getAnimator() {
    	
    	return animator;
    }
    
    public void endLevel() {
    	//levelFinished = true;
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