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





public class Board extends JPanel 
		implements Runnable{


	private final int width = 800;
	private final int height = 800;
	private final int delay = 25;
	private Thread animator;
	

	private int currentX = 0, currentY = 0, presX = 0, presY = 0, dragX = 0, dragY = 0, blackHoleX = 0, blackHoleY = 0, goalX, goalY;
	private int catInitX, catInitY, goalInitX, goalInitY, catNum, starNum, blackNum, goalNum;
	private List<Integer> starInitX, starInitY, blackInitX, blackInitY;
	private String lev;
	
	private double angle, tanAngle;

	private boolean drag = false, flipCat = false;
	private boolean addingBlackHole = false;
	private boolean addingGoal = false;
	
    private JLabel label;
    
    private JPanel displayPanel;
    private JPanel inputPanel;
    

    private JButton addBlackHole;
    private JButton addGoal;
    private JButton saveLevel;
    private JButton clear;    
    private JTextField inputLevel;
    
	
	private gravitationalField gravityField;
	private cat cat1;
	private List<star> starList;
	private goal gol;
	
	
	public Board() {
		
		initBoard();
	}
	
	private void initBoard() {

		starInitX = new ArrayList<Integer>();
		starInitY = new ArrayList<Integer>();
		blackInitX = new ArrayList<Integer>();
		blackInitY = new ArrayList<Integer>();
		
		starList = new ArrayList<star>();
		gravityField = new gravitationalField(width, height);		
		
		setProps();	   //Read props data from level file and initializes props
		setCanvasAppearance(); //Sets JPanel general appearance
		setDisplayPanel(); // Sets stat display panel at top of screen
		setInputPanel(); // Sets input buttons at button of panel
		setEventHandlers(); //Sets event handlers
		addElements();  //Adds panels to screen


		
	}
	
	
	
	private class HandlerClass implements MouseListener, MouseMotionListener{
		public void mouseClicked(MouseEvent event) {
			
			
			currentX = event.getX();
			currentY = event.getY();
			label.setText("x = " + currentX + "     y = " + currentY);								
			
		    
		    if(addingBlackHole == false && addingGoal == false) {
		    	
		    	resetCat(currentX, currentY);
				
		    }
		    
		    if (addingBlackHole == true) {
		    	
		    	blackHole b = new blackHole(event.getX(), event.getY(), 5, 10);      // write black hole position
		    	gravityField.addBlackHole(b);
		    	
		    	addingBlackHole = false;
		    
		    	
		    }
		    
		    if (addingGoal == true) {
		    	
		    	if(gol == null) {
		    		
		    		gol = new goal(event.getX(), event.getY(), 10);
		    	}
		    	else {
		    		gol.setPosition(event.getX(), event.getY());		    		 // write goal position
		    	}
		    	
		    	addingGoal = false;


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
			
			
			if(cat1.getStationary() == true) {
				cat1.setVelocity( 0.1 * (presX - dragX), 0.1 * (presY - dragY) );
			}
			
			presX = 0;
			presY = 0;
			dragX = 0;
			dragY = 0;
			
			//repaint();
					
			if(addingBlackHole == false && addingGoal == false && drag == true) {    //potential source of error
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
			
			if(addingGoal == true) {
				
				goalX = event.getX();
			    goalY = event.getY();
				
			}

		}
		
		
	}
	
	
	private void setProps() {
		

		try (FileReader reader = new FileReader("src/resources/levelParameters/levelTest.txt");
				BufferedReader br = new BufferedReader(reader)){
			readPropData(br);
			initProps();
			//scan = new Scanner(new File("src/resources/levelParameters/level1.txt"));
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
    		
			Donut2 f1 = (Donut2) SwingUtilities.windowForComponent(this);
			
			//System.out.println(f1.getString());
    		//setVisible(false);
    		
			
    	}
    	
    	
    	if(x > width || x < 0 || y > height || y < 0) {
    		
    		resetCat(catInitX, catInitY);
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
   
    			catInitX = readStringAsInt(catInitX, parts[0]);
    			catInitY = readStringAsInt(catInitY, parts[1]);
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
		cat1 = new cat(catInitX, catInitY, 0, 0, 0, 0, 10);
		
		for(int i = 0; i < starInitX.size(); i++) {
			
			star s = new star(starInitX.get(i), starInitY.get(i), 10);
			starList.add(s);
			
		}
		
		for(int i = 0; i < blackInitX.size(); i++) {
			
	    	blackHole b = new blackHole(blackInitX.get(i), blackInitY.get(i), 5, 10);
	    	gravityField.addBlackHole(b);
			
		}
		gol = new goal(goalInitX, goalInitY, 10);
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
    
    private void setInputPanel() {
    	
		inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(width, 20));
		inputPanel.setLayout(null);
		setAddBlackHoleButton();
		setAddGoalButton();
		setSaveLevelButton();
		setClearButton();
		setInputLevelField();
		
    }
    
    private void addElements() {
    	
	    this.add(displayPanel, BorderLayout.NORTH);
	    this.add(inputPanel, BorderLayout.SOUTH);
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
    		label.setText("angle" + angle);
    		
    		
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

        if(addingBlackHole == true) {
        	
        	g2d.drawOval(blackHoleX, blackHoleY, 10, 10);
        	
        }
        
    }
 
    private void drawGoal(Image image, Graphics2D g2d) {
    	
        if(gol != null) {
        	image = gol.getNextSprite();
        	g2d.drawImage(image, gol.getX_Coord(), gol.getY_Coord(), this);        	
        }
        
        if(addingGoal == true) {
        	
        	g2d.drawRect(goalX, goalY, 10, 10);
        	
        }
    	
    }
    
    private void drawLine(Graphics g) {
    	
        if(dragX == 0 && dragY == 0) {
        	
        	dragX = presX;
        	dragY = presY;
        }
        
        g.drawLine(presX, presY, dragX, dragY);    	
    }
    
    private void clearLevel() {
    	starList.clear();
    	gravityField.clearBlackHoles();
    
    }
    
    private void setAddBlackHoleButton(){
		addBlackHole = new JButton();
		addBlackHole.setBounds(0, 0, 20, 20);
		addBlackHole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				addingBlackHole = true;
				
			}
			
		}); 
		inputPanel.add(addBlackHole);
    }
    
    private void setAddGoalButton() {
		addGoal = new JButton();
		addGoal.setBounds(20, 0, 20, 20);
		addGoal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				addingGoal = true;
				
			}
			
		}); 
		inputPanel.add(addGoal);
    	
    }
    
    private void setSaveLevelButton() {
		saveLevel = new JButton();
		saveLevel.setBounds(780, 0, 20, 20);
		saveLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                try {
                	FileWriter fw = new FileWriter("src/resources/levelParameters/level" + lev + ".txt");
                	BufferedWriter writeFileBuffer = new BufferedWriter(fw);
                	
                	List <blackHole> blackHoleList = gravityField.getBlackHoleList();
                	int maxListSize = starList.size();
                	if(blackHoleList.size() > maxListSize) {
                		maxListSize = blackHoleList.size();
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


                		if(i == 0) {
                			writeFileBuffer.write(gol.getX_Coord() + " ");
                			writeFileBuffer.write(gol.getY_Coord() + " ");		
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
    
    private void setClearButton() {
		clear = new JButton();
		clear.setBounds(760, 0, 20, 20);
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
		inputLevel.setBounds(40, 0, 20,20);
		inputLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lev = inputLevel.getText();
				System.out.println("level: " + lev);
			}

		});
		inputPanel.add(inputLevel);
    }
    
    public Thread getAnimator() {
    	
    	return animator;
    }
    
}