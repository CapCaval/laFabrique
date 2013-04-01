package org.capcaval.ccoutils.lang;

public class ArrayError {

	protected int index;
	protected Exception exception;
	private Object errorSourceObject;

	public ArrayError(int index, Exception exception, Object errorSourceObject) {
		this.index = index;
		this.exception = exception;
		this.errorSourceObject = errorSourceObject;
	}
	
	public int getIndex(){
		return this.index;
	}
	public Exception getException(){
		return this.exception;
	}
	
	public Object getErrorSourceObject(){
		return this.errorSourceObject;
	}

}
