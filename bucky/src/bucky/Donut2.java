package bucky;

import java.io.IOException;
import java.awt.EventQueue;
import javax.swing.JFrame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Donut2 extends JFrame {
    
	JPanel gamePanel;
	final static String TITLEPANEL = "Card with TitleScreen";
	final static String GAMEPANEL = "Card with BoardPanel";
	
    public Donut2() {

        initUI();
    }

    private void initUI() {
    	
    	
    	gamePanel = new JPanel(new CardLayout());
        final CardLayout cl = (CardLayout)(gamePanel.getLayout());  
        
        /*
    	JPanel titlePanel = new JPanel();
    	
    	JButton play = new JButton("Play");
    	
    	play.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			
    			cl.show(gamePanel, GAMEPANEL);
    		}
    	});
    	
    	titlePanel.add(play);
    	*/
    		
        
       title titlePanel = new title(cl, gamePanel, GAMEPANEL);
        
    	Board board = new Board();
    	
    	gamePanel.add(titlePanel, TITLEPANEL);
    	gamePanel.add(board, GAMEPANEL);
    	
       
        cl.show(gamePanel, TITLEPANEL);   	
        
        
        
        add(gamePanel);
        
        setResizable(false);
        pack();
        
        setTitle("Nekor");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
    }    
    
    public static void main(String[] args) {

        
       
                Donut2 ex = new Donut2();
                ex.setVisible(true);
    }
}

