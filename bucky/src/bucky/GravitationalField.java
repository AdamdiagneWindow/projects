/*package bucky;

import java.util.List;
import java.util.ArrayList;


public class GravitationalField {
	
	
	
	private Acc_Vector [][] potentialMatrix;  
	private double [][] fieldOccupancy; 
	private List<Prop> blackHoleList;
	private int width, height;
	
	
	
	
	public GravitationalField(int w, int h) {
		width = w;
		height = h;
		potentialMatrix = new Acc_Vector[width][height];
		
		for(int i = 0; i < potentialMatrix.length; i++) {
			for(int j = 0; j < potentialMatrix[0].length; j++) {
				
				Acc_Vector acc = new Acc_Vector(0,0);
				potentialMatrix[i][j] = acc;
				
			}
		}
		
		
		fieldOccupancy = new double[width][height];
		blackHoleList = new ArrayList<Prop>();
		
	
	}
	

	
	public void updateFieldOccupancy(int x, int y, double m) {
		
		fieldOccupancy[x][y] = m;
		
	}
	
	public void updatePotentialMatrix(int x, int y, double m) {
		
		for(int i = 0; i < potentialMatrix.length ; i++) {
			
			for(int j = 0; j < potentialMatrix[0].length; j++) {
				
				double ry = y - j;
			    double rx = x - i;
				double r = java.lang.Math.sqrt(rx*rx + ry*ry); 
				
				double ay = 1000 * m *(ry/r)/(ry*ry + rx*rx);
				double ax = 1000 * m *(rx/r)/(ry*ry + rx*rx);
				Acc_Vector acc = new Acc_Vector(ax, ay);
				potentialMatrix[i][j].add(acc);
			}
		}		
	}
	
	public void clearBlackHoles() {
		
		blackHoleList.clear();
	}
	
	public void resetPotentialMatrix() {
		for(int i = 0; i < potentialMatrix.length; i++) {
			for(int j = 0; j < potentialMatrix[0].length; j++) {
				
				Acc_Vector acc = new Acc_Vector(0,0);
				potentialMatrix[i][j] = acc;
				
			}
		}
	}
	
	
	public void addBlackHole(BlackHole bHole) {
		
		blackHoleList.add(bHole);
		
		int x = bHole.getX_Coord();
		int y = bHole.getY_Coord();
		double m = bHole.getMass();
		
		updateFieldOccupancy(x, y, m);
		updatePotentialMatrix(x,y,m);
			
	}
	
	public Acc_Vector getAcc_Vector(int x, int y) {
		
		return potentialMatrix[x][y];
				
	}
	
	public List<Prop> getBlackHoleList(){
		
		return blackHoleList;
		
	}
	
	
	
	
}*/



package bucky;

import java.util.List;
import java.util.ArrayList;


public class GravitationalField {
	
	private ForceVector [][] fieldMatrix;  //= new accelerationVector[10000][10000];
	private double [][] fieldOccupancy; //= new double [10000][10000];
	private List<Prop> blackHoleList;
	private int width, height;
	
	
	
	
	public GravitationalField(int w, int h) {
		width = w;
		height = h;
		fieldMatrix = new ForceVector[width][height];
		
		for(int i = 0; i < fieldMatrix.length; i++) {
			for(int j = 0; j < fieldMatrix[0].length; j++) {
				
				ForceVector force = new ForceVector(0,0);
				fieldMatrix[i][j] = force;
				
			}
		}
		
		
		fieldOccupancy = new double[width][height];
		blackHoleList = new ArrayList<Prop>();
		
	
	}
	

	
	public void updateFieldOccupancy(int x, int y, double m) {
		
		fieldOccupancy[x][y] = m;
		
	}
	
	public void updatePotentialMatrix(int x, int y, double m) {
		
		for(int i = 0; i < fieldMatrix.length ; i++) {
			
			for(int j = 0; j < fieldMatrix[0].length; j++) {
				
				double ry = y - j;
			    double rx = x - i;
				double r = java.lang.Math.sqrt(rx*rx + ry*ry); 
				
				double fy = 100000000 * m *(ry/r)/(ry*ry + rx*rx);
				double fx = 100000000 * m *(rx/r)/(ry*ry + rx*rx);
				ForceVector force = new ForceVector(fx, fy);
				fieldMatrix[i][j].add(force);
			}
		}		
	}
	

	public void resetPotentialMatrix() {
		for(int i = 0; i < fieldMatrix.length; i++) {
			for(int j = 0; j < fieldMatrix[0].length; j++) {
				
				ForceVector force = new ForceVector(0,0);
				fieldMatrix[i][j] = force;
				
			}
		}
	}
	
	
	public void addBlackHole(BlackHole bHole) {
		
		blackHoleList.add(bHole);
		
		int x = bHole.getX_Coord();
		int y = bHole.getY_Coord();
		double m = bHole.getMass();
		
		updateFieldOccupancy(x, y, m);
		updatePotentialMatrix(x,y,m);
			
	}
	
	public ForceVector getForceVector(int x, int y) {
		
		return fieldMatrix[x][y];
				
	}
	
	public List<Prop> getBlackHoleList(){
		
		return blackHoleList;
		
	}
	
	
	
	
}
