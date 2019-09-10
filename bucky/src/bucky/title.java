package bucky;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import javax.swing.*;


public class title extends JPanel{
	
	
	private JButton start;
	private JLabel title;
	
	public title(final CardLayout cl, final Container pane, final String nextPanelName) {
		
		initTitle(cl, pane, nextPanelName);
	}
	
	private void initTitle(final CardLayout cl, final Container pane, final String nextPanelName) {
		
		
		setBackground(Color.BLACK);
		this.setLayout(null);
		
		setStart(cl, pane, nextPanelName);
		setTitle();
 
	}
	
	private void setStart(final CardLayout cl, final Container pane, final String nextPanelName) {
		
		Image startIm;
		ImageIcon startIcon = new ImageIcon("src/resources/start.png");
		startIm = startIcon.getImage();
		
		start = new JButton(startIcon);
		start.setRolloverIcon(new ImageIcon("src/resources/startSelected.png"));
		
		start.setMargin(new Insets(0, 0, 0, 0));
		start.setBorder(null);
		
    	start.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			
    			cl.show(pane, nextPanelName);
    		}
    	});
    	
    	start.setBounds(250, 300, startIm.getWidth(null), startIm.getHeight(null));
     	this.add(start);		
	}
	
	private void setTitle(){
    	
		Image titleIm;
    	ImageIcon titleIcon = new ImageIcon("src/resources/title.png");
    	title = new JLabel(titleIcon);
    	titleIm = titleIcon.getImage();
    	title.setBounds(160, 100, titleIm.getWidth(null), titleIm.getHeight(null));
    	
    	this.add(title);		
		
	}
	
}
