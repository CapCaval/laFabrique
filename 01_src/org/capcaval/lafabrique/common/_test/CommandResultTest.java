package org.capcaval.lafabrique.common._test;

import junit.framework.Assert;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.lang.StringMultiLine;

public class CommandResultTest {
	@org.junit.Test
	public void commandResultAddTest() {
		CommandResult cr = new CommandResult("This is a comment");
		Assert.assertEquals("This is a comment", cr.toString());
		Assert.assertEquals(Type.NONE, cr.getType());
		//Assert.assertEquals(null, cr.getErrorType());
	}
	
	
	@org.junit.Test
	public void commandResultErrorTest() {
		CommandResult.applicationName.set("HypeApp");
		CommandResult cr = new CommandResult(Type.ERROR,  "This is a comment");
		Assert.assertEquals(Type.ERROR, cr.getType());
		//Assert.assertEquals(ErrorType.COMMAND_NOT_FOUND, cr.getErrorType());
		Assert.assertEquals("[HypeApp] ERROR : This is a comment", cr.toString());
	}

	@org.junit.Test
	public void commandResultWarningTest() {
		CommandResult.applicationName.set("HypeApp");
		CommandResult cr = new CommandResult(Type.WARNING,  "This is a comment");
		Assert.assertEquals(Type.WARNING, cr.getType());
		//Assert.assertEquals(null, cr.getErrorType());
		Assert.assertEquals("[HypeApp] WARNING : This is a comment", cr.toString());
	}

	@org.junit.Test
	public void commandResultNullTypeTest() {
		CommandResult.applicationName.set("HypeApp");
		CommandResult cr = new CommandResult((Type)null,  "This is a comment");
		Assert.assertEquals(null, cr.getType());
		// Assert.assertEquals(null, cr.getErrorType());
		Assert.assertEquals("This is a comment", cr.toString());
	}

	@org.junit.Test
	public void commandResultMultiMessageTest() {
		CommandResult.applicationName.set("HypeApp");
		CommandResult cr = new CommandResult(Type.INFO,  "This is a comment");
		cr.addMessage( Type.ERROR, "This is an error comment");
		cr.addMessage("       This is a error comment on another line");
		cr.addMessage(Type.WARNING, "This is a warning error comment");

		StringMultiLine sm = new StringMultiLine(
				"[HypeApp] INFO : This is a comment",
				"[HypeApp] ERROR : This is an error comment",
				"       This is a error comment on another line",
				"[HypeApp] WARNING : This is a warning error comment"
				);
		
		Assert.assertEquals(sm.toString(), cr.toString());
		Assert.assertEquals(Type.ERROR, cr.getType());
		//Assert.assertEquals(ErrorType.DOMAIN, cr.getErrorType());
	}
	
	@org.junit.Test
	public void commandResultConcatMessageTest() {
		CommandResult.applicationName.set("HypeApp");
		CommandResult cr = new CommandResult(Type.INFO,  "This is a comment");
		cr.addMessage("       This is an info comment on another line");
		cr.addMessage(Type.WARNING, "This is a warning error comment");

		CommandResult cr2 = new CommandResult(Type.ERROR,  "This is an error comment");
		
		cr.concat(cr2);
		
		StringMultiLine sm = new StringMultiLine(
				"[HypeApp] INFO : This is a comment",
				"       This is an info comment on another line",
				"[HypeApp] WARNING : This is a warning error comment",
				"[HypeApp] ERROR : This is an error comment"
				);
		
		
		
		Assert.assertEquals(sm.toString(), cr.toString());
		Assert.assertEquals(Type.ERROR, cr.getType());
		//Assert.assertEquals(ErrorType.DOMAIN, cr.getErrorType());
	}

	
}
