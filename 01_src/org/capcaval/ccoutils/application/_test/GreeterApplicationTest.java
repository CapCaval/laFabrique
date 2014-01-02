package org.capcaval.ccoutils.application._test;

import org.capcaval.ccoutils.application.ApplicationTools;
import org.capcaval.ccoutils.common.CommandResult;
import org.junit.Assert;

public class GreeterApplicationTest {
	@org.junit.Test
	public void testGreatWithParameter(){
		CommandResult result = ApplicationTools.runApplication(GreeterApp.class, "greet", "Louis");
		Assert.assertEquals("Hello Louis!", result.getReturnMessage());
		
	}
	
	@org.junit.Test
	public void testGreetWithPersistence(){
		CommandResult result = ApplicationTools.runApplication(GreeterApp.class, "greet", "Jacques");
		Assert.assertEquals("Hello Jacques!", result.getReturnMessage());
		
		// Jacques is now persistent, relaunch the command without parameter
		result = ApplicationTools.runApplication(GreeterApp.class, "greet");
		Assert.assertEquals("Hello Jacques!", result.getReturnMessage());

		// Clean all parameters persistence
		ApplicationTools.cleanApplicationProperties(GreeterApp.class);
		result = ApplicationTools.runApplication(GreeterApp.class, "greet");
		Assert.assertEquals("Hello !", result.getReturnMessage());
	}

	@org.junit.Test
	public void testSimpleAplicationTestHelp(){
		CommandResult result = ApplicationTools.runApplication(GreeterApp.class, new String[]{"help"});
		
		System.out.println(result.getReturnMessage());
	}
	
	public static void main(String[] args) {
		CommandResult result = ApplicationTools.runApplication(GreeterApp.class, args);
		System.out.println(result.getReturnMessage());
	}

}
