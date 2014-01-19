package org.capcaval.ccoutils.askii._test;

import org.capcaval.ccoutils.askii.AskiiTools;


public class FromStringMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = AskiiTools.convertStringToAscii("laFabrique");
		System.out.println(s);
		
		s = AskiiTools.convertStringToAscii("a");
		System.out.println(s);
	}

}
