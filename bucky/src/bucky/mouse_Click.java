package bucky;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mouse_Click extends JFrame {

 private JPanel contentPane;
 private int startX = 0;
 private int startY = 0;
 
 private int currentX = 0;
 private int currentY = 0;
 
 private int frameWidth = 608;
 private int frameHeight = 448;
 private int panelWidth = 592;
 private int panelHeight = 410;
 
 

 /**
  * Launch the application.
  */
 public static void main(String[] args) {
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     mouse_Click frame = new mouse_Click();
     frame.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }

 /**
  * Create the frame.
  */
 public mouse_Click() {
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setBounds(100, 100, frameWidth, frameHeight);
  contentPane = new JPanel();
  contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
  setContentPane(contentPane);
  contentPane.setLayout(null);
  
  jPanel2 panel = new jPanel2();
  panel.setLayout(null);
  panel.setBounds(0, 0, panelWidth, panelHeight);
  contentPane.add(panel);
  
  final JLabel label = new JLabel(".................");
  label.setFont(new Font("Tahoma", Font.BOLD, 10));
  label.setBounds(10,10, 500, 20);  
  panel.add(label);

  
  final JLabel label2 = new JLabel(".................");
  label2.setFont(new Font("Tahoma", Font.BOLD, 10));
  label2.setBounds(10,30, 500, 20);  
  panel.add(label2);
  
  
  
  
  final GravitationalField gravityField = new GravitationalField(panelWidth, panelHeight);
  
  BlackHole b = new BlackHole(300, 300, 100.00, 10);
  
  gravityField.addBlackHole(b);
  
  
  
  panel.addMouseListener(new MouseAdapter() {
   
   public void mouseClicked(MouseEvent e){
    
	currentX = e.getX();
	currentY = e.getY();
	   
    label.setText("aX = " + gravityField.getAcc_Vector(currentX, currentY).getAcc_H() + "    aY = " + gravityField.getAcc_Vector(currentX, currentY).getAcc_V());
    label2.setText("x = " + currentX + "     y = " + currentY);
    
    
    
    startX = currentX;
    startY = currentY;
    repaint();
   }
   
  });  
 }
 
 class jPanel2 extends JPanel{
	 
	 @Override
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 
		 //g.drawRect(200, 200, width, height);
		 g.drawLine(0, 0, startX, startY);
		 
		 Graphics2D g2d = (Graphics2D)g;
		 g2d.drawOval(300,300, 5, 5);
		 
		 
	 }
 }
 
 
 
}