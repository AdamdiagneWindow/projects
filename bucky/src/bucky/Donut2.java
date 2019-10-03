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
	Level lev;
	LevelTitle levTitle;
	Title titlePanel;
	
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
    		
        
       titlePanel = new Title(/*cl, gamePanel, LEVELTITLE*/);
        
       /*
    	board = new Board();*/
       lev = new Level(1);
       
    	levTitle = new LevelTitle(1);
    	
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
    
    public void addLevel(int level, Cat caT) {
    	lev = null;
    	lev = new Level(level, caT);
    	gamePanel.add(lev, GAMEPANEL);
    }
    
    public void addLevelTitle(int level){
    	levTitle = new LevelTitle(level);
    	gamePanel.add(levTitle, LEVELTITLE);    	
    	
    }
    
    
    public String getString() {
    	return test;
    }
    
    
    
   public Title getTitlePanel() {
	   return titlePanel;
   }
    
    public Board getBoard() {
    	return board; 
    }
    
    public Level getLevel() {
    	return lev;
    }
    
    public LevelTitle getLevelTitle() {
    	return levTitle;
    }
    
    
    public static void main(String[] args) {


                Donut2 ex = new Donut2();
                ex.setVisible(true);
                
    }
}


