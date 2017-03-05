package org.capcaval.lafabrique.formattedtext;

public enum Chap {
	none, level1, level2, level3, level4;
	
	@Override
	public String toString(){
		return Type.start + super.toString() + Type.stop;  
	}
}
