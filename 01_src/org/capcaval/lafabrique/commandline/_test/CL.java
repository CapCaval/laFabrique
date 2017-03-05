package org.capcaval.lafabrique.commandline._test;

import org.capcaval.lafabrique.commandline.Command;
import org.capcaval.lafabrique.commandline.CommandLineAbstractMain;

public class CL extends CommandLineAbstractMain{
	
	static{
		commandList.add(new CL());
		consoleName = "CL";
	}
	
	@Command
	public int add(int... valueNumberlist){
		int sum=0;
		for(int value : valueNumberlist){
			sum = sum + value;
		}
		return sum;
	}

	@Command
	public double multi(double... valueNumberlist){
		double sum=1;
		
		if(valueNumberlist.length==0){
			sum = 0;
		}
		
		for(double value : valueNumberlist){
			sum = sum * value;
		}
		return sum;
	}
	
}
