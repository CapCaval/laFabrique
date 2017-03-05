package org.capcaval.lafabrique.commandline._test;

import org.capcaval.lafabrique.commandline.Command;
import org.capcaval.lafabrique.commandline.CommandParam;

public class CommandSample {
	
	@Command
	public void compile(){
		System.out.println("compile");
	}
	@Command
	public int add(
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

	@Command 
	public String getMethod(){
		return "No param return value.";
	}

	@Command 
	public String getMethod(String param){
		return "With param return value.";
	}
	
}
