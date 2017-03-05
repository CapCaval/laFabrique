package org.capcaval.lafabrique.commandline._test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Assert;

import org.capcaval.lafabrique.commandline.CommandLineComputer;
import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.junit.Test;


public class CommandLineTest {

	@Test
	public void commandTest(){
		CommandLineComputer clc = new CommandLineComputer();
		clc.addCommandClass(CommandSample.class);
		
		String result = clc.computeCommandLine("addition", "1", "5").toString();
		Assert.assertEquals("6.0", result);
		
		// test enum
		result = clc.computeCommandLine("getCity", "City.Paris").toString();
		System.out.println(result);
		Assert.assertEquals("City.Paris", result);

	}
	
	@Test
	public void commandListTest(){
		CommandLineComputer clc = new CommandLineComputer();
		clc.addCommandClass(CommandSample.class);
		
		String result = clc.computeCommandLine("addition", "1", "5", "2.2").toString();
		System.out.println(result);
		Assert.assertEquals("8.2", result);
	}
	
	@Test
	public void commandErrorArrayTest(){
		CommandLineComputer clc = new CommandLineComputer();
		clc.addCommandClass(CommandSample.class);

		CommandResult result = clc.computeCommandLine("addition", "A", "5");
		Assert.assertEquals(result.getType(), Type.ERROR);
		System.out.println(result);
		Assert.assertTrue(result.toString().contains("ERROR"));
		Assert.assertTrue(result.toString().contains("index 0"));
		
		System.out.println("-----------------");
		
		result = clc.computeCommandLine("addition", "5", "4", "B");
		Assert.assertEquals(result.getType(), Type.ERROR);
		System.out.println(result);
		Assert.assertTrue(result.toString().contains("ERROR"));
		Assert.assertTrue(result.toString().contains("index 2"));
	}

	@Test
	public void commandErrorTest(){
		CommandLineComputer clc = new CommandLineComputer();
		clc.addCommandClass(CommandSample.class);

		CommandResult result = clc.computeCommandLine("add", "A", "5");
		Assert.assertEquals(Type.ERROR, result.getType());
		System.out.println(result);
		Assert.assertTrue(result.toString().contains("ERROR"));
		Assert.assertTrue(result.toString().contains("index 0"));
		
		System.out.println("-----------------");
		
		result = clc.computeCommandLine("add", "5", "B");
		Assert.assertEquals(result.getType(), Type.ERROR);
		System.out.println(result);
		Assert.assertTrue(result.toString().contains("ERROR"));
		Assert.assertTrue(result.toString().contains("index 1"));
		
		result = clc.computeCommandLine("not a command");
		Assert.assertEquals(result.getType(), Type.COMMAND_NOT_FOUND);
		System.out.println(result);
		Assert.assertTrue(result.toString().contains("can not be found"));

	}

	
	@Test
	public void commandHelpTest(){
		CommandLineComputer clc = new CommandLineComputer("HelpTestCommand", "-");
		clc.addCommandClass(CommandSample.class);
		
		String result = clc.computeCommandLine("help").toString();
		System.out.println(result);
		Assert.assertTrue(result.contains("help"));
	}
	
	@Test
	public void commandMultiParamTest(){
		CommandLineComputer clc = new CommandLineComputer();
		clc.addCommandClass(CommandSample.class);
		
		String result = clc.computeCommandLine("getMethod").toString();
		Assert.assertEquals("No param return value.", result);
		
		// test enum
		result = clc.computeCommandLine("getMethod", "param").toString();
		System.out.println(result);
		Assert.assertEquals("With param return value.", result);

	}

	@Test
	public void AnnotationCommandHandlerTest(){
		CommandLineComputer clc = new CommandLineComputer();
		
		clc.addCommandHandler(Custom.class, new CommandHandler(){

			@Override
			public Object[] handle(Object instance, Method method, Object[] paramArray) {
				// call the next call
				Object ret = null;
				try {
					ret = method.invoke(instance, paramArray);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return new String[]{ret.toString() + " you."};
			}
		});
		clc.addCommandClass(HelloCommandSample.class);
		
		String result = clc.computeCommandLine("hello").toString();
		Assert.assertEquals("Hello you.", result);
	}


}
