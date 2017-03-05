package org.capcaval.ermine.layout;

public class RefPoint<T> {
	protected RefPointEnum refPoint;
	protected int percentX;
	protected int percentY;
	protected int deltaX;
	protected int deltaY;
	protected T node;
	
	public RefPoint(T node, RefPointEnum refPoint, int deltaX, int deltaY) {
		this.node = node;
		this.refPoint = refPoint;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public RefPoint(T node, int percentX, int percentY, int deltaX, int deltaY) {
		this.node = node;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.percentX = percentX;
		this.percentY = percentY;

	}
	
	public T getNode() {
		return this.node;
	}
	public RefPointEnum getRefPoint() {
		return this.refPoint;
	}
	public int getDeltaX() {
		return this.deltaX;
	}
	public int getDeltaY() {
		return this.deltaY;
	}

	public int getPercentX() {
		return this.percentX;
	}
	public int getPercentY() {
		return this.percentY;
	}

	@Override
	public String toString(){
		return "%x" + this.percentX + " %y" + this.percentY + " dx" + this.deltaX + " dy" + this.deltaY;
	}
	
}
