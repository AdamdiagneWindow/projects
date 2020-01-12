package bucky;

public class ForceVector {

	private double force_H;
	private double force_V;
	private double force_Mag;
	
	public ForceVector(double h, double v) {
		
		force_H = h;
		force_V = v;
		
	}
	
	public void add(ForceVector f) {
		
		force_H += f.getForce_H();
		force_V += f.getForce_V();
		
	}
	
	
	public double getForce_H() {
		
		return force_H;
				
	}
	
	public double getForce_V() {
		
		return force_V;
		
	}
	
	
	
	
}
