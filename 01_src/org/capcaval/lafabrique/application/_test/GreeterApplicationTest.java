package org.capcaval.lafabrique.application._test;

import java.io.IOException;
import java.lang.reflect.Method;

import org.capcaval.lafabrique.application.ApplicationRunner;
import org.capcaval.lafabrique.application.ApplicationTools;
import org.capcaval.lafabrique.commandline._test.CommandHandler;
import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.SystemTools;
import org.junit.Assert;

public class GreeterApplicationTest {
	@org.junit.Test
	public void testErrorCommandNotFound(){
		CommandResult result = ApplicationTools.runApplication(GreeterApp.class, "unknownCommand", "noneParam");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("testErrorCommandNotFound : \n" + result);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		// the command shall not be found
		Assert.assertEquals(Type.COMMAND_NOT_FOUND, result.getType());
		// an human error message shall be available
		Assert.assertTrue( result.toString().contains("can not be found"));
	}

	@org.junit.Test
	public void testGreetWithParameter(){
		CommandResult result = ApplicationTools.runApplication(GreeterApp.class, "greet", "Louis");
		Assert.assertEquals("Hi Louis!", result.toString());
		
	}
	@org.junit.Test
	public void testGreetWithSeveralMethodSignature(){
		// here the greet without parameter should be called
		// and it returns Kajez name
		CommandResult result = ApplicationTools.runApplication(GreeterApp.class, "greet");
		Assert.assertEquals("Hi Jakez!", result.toString());
		
		// now the same method name with a parameter
		result = ApplicationTools.runApplication(GreeterApp.class, "greet", "Per");
		Assert.assertEquals("Hi Per!", result.toString());		

	}
	

	@org.junit.Test
	public void testGreetPersistence(){
		// set jojo as parameter on a persistent property
		String str = SystemTools.executeClassInAnotherJVM(GreeterApp.class, "hello", "jojo");
		Assert.assertEquals("Hello jojo!", str);

		// now jojo is saved inside the property file
		String propertiesStr = "";

		propertiesStr = FileTools.readStringfromFile("Greeter.properties");
		Assert.assertTrue(propertiesStr.contains("name=jojo"));
			
		// test without parameter the persistent property jojo shall be used
		str = SystemTools.executeClassInAnotherJVM(GreeterApp.class, "hello");
		Assert.assertEquals("Hello jojo!", str);
		

		System.out.println("finished");
	}

	
	@org.junit.Test
	public void testGreetPersistenceCustomFile(){
		// TODO ------------------------------------------
		// TODO add a dynamic setter of property file
		// TODO ------------------------------------------
		
		// set jojo as parameter on a persistent property
		String str = SystemTools.executeClassInAnotherJVM(GreeterApp.class, "hello", "juju");
		Assert.assertEquals("Hello juju!", str);

		// now jojo is saved inside the property file
		String propertiesStr = "";

		propertiesStr = FileTools.readStringfromFile("Greeter.properties");
		Assert.assertTrue(propertiesStr.contains("name=juju"));
			
		// test without parameter the persistent property jojo shall be used
		str = SystemTools.executeClassInAnotherJVM(GreeterApp.class, "hello");
		Assert.assertEquals("Hello juju!", str);
	}

	
	
	@org.junit.Test
	public void testGreetWithNoParameter(){
		// Make sure that the test is clean
		// Clean all parameters persistence
		ApplicationTools.cleanApplicationProperties(GreeterApp.class);
		
		Assert.assertTrue(FileTools.isFileExist("Greeter.properties")==false);
		
		CommandResult result = ApplicationTools.runApplication(GreeterApp.class, "hello");
		Assert.assertEquals("Hello unknown!", result.toString());
	}

	@org.junit.Test
	public void testSimpleAplicationTestHelp(){
		CommandResult result = ApplicationTools.runApplication(GreeterApp.class, "help");
		
		Assert.assertTrue(result.toString().contains("Version :"));
		Assert.assertTrue(result.toString().contains("About:"));
		
		System.out.println(result.toString());
	}
	
	@org.junit.Test
	public void applicationRunnerTest(){
		ApplicationRunner ar = ApplicationTools.newApplicationRunner();
		ar.addCommandHandler( TestCommand.class, new CommandHandler(){
			@Override
			public Object[] handle(Object proxy, Method method, Object[] paramArray) {
				
				Object result = null;
				String[] returnObjectArray = new String[1];
				
				try {
					result = method.invoke(proxy, paramArray);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				returnObjectArray[0] = ((String)result) + " result!";
				
				return returnObjectArray;
			}});
		
		CommandResult cr = ar.run( GreeterApp.class, new String[]{"customCommand"});
		
		Assert.assertEquals("custom result!", cr.toString());
	}
}
