package bucky;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import javax.swing.*;


public class title extends JPanel{
	
	
	private JButton play;
	
	public title(final CardLayout cl, final Container pane, final String nextPanelName) {
		
		initTitle(cl, pane, nextPanelName);
	}
	
	private void initTitle(final CardLayout cl, final Container pane, final String nextPanelName) {
		
		setBackground(Color.BLACK);
		
		
		play = new JButton("Play");
		
    	play.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			
    			cl.show(pane, nextPanelName);
    		}
    	});
    	
    	JPanel buttonPanel = new JPanel();
    	buttonPanel.setPreferredSize(new Dimension(600, 600));
    	buttonPanel.setBackground(Color.BLACK);
    	buttonPanel.setLayout(null);
    	
    	play.setBounds(300, 300, 100, 40);
    	
    	buttonPanel.add(play);
    	this.add(buttonPanel);
		
		
	}
	
}
