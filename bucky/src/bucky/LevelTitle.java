package bucky;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import javax.swing.*;

import java.io.*; 


public class LevelTitle extends JPanel
		implements Runnable{
	
	private final int delay = 25;
	
	private JButton start;
	private JLabel title;
	private Font pixelFont;
	
	private int width = 800, height = 800, lev = 0, counter = 0;
	private boolean exit= false;
	
	private Thread timer;
	
	public LevelTitle(int level) {
		
		lev = level;
		initLevelTitle(level);
		
	}
	
	private void initLevelTitle (int level) {
		
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.BLACK);
		this.setLayout(null);
		
		setTitle(level);
 
	}
	
	private void setTitle(int level) {

		try {
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("PixelMplus12.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("PixelMplus12.ttf")));
					
		}
		
		catch (IOException | FontFormatException e){
			
		}
				
		    title = new JLabel("LEVEL " + Integer.toString(level));
		    title.setFont(new Font("PixelMplus12", Font.BOLD, 25));
		    title.setForeground(Color.white);

		
		title.setBounds(width/2 - 50, height/2 - 100, 100, 100);
	
		this.add(title);	
		
	
	}
	
	public void startTimer() {
		timer.start();
	}
	
	
	@Override
	public void addNotify() {
		super.addNotify();
		
		timer = new Thread(this);

		
	}
	
	
	private void cycle() {
		
		counter++;
		if (counter == 50) {
			System.out.println("iniii");
			exit = true;
			GameLauncher f1 = (GameLauncher) SwingUtilities.windowForComponent(this);
			this.setVisible(false);
			f1.getTitlePanel().setVisible(false);
			f1.getLevel().setVisible(true);
			if(f1.getLevel().getAnimator().isAlive() == false) {
				f1.getLevel().startTimer();
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

	
}