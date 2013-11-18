package org.capcaval.ccoutils.application._test;

import org.capcaval.ccoutils.application.ApplicationTool;
import org.capcaval.ccoutils.commandline.CommandLineComputer.ComputeResult;
import org.junit.Assert;

public class GreeterApplicationTest {
	@org.junit.Test
	public void testGreatWithParameter(){
		ComputeResult result = ApplicationTool.runApplication(GreeterApp.class, "greet", "Louis");
		Assert.assertEquals("Hello Louis!", result.getReturnMessage());
		
	}
	
	@org.junit.Test
	public void testGreetWithPersistence(){
		ComputeResult result = ApplicationTool.runApplication(GreeterApp.class, "greet", "Jacques");
		Assert.assertEquals("Hello Jacques!", result.getReturnMessage());
		
		// Jacques is now persistent, relaunch the command without parameter
		result = ApplicationTool.runApplication(GreeterApp.class, "greet");
		Assert.assertEquals("Hello Jacques!", result.getReturnMessage());

		// Clean all parameters persistence
		ApplicationTool.cleanApplicationProperties(GreeterApp.class);
		result = ApplicationTool.runApplication(GreeterApp.class, "greet");
		Assert.assertEquals("Hello !", result.getReturnMessage());
	}

	@org.junit.Test
	public void testSimpleAplicationTestHelp(){
		ComputeResult result = ApplicationTool.runApplication(GreeterApp.class, new String[]{"help"});
		
		System.out.println(result.returnMessage);
	}
	
	public static void main(String[] args) {
		ComputeResult result = ApplicationTool.runApplication(GreeterApp.class, args);
		System.out.println(result.returnMessage);
	}

}
