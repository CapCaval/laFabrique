package org.capcaval.ccoutils.commandline._test;

import java.util.List;

import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.commandline.CommandParam;

public class CommandSample {
	
	@Command
	public void compile(){
		System.out.println("compile");
	}
//	@Command
	public int addition(
			@CommandParam(name="firstNumber", desc="First number of the addition")
			int a, 
			@CommandParam(name="secondNumber", desc="second number of the addition")
			int b){
		int result = a+ b;
		System.out.println("addition " + a + "+" + b + "=" + result);
		return result;
	}

	@Command
	public double addition(
			@CommandParam(name="List of numbers", desc="All the numbers will be added.")
			double... valueList)
			{
		double returnValue=0;
		for(double value : valueList){
			returnValue = returnValue + value;
		}
		return returnValue;
	}

	
	@Command 
	public City getCity(City city){
		return city;
	}
	
}
