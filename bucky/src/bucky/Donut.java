package bucky;

import java.io.IOException;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Donut extends JFrame {
	
	Board board;
    LevelEditor e;
	
    public Donut() {

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

        
       
                Donut ex = new Donut();
                ex.setVisible(true);
    }
}


