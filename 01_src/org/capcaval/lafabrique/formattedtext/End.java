package org.capcaval.lafabrique.formattedtext;

public enum End {
	
	bold, italic;
	
	@Override
	public String toString(){
		return Type.start + super.toString() + Type.stop;  
	}
}

