package org.capcaval.cctools.commandline.test;

import org.capcaval.cctools.commandline.Command;

public class CommandSample {
	
	@Command
	public void compile(){
		System.out.println("compile");
	}
	@Command
	public int addition(int a, int b){
		int result = a+ b;
		System.out.println("addition " + a + "+" + b + "=" + result);
		return result;
	}
}
