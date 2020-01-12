package bucky;

import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;


public class MyContactListener implements ContactListener {
	
	
	
	public void beginContact(Contact contact) {
		System.out.println("contactListener");
		Object bodyUserData = contact.getFixtureA().getBody().getUserData();
		((ProjectileNew)bodyUserData).startContact();
		
	}
	
	
	public void endContact(Contact contact) {
		Object bodyUserData = contact.getFixtureA().getBody().getUserData();
		((ProjectileNew)bodyUserData).endContact();
		
	}
	
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}
	
	
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
	
}
