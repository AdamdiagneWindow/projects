package bucky;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import javax.swing.*;


public class levelTitle extends JPanel
		implements Runnable{
	
	private final int delay = 25;
	
	private JButton start;
	private JLabel title;
	private int width = 800;
	private int height = 800;
	private int counter = 0;
	
	private Thread timer;
	
	
	public levelTitle() {
		
		initLevelTitle();
		
	}
	
	private void initLevelTitle() {
		
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.BLACK);
		this.setLayout(null);
		
		setTitle();
 
	}
	
	private void setTitle() {
		
		Image titleIm;
    	ImageIcon titleIcon = new ImageIcon("src/resources/level1.png");
    	title = new JLabel(titleIcon);
    	titleIm = titleIcon.getImage();
    	title.setBounds(width/2 - 50, height/2, titleIm.getWidth(null), titleIm.getHeight(null));
    	
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
				
		if (counter == 120) {
			Donut2 f1 = (Donut2) SwingUtilities.windowForComponent(this);
			f1.getLevelTitle().setVisible(false);
			f1.getBoard().setVisible(true);
			
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