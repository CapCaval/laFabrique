package org.capcaval.lafabrique.lang;

public class CompareStringResult {
	
	protected boolean isEqual;
	protected int diffIndex;

	CompareStringResult(boolean isEqual){
		this.isEqual = isEqual;
		this.diffIndex = -1;
	}

	
	CompareStringResult(boolean isEqual, int diffIndex){
		this.isEqual = isEqual;
		this.diffIndex = diffIndex;
	}
	
	boolean isEqual(){
		return this.isEqual;
	}
	
	int getDiffIndex(){
		return this.diffIndex;
	}
}
