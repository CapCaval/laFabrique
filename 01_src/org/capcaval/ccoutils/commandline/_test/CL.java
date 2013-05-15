package org.capcaval.ccoutils.commandline._test;

import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.commandline.CommandLineAbstractMain;

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
