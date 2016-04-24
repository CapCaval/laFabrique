package ccoutils.application;

import org.capcaval.ccoutils.application.ApplicationTools;
import org.capcaval.ccoutils.application.annotations.AppInformation;
import org.capcaval.ccoutils.application.annotations.AppProperty;
import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.commandline.CommandParam;
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
	
	@Command(desc="Command illustrating how to use properties.")
	public String hello(){
		if(this.name == null){
			this.name = SystemTools.readConsoleInput("What is your name?");
		}
		
		return "Hello " + this.name;
	}
	
	
	@Command(desc="Simple command with integer.")
	public int add(
			@CommandParam(name = "a", desc="First value to add.")
			int a, 
			@CommandParam(name = "b", desc="Second value to add.")
			int b){
		return a+b;
	}

	@Command(desc="Simple command with float.")
	public float sub(
			@CommandParam(name = "a", desc="First value to substract.")
			float a, 
			@CommandParam(name = "b", desc="Second value to substract.")
			float b){
		return a-b;
	}
}
