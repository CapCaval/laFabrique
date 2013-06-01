package org.capcaval.ccoutils.lang;

public class StringMultiLine {
	StringBuilder strb = new StringBuilder();
	
	public StringMultiLine(String... lineList){
		this.addLine(lineList);
	}
	
	public void addLine(String... lineList){
		// for each line append it
		for(String lineStr : lineList){
			// add an \n to define the new line
			this.strb.append(lineStr + "\n");
		}
	}
	@Override
	public String toString(){
		return this.strb.toString();
	}
}
