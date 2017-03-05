package org.capcaval.lafabrique.lang;

public class StringMultiLine {
	StringBuilder strb = new StringBuilder();
	
	public StringMultiLine(String... lineList){
		this.addLine(lineList);
	}
	
	public void addLine(String... lineList){
		if(lineList ==null){
			return;
		}
		
		// for each line append it
		for(int i=0; i<lineList.length; i++){
			// get the string
			String lineStr = lineList[i];
			// if not the first add a \n
			if(strb.length()>0){
				this.strb.append("\n");
			}
			
			// add an \n to define the new line
			this.strb.append(lineStr);
			//
		}
	}
	@Override
	public String toString(){
		String str = this.strb.toString();
		
		if(this.strb.length() == 0){
			str = ""; // return an empty string instead of null
		}
		
		// return value 
		return str;
	}
}
