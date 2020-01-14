/*package bucky;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;

import java.awt.Rectangle;


public abstract class Prop {

	protected int x_Coord, y_Coord, width, height, imageIndex = 0, count = 0, frameDelay, frameDelayInit;
	protected List<Image> sprites;
	protected Long collisionStart = new Long(0);
	
	public Prop() {
		
		sprites = new ArrayList<Image>();
		
	}
	
	public Prop (int x, int y, int delay){
		
		x_Coord = x;
		y_Coord = y;
		
		frameDelay = delay;
		frameDelayInit = delay;
		
	}
	
	
	public void addSprite(String directory) {
		
		Image im;
		ImageIcon icon = new ImageIcon(directory);
		im = icon.getImage();
		sprites.add(im);
		
		if(sprites.size() == 1) {
			width = im.getWidth(null);
			height = im.getHeight(null);
		}
		
	}
	
	
	public void animate() {
		
		if(count >= frameDelay) {
			imageIndex++;
			count = 0;
		}
			
		if(imageIndex == sprites.size()) {
			imageIndex = 0;
		}
		count++;
	}
	
	
	
	
	public void setPosition(int x, int y) {
		
		x_Coord = x;
		y_Coord = y;
		
	}
	
	public void setDelay(int delay) {
		
		frameDelay = delay;
		
	}
	
	public void resetPropAnimation() {
		
		imageIndex = 0;
		count = 0;
		frameDelay = frameDelayInit;
		
		
	}
	
	
	public int getWidth() {
		
		return width;
		
	}
	
	public int getHeight() {
		
		return height;
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x_Coord, y_Coord, width, height);
		
	}
	
	public Image getSprite() {
		
		return sprites.get(imageIndex);
		
	}
	
	public Image getNextSprite() {
		
		animate();
		return getSprite();
		
	}
	
	public Image getSpecificSprite(int x) {
		
		return sprites.get(x);
		
	}
	
	public List<Image> getSpriteList(){
		
		return sprites;
	}
	
	public int getX_Coord() {
		
		return x_Coord;
	}
	
	public int getY_Coord() {
		
		return y_Coord;
	}
	
	
	
	
	
	
}*/


package bucky;

import java.awt.Image;
import javax.swing.ImageIcon;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;


import java.util.List;
import java.util.ArrayList;
import java.awt.Point;

import java.awt.Rectangle;


public class Prop {

	protected int  width, height, imageIndex = 0, count = 0, frameDelay, frameDelayInit;
	protected boolean contacting;
	protected List<Image> sprites;
	protected Long collisionStart = new Long(0);
	
	
	//BOX2D Classes
	
	protected World m_world; 
	protected BodyDef bd;
	protected Body body;
	protected PolygonShape shape;
	protected FixtureDef fd;
	
	
	
	public Prop() {
		
			
	}
	
	public Prop (int x, int y, int delay, World world){
		
		sprites = new ArrayList<Image>();
		contacting = false;
		
		m_world = world;
		
		bd = new BodyDef();                  //Initialize Body and Fixture		
		bd.position.set(x,y);
		bd.type = BodyType.STATIC;
		body = m_world.createBody(bd);
		shape = new PolygonShape();
		shape.setAsBox(20f, 20f);
		fd = new FixtureDef();
		fd.shape = shape;                  
		fd.density = 0.0f;                   //Default Density. Change with setDensity function
		fd.restitution = 1;                   //Default Restitution. Change with setRestitution function
		//fd.filter.maskBits = 0;
		fd.isSensor  = true;
		body.createFixture(fd);
		body.setUserData(this);
	
	
		frameDelay = delay;
		frameDelayInit = delay;
		
	}
	
	public void addSprite(String directory) {
		
		Image im;
		ImageIcon icon = new ImageIcon(directory);
		im = icon.getImage();
		sprites.add(im);
		
		if(sprites.size() == 1) {
			width = im.getWidth(null);
			height = im.getHeight(null);
		}
		
	}
	
	
	public void animate() {
		
		if(count >= frameDelay) {
			imageIndex++;
			count = 0;
		}
			
		if(imageIndex == sprites.size()) {
			imageIndex = 0;
		}
		count++;
	}
	
	
	
	
	public void setPosition(int x, int y) {
		
		Vec2 position = new Vec2((float)x, (float)y);
		body.setTransform( position, 0);
	
		
	}
	
	public void setDelay(int delay) {
		
		frameDelay = delay;
		
	}
	

	public void resetPropAnimation() {
		
		imageIndex = 0;
		count = 0;
		frameDelay = frameDelayInit;
		
		
	}
	
	
	public int getWidth() {
		
		return width;
		
	}
	
	public int getHeight() {
		
		return height;
		
	}
	
	public Rectangle getBounds() {
		
		return new Rectangle((int)body.getPosition().x, (int)body.getPosition().y, width, height);
		
	}
	
	public Image getSprite() {
		
		return sprites.get(imageIndex);
		
	}
	
	public Image getNextSprite() {
		
		animate();
		return getSprite();
		
	}
	
	public Image getSpecificSprite(int x) {
		
		return sprites.get(x);
		
	}
	
	public List<Image> getSpriteList(){
		
		return sprites;
	}
	
	public int getX_Coord() {
		
		return (int)body.getPosition().x;
	}
	
	public int getY_Coord() {
		
		return (int)body.getPosition().y;
	}
	

	public void startContact() {
		//System.out.println("start contact");
		
	}
	
	public void endContact() {
		//System.out.println("end contact");
		
	}
	

	
	
	
	
}

