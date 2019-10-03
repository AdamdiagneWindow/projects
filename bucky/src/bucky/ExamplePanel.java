package bucky;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;


import java.awt.BorderLayout;



public class ExamplePanel extends JPanel {

	
	private final int width = 800;
	private final int height = 800;

	
	public ExamplePanel() {
		
		initBoard();
	}
	
	private void initBoard() {

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		setLayout(new BorderLayout());
		
		
	}
	
	
	
 
}