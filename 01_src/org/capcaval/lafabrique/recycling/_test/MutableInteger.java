package org.capcaval.lafabrique.recycling._test;

public class MutableInteger {
	int value;
	
	public MutableInteger(){
		this.value=0;
	}
	
	public MutableInteger(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}

	public void setValue(int value){
		this.value = value;
	}
	
	@Override
	public String toString(){
		return Integer.toString(value);
	}

}
