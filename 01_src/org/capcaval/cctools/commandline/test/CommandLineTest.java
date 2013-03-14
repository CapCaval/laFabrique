package org.capcaval.cctools.commandline.test;

import junit.framework.Assert;

import org.capcaval.cctools.commandline.CommandLineComputer;
import org.junit.Test;

import sun.tools.jar.CommandLine;

public class CommandLineTest {

	@Test
	public void commandTest(){
		CommandLineComputer clc = new CommandLineComputer();
		clc.addCommandClass(CommandSample.class);
		
		String result = clc.computeCommandLine("addition", "1", "5");
		Assert.assertEquals("6", result);
	}
	
}
