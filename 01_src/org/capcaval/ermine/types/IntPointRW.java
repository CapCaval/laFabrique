package org.capcaval.ermine.types;

public class IntPointRW extends IntPoint{

	IntPointRW(int x, int y) {
		super(x, y);
	}

	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}
}
