package bucky;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameOverScreen extends JPanel
		implements Runnable{
	
	private final int delay = 25;
	
	private JLabel title, image;
	private Font pixelFont;
	private Animation gameOverAnimation;
	
	private int width = 800, height = 800, lev = 0, counter = 0;
	private boolean exit = false;
	
	private Thread timer;
	
	public GameOverScreen() {
		
		initGameOverScreen();
	}
	
	private void initGameOverScreen() {
		
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.BLACK);
		this.setLayout(null);
		
		setTitle();
		setAnimation();
		
	}
	
	private void setTitle() {

		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("PixelMplus12.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("PixelMplus12.ttf")));
					
		}
		
		catch (IOException | FontFormatException e){
			
		}
				
		    title = new JLabel("GAME OVER ");
		    title.setFont(new Font("PixelMplus12", Font.BOLD, 25));
		    title.setForeground(Color.white);

		
		title.setBounds(width/2 - 50, height/2, 400, 100);
	
		this.add(title);	
		
	
	}
	
	private void setAnimation() {
		
		gameOverAnimation = new Animation(width/2 - 100, height/3, 7);
		gameOverAnimation.addSprite("src/resources/grave1.png");
		gameOverAnimation.addSprite("src/resources/grave2.png");
		gameOverAnimation.addSprite("src/resources/grave3.png");
		gameOverAnimation.addSprite("src/resources/grave4.png");
		gameOverAnimation.addSprite("src/resources/grave5.png");
		gameOverAnimation.addSprite("src/resources/grave6.png");
		gameOverAnimation.addSprite("src/resources/grave7.png");
		gameOverAnimation.addSprite("src/resources/grave8.png");
		gameOverAnimation.addSprite("src/resources/grave9.png");
	}
	
	
	
	public void startTimer() {
		timer.start();
	}
	
	
	@Override
	public void addNotify() {
		super.addNotify();
		
		timer = new Thread(this);

		
	}
	
	public void doDrawing(Graphics g) {
		
    	Image image = null;
    	
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.setPaint(new Color(150, 150, 150));
        RenderingHints rh = new RenderingHints(
        		RenderingHints.KEY_ANTIALIASING,
        		RenderingHints.VALUE_ANTIALIAS_ON);	
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);   
        
        //drawAnimation(image, g2d, gameOverAnimation );
        gameOverAnimation.drawAnimation(this, image, g2d, counter, 30, 300, 7);
        
		
	}
	
	
    @Override
    public void paintComponent(Graphics g) {
    	
    	super.paintComponent(g);
    	doDrawing(g);
    	
    }
	
	private void cycle() {
		
		counter++;
		if (counter == 200) {
	    	exit = true;
	    	GameLauncher f1 = (GameLauncher) SwingUtilities.windowForComponent(this);
	    	LevelData levD = new LevelData(1, 0, 30);
			f1.addLevel(levD);
			f1.addLevelTitle(1);
	    	this.setVisible(false);
	    	f1.getTitlePanel().setVisible(true);
			
			
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
    
    /*
    private void drawAnimation(Image image, Graphics2D g2d, Animation animation){
    	
    	image = animation.getNextSprite();
    	g2d.drawImage(image, animation.getX_Coord(), animation.getY_Coord(), this);
    	
    }*/

}
