package bucky;

import java.io.IOException;
import java.awt.EventQueue;
import javax.swing.JFrame;
import org.lwjgl.*;

//This is the game editor
public class GameEditor extends JFrame {
	
	
    LevelEditor e;
	
    public GameEditor() {

        initUI();
    }

    private void initUI() {
    	
    	/*
    	board = new Board();
        add(board);*/
    	
    	e = new LevelEditor();
    	add(e);
        setResizable(false);
        pack();
    
        
        
        setTitle("Basic shapes");
        setLocationRelativeTo(null);
        
        e.startTimer();
        //board.startTimer();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }    
    
    public static void main(String[] args) {

        
       
                GameEditor ex = new GameEditor();
                ex.setVisible(true);
    }
}


