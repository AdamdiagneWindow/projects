package bucky;

import java.io.IOException;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Donut extends JFrame {
    
    public Donut() {

        initUI();
    }

    private void initUI() {

        add(new Board());
        setResizable(false);
        pack();
        
        setTitle("Basic shapes");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }    
    
    public static void main(String[] args) {

        
       
                Donut ex = new Donut();
                ex.setVisible(true);
    }
}


