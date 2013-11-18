package org.capcaval.ccoutils.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class SystemTools {

	public enum OSType {Windows, Linux};
	
	private static String osName = System.getProperty("os.name").toLowerCase();
	
	public static OSType getOSType() {
		OSType type = OSType.Linux;
		
		if(osName.contains("win")){
			type = OSType.Windows;
		}
		
		return type;
	}
	
	public static String readConsoleInput(String question) {
		// display the question
		System.out.println(question);
		
		String message = null;
	    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	    
	    try {
			message = stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		return message;
	}


}
