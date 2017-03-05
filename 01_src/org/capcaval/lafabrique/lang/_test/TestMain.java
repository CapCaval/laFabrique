package org.capcaval.lafabrique.lang._test;

import org.capcaval.lafabrique.lang.SystemTools;
import org.capcaval.lafabrique.lang.Version;

public class TestMain {
	
	public String getValue(){
		return "No worries.";
	}

	public static void main(String[] args) {
		Version version =SystemTools.getCurrentJavaVersion();
		System.out.println("Main application." + args[0]);
	}
}
