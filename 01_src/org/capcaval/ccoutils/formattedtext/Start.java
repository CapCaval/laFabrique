package org.capcaval.ccoutils.formattedtext;

public enum Start {
	bold, italic;
	
	@Override
	public String toString(){
		return Type.start + super.toString() + Type.stop;  
	}
}
