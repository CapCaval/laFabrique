package org.capcaval.ccoutils.jbitascii.test;

import org.capcaval.ccoutils.jbitascii.BitmapAsciiTools;


public class FromStringMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = BitmapAsciiTools.convertStringToAscii("laFabrique");
		System.out.println(s);
		
		s = BitmapAsciiTools.convertStringToAscii("Greeter");
		System.out.println(s);
	}

}
