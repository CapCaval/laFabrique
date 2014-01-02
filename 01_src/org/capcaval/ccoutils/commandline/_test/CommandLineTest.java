package org.capcaval.ccoutils.commandline._test;

import junit.framework.Assert;

import org.capcaval.ccoutils.commandline.CommandLineComputer;
import org.junit.Test;


public class CommandLineTest {

	@Test
	public void commandTest(){
		CommandLineComputer clc = new CommandLineComputer();
		clc.addCommandClass(CommandSample.class);
		
		String result = clc.computeCommandLine("addition", "1", "5").getReturnMessage();
		Assert.assertEquals("6.0", result);
		
		// test enum
		result = clc.computeCommandLine("getCity", "City.Paris").getReturnMessage();
		System.out.println(result);
		Assert.assertEquals("City.Paris", result);

	}
	
	@Test
	public void commandListTest(){
		CommandLineComputer clc = new CommandLineComputer();
		clc.addCommandClass(CommandSample.class);
		
		String result = clc.computeCommandLine("addition", "1", "5", "2.2").getReturnMessage();
		System.out.println(result);
		Assert.assertEquals("8.2", result);
	}
	
	@Test
	public void commandErrorTest(){
		CommandLineComputer clc = new CommandLineComputer();
		clc.addCommandClass(CommandSample.class);

		String result = clc.computeCommandLine("addition", "A", "5").getReturnMessage();
		System.out.println(result);
		Assert.assertTrue(result.contains("Error"));
		
		result = clc.computeCommandLine("addition", "A").getReturnMessage();
		System.out.println(result);
		Assert.assertTrue(result.contains("Error"));
	}
	@Test
	public void commandHelpTest(){
		CommandLineComputer clc = new CommandLineComputer("HelpTestCommand", "-");
		clc.addCommandClass(CommandSample.class);
		
		String result = clc.computeCommandLine("help").getReturnMessage();
		System.out.println(result);
		Assert.assertTrue(result.contains("help"));
	}
}
