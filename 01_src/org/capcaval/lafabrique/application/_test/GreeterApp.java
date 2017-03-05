package org.capcaval.lafabrique.application._test;

import org.capcaval.lafabrique.application.ApplicationTools;
import org.capcaval.lafabrique.application.annotations.AppInformation;
import org.capcaval.lafabrique.application.annotations.AppProperty;
import org.capcaval.lafabrique.application.annotations.DefaultApplicationCommand;
import org.capcaval.lafabrique.commandline.Command;
import org.capcaval.lafabrique.commandline.CommandParam;
import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.lang.SystemTools;

@AppInformation (
		name="Greeter",
//		asciiLogoGradient="@;. ",
//		helpWidthInChar=120,
		author="CapCaval.org",
		about= {"This appplication is able to perform a greetings to anyone. It is very simple and fast to write. It provides an automatic help.",
				"It also remember your names if you run it at least twice."
				},
		version="0.0_7"
		)
public class GreeterApp{

	@AppProperty(comment = "Greeter apllication name", persistence = true)
	String name="unknown";
	
	@DefaultApplicationCommand
	@Command
	public String hello(){
		// use the default or the persistent or the property defined name
		return this.hello(this.name);
	}
	
	@Command
	public String hello(String name){
		// first keep the new value
		this.name = name;

		// Let's greet.
		return "Hello " + this.name +  "!";
	}
	
	@Command
	public void goodDay(){
		// use the default or the persistent or the property defined name
		this.goodDay(this.name);
	}

	@Command
	public void goodDay(String name){
		if(this.name == null){
			this.name = SystemTools.readConsoleInput("What is your name?");
		}
		// Let's greet.
		System.out.println("G'day " + this.name +  "!");
	}


	
	@Command(desc = "A command to greet people.")
	public String greet(
			@CommandParam (name= "name", desc="name to greet")
			String... nameList){
		// keep the value persistent
		if(nameList.length >= 1){
			this.name = nameList[0];}
		// else use persistent value stored inside this.name
		
		// Let's greet.
		return new String("Hi " + this.name +  "!");
	}

	@Command
	public String greet(){
		// use the persistent one of any
		return this.greet("Jakez");
	}
	
	@TestCommand
	@Command
	public String customCommand(){
		return "custom";
	}
	
	public static void main(String[] args) {
		CommandResult cr = ApplicationTools.runApplication(GreeterApp.class, args);
		System.out.println(cr.toString());
	}

	
}
