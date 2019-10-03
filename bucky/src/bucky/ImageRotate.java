package bucky;

import java.io.File;
import java.io.IOException;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class ImageRotate extends JPanel {

	
	  
	
	  public BufferedImage rotateImageByDegrees(Image img, double degrees) {
	        double rads = Math.toRadians(degrees);
	        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
	        int w = img.getWidth(null);
	        int h = img.getHeight(null);
	        int newWidth = (int) Math.floor(w * cos + h * sin);
	        int newHeight = (int) Math.floor(h * cos + w * sin);

	        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = rotated.createGraphics();
	        AffineTransform at = new AffineTransform();
	        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

	        int x = w/2;
	        int y = h/2;

	        at.rotate(rads, x, y);
	        g2d.setTransform(at);
	        g2d.drawImage(img, 0, 0, this);
	        //g2d.setColor(Color.RED);
	        //g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
	        g2d.dispose();

	        return rotated;
	  }	
	  
	  
	
	public static void main(String[] args) throws IOException {
	
		ImageRotate2 rotate = new ImageRotate2();
		Image image;
		ImageIcon icon = new ImageIcon("src/resources/cat1.png");
		image = icon.getImage();

		BufferedImage rotated = rotate.rotateImageByDegrees(image, 90);
		ImageIO.write(rotated, "png", new File("RotatedImage.png"));
		
		
		
		
	}
	
}
