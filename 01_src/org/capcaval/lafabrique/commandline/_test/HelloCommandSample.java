package org.capcaval.lafabrique.commandline._test;

import org.capcaval.lafabrique.commandline.Command;

public class HelloCommandSample {

	@Custom
	@Command
	public String hello(){
		return "Hello";
	}
}
