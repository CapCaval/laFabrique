package ccoutils.application;

import org.capcaval.ccoutils.application.ApplicationTools;
import org.capcaval.ccoutils.application.annotations.AppInformation;
import org.capcaval.ccoutils.application.annotations.AppProperty;
import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.common.CommandResult;
import org.capcaval.ccoutils.lang.SystemTools;

@AppInformation(name="Calculator", author = "FinistJUG", about="This is a great calculator!")
public class Calc {
	
	@AppProperty(persistence=true)
	private String name2 = null;
	
	public static void main(String[] args) {
		
		CommandResult r = ApplicationTools.runApplication(Calc.class, args);
		System.out.println(r.getReturnMessage());
	}
	
	@Command
	public String hello(){
		if(this.name2 == null){
			this.name2 = SystemTools.readConsoleInput("What is your name?");
		}
		
		return "Hello " + this.name2;
	}
	
	
	@Command
	public int add(int a, int b){
		return a+b;
	}

	@Command
	public float sub(float a, float b){
		return a-b;
	}
}
