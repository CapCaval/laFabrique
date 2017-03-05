package org.capcaval.lafabrique.askii._test;

import org.capcaval.lafabrique.askii.AskiiTools;


public class FromStringMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = AskiiTools.convertStringToAscii("laFabrique");
		//System.out.println(s);
		
		s = AskiiTools.convertStringToAscii("ccOutils", "@,. ", 80);
		System.out.println(s);
	}

}
