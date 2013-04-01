package org.capcaval.ccoutils.lang;



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

}
