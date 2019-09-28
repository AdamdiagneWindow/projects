package bucky;

import java.io.IOException;

import javax.swing.JFrame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileWriter;
import java.io.BufferedWriter;

public class Donut2 extends JFrame {
    
	JPanel gamePanel;
	String test = " test";
	final static String TITLEPANEL = "Card with TitleScreen";
	final static String GAMEPANEL = "Card with BoardPanel";
	final static String LEVELTITLE = "Card with LevelTitle";
	
	Board board;
	level lev;
	levelTitle levTitle;
	title titlePanel;
	
    public Donut2() {

        initUI();
    }

    private void initUI() {
    	
    	
    	gamePanel = new JPanel(new CardLayout());
       // final CardLayout cl = (CardLayout)(gamePanel.getLayout());  
        
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
    		
        
       titlePanel = new title(/*cl, gamePanel, LEVELTITLE*/);
        
       /*
    	board = new Board();*/
       lev = new level(1);
       
    	levTitle = new levelTitle(1);
    	
    	gamePanel.add(titlePanel, TITLEPANEL);
    	gamePanel.add(lev, GAMEPANEL);
    	gamePanel.add(levTitle, LEVELTITLE);
    	
       
        //cl.show(gamePanel, TITLEPANEL);
    	titlePanel.setVisible(true);
        
        
        
        add(gamePanel);
        
        setResizable(false);
        pack();
        
        setTitle("Nekor");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
    }    
    
    public void addLevel(int level, cat caT) {
    	lev = null;
    	lev = new level(level, caT);
    	gamePanel.add(lev, GAMEPANEL);
    }
    
    public void addLevelTitle(int level){
    	levTitle = new levelTitle(level);
    	gamePanel.add(levTitle, LEVELTITLE);    	
    	
    }
    
    
    public String getString() {
    	return test;
    }
    
    
    
   public title getTitlePanel() {
	   return titlePanel;
   }
    
    public Board getBoard() {
    	return board; 
    }
    
    public level getLevel() {
    	return lev;
    }
    
    public levelTitle getLevelTitle() {
    	return levTitle;
    }
    
    
    public static void main(String[] args) {


                Donut2 ex = new Donut2();
                ex.setVisible(true);
                
    }
}


