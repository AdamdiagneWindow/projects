package bucky;

import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;


public class MyContactListener implements ContactListener {
	
	
	
	public void beginContact(Contact contact) {
		
		
		Object bodyUserData_A = contact.getFixtureA().getBody().getUserData();
		Object bodyUserData_B = contact.getFixtureB().getBody().getUserData();
		//((ProjectileNew)bodyUserData_A).startContact();
		
		String className_A = bodyUserData_A.getClass().getSimpleName();
	    String className_B = bodyUserData_B.getClass().getSimpleName();
		
		if((className_A.contentEquals("Cat") && className_B.contentEquals("Goal")) ||   
				(className_B.contentEquals("Cat") && className_A.contentEquals("Goal"))) {
			
			((Cat)bodyUserData_A).setAllowReset(true);
			((Cat)bodyUserData_A).setAllowEndLevel(true);
			
		}
		
		if((className_A.contentEquals("Cat") && className_B.contentEquals("BlackHole")) ||   
				(className_B.contentEquals("Cat") && className_A.contentEquals("BlackHole"))) {
			
			((Cat)bodyUserData_A).setAllowReset(true);
    		System.out.println("sucked");
			
			
		}
		
		if((className_A.contentEquals("Cat") && className_B.contentEquals("Satellite")) ||   
				(className_B.contentEquals("Cat") && className_A.contentEquals("Satellite"))) {
				
			System.out.println("collided");
			
		}
		
		if((className_A.contentEquals("Satellite") && className_B.contentEquals("BlackHole")) ||   
				(className_B.contentEquals("Satellite") && className_A.contentEquals("BlackHole"))) {
			if(className_A.contentEquals("Satellite")) {
				((Satellite)bodyUserData_A).setAllowReset(true);
			}
			if(className_B.contentEquals("Satellite")) {
				((Satellite)bodyUserData_B).setAllowReset(true);
			}
			

			System.out.println("Sucked");
			
		}

	}
	
	
	public void endContact(Contact contact) {
		
	}
	
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}
	
	
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
	
}
