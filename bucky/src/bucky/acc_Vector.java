package bucky;

public class acc_Vector {

	private double acc_H;
	private double acc_V;
	private double acc_Mag;
	
	public acc_Vector(double h, double v) {
		
		acc_H = h;
		acc_V = v;
		
	}
	
	public void add(acc_Vector a) {
		
		acc_H += a.getAcc_H();
		acc_V += a.getAcc_V();
		
	}
	
	
	public double getAcc_H() {
		
		return acc_H;
				
	}
	
	public double getAcc_V() {
		
		return acc_V;
		
	}
	
	
	
	
}
