package org.capcaval.ccoutils.application._test;

import org.capcaval.ccoutils.application.annotations.AppInformation;
import org.capcaval.ccoutils.application.annotations.AppProperty;
import org.capcaval.ccoutils.application.annotations.DefaultApplicationCommand;
import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.commandline.CommandParam;
import org.capcaval.ccoutils.lang.SystemTools;

@AppInformation (
		name="Greeter",
//		asciiLogoGradient="@;. ",
//		helpWidthInChar=120,
		author="CapCaval.org",
//		about= {"This appplication is able to perform a greetings to anyone. It is very simple and fast to write. It provides an automatic help.",
//				"It also remember your names if you run it at least twice."
//				},
		version="0.0_7"
		)
public class GreeterApp{

	@AppProperty(comment = "Greeter apllication name", persistence = true)
	String name;
	
	@DefaultApplicationCommand
	@Command
	public void run(){
		if(this.name == null){
			this.name = SystemTools.readConsoleInput("What is your name?");
		}
		// Let's greet.
		System.out.println("Hello " + this.name +  "!");
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
		return new String("Hello " + this.name +  "!");
	}

	@Command
	public String greet(){
		// use the persistent one of any
		return this.greet(this.name);
	}

	
}
