package bucky;


import java.io.IOException;

import javax.swing.JFrame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileWriter;
import java.io.BufferedWriter;

public class GameLauncher extends JFrame {
    
	JPanel gamePanel;
	String test = " test";
	final static String TITLEPANEL = "Card with TitleScreen";
	final static String GAMEPANEL = "Card with BoardPanel";
	final static String LEVELTITLE = "Card with LevelTitle";
	
	
	Level lev;
	LevelTitle levTitle;
	Title titlePanel;
	
    public GameLauncher() {

        initUI();
    }

    private void initUI() {
    	
    	
    	gamePanel = new JPanel(new CardLayout());
        
        titlePanel = new Title();
        
        lev = new Level(1);
       
    	levTitle = new LevelTitle(1);
    	
    	gamePanel.add(titlePanel, TITLEPANEL);
    	gamePanel.add(lev, GAMEPANEL);
    	gamePanel.add(levTitle, LEVELTITLE);

    	titlePanel.setVisible(true);
        
    	
    	//lev.setVisible(true);// Remove
        //lev.startTimer(); //Remove this in real game
    	
    	
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
    
    
    public Level getLevel() {
    	return lev;
    }
    
    public LevelTitle getLevelTitle() {
    	return levTitle;
    }
    
    
    public static void main(String[] args) {


                GameLauncher ex = new GameLauncher();
                ex.setVisible(true);
                
    }
}


