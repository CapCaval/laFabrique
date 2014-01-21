package ccoutils.application;

import org.capcaval.ccoutils.application.ApplicationTools;
import org.capcaval.ccoutils.application.annotations.AppInformation;
import org.capcaval.ccoutils.application.annotations.AppProperty;
import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.common.CommandResult;
import org.capcaval.ccoutils.lang.SystemTools;

@AppInformation(author = "CapCaval.org")
public class AppSample {
	
	@AppProperty(persistence=true)
	private String name = null;
	
	public static void main(String[] args) {
		
		CommandResult r = ApplicationTools.runApplication(AppSample.class, args);
		System.out.println(r.getReturnMessage());
	}
	
	@Command
	public String hello(){
		if(this.name == null){
			this.name = SystemTools.readConsoleInput("What is your name?");
		}
		
		return "Hello " + this.name;
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
