package org.capcaval.ermine.types;

public class IntPoint {

	protected int y;
	protected int x;

	public IntPoint(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
}
