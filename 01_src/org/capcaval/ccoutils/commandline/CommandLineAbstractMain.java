package org.capcaval.ccoutils.commandline;

import java.util.ArrayList;
import java.util.List;

public class CommandLineAbstractMain {
	
	public static List<Object> commandList = new ArrayList<>();
	public static String consoleName;
	

	public static void main(String[] args) {
		
		CommandLineComputer clc = new CommandLineComputer(consoleName, "-");
		
		// add all the registered command
		for(Object object : commandList){
			clc.addCommandClass(object);
		}
		
		String resultStr = clc.computeCommandLine(args);
		System.out.println(resultStr);
	}

}
