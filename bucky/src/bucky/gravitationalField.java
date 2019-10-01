package bucky;

import java.util.List;
import java.util.ArrayList;


public class gravitationalField {
	
	
	
	private acc_Vector [][] potentialMatrix;  //= new accelerationVector[10000][10000];
	private double [][] fieldOccupancy; //= new double [10000][10000];
	private List<blackHole> blackHoleList;
	private int width, height;
	
	
	
	
	public gravitationalField(int w, int h) {
		width = w;
		height = h;
		potentialMatrix = new acc_Vector[width][height];
		
		for(int i = 0; i < potentialMatrix.length; i++) {
			for(int j = 0; j < potentialMatrix[0].length; j++) {
				
				acc_Vector acc = new acc_Vector(0,0);
				potentialMatrix[i][j] = acc;
				
			}
		}
		
		
		fieldOccupancy = new double[width][height];
		blackHoleList = new ArrayList<blackHole>();
		
	
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
				acc_Vector acc = new acc_Vector(ax, ay);
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
				
				acc_Vector acc = new acc_Vector(0,0);
				potentialMatrix[i][j] = acc;
				
			}
		}
	}
	
	
	public void addBlackHole(blackHole bHole) {
		
		blackHoleList.add(bHole);
		
		int x = bHole.getX_Coord();
		int y = bHole.getY_Coord();
		double m = bHole.getMass();
		
		updateFieldOccupancy(x, y, m);
		updatePotentialMatrix(x,y,m);
			
	}
	
	public acc_Vector getAcc_Vector(int x, int y) {
		
		return potentialMatrix[x][y];
				
	}
	
	public List<blackHole> getBlackHoleList(){
		
		return blackHoleList;
		
	}
	
	
	
	
}
