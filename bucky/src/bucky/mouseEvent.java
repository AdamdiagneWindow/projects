package bucky;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class mouseEvent extends JFrame{
	
	private JPanel mousepanel;
	private JLabel statusbar;
	
	private int posX = 0;
	private int posY = 0;
	private int dragX = 0;
	private int dragY = 0;
	
	
	public mouseEvent() {
		
		super("title");
	
		mousepanel = new jPanel2();
		mousepanel.setBackground(Color.WHITE);
	    add(mousepanel, BorderLayout.CENTER);	
	    
	    statusbar = new JLabel("default");
	    add(statusbar, BorderLayout.SOUTH);
	    
	    Handlerclass handler = new Handlerclass();
	    mousepanel.addMouseListener(handler);
		mousepanel.addMouseMotionListener(handler);
		
		
	}
	
	private class Handlerclass implements MouseListener, MouseMotionListener{
		public void mouseClicked(MouseEvent event) {
			statusbar.setText(String.format("clicked at %d, %d", event.getX(), event.getY()));
		}
		
		public void mousePressed(MouseEvent event) {
			statusbar.setText(String.format("pressed at %d, %d", event.getX(), event.getY()));
			posX = event.getX();
			posY = event.getY();
		}
		
		public void mouseReleased(MouseEvent event) {
			statusbar.setText(String.format("released at %d, %d", event.getX(), event.getY()));
			posX = 0;
			posY = 0;
			dragX = 0;
			dragY = 0;
			repaint();
		}
		
		public void mouseEntered(MouseEvent event) {
			statusbar.setText("you entered the area");
			//mousepanel.setBackground(Color.RED);
		}
		
		public void mouseExited(MouseEvent event) {
			statusbar.setText("The mouse has left the window");
		    //mousepanel.setBackground(Color.WHITE);
		}
		
		public void mouseDragged(MouseEvent event) {
			statusbar.setText(String.format("dragging at %d, %d", event.getX(), event.getY()));
			dragX = event.getX();
			dragY = event.getY();
			repaint();
			
		}
		
		public void mouseMoved(MouseEvent event) {
			statusbar.setText("you are moving the mouse");
		}
		
		
	}

	 class jPanel2 extends JPanel{
		 
		 @Override
		 public void paintComponent(Graphics g) {
			 super.paintComponent(g);
			 
			 //g.drawRect(200, 200, width, height);
			 g.drawLine(posX, posY, dragX, dragY);
			 
			 
			 
		 }
	 }
	 
	
	

	
	

}
