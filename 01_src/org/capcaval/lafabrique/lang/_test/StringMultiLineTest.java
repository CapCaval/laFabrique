package org.capcaval.lafabrique.lang._test;

import java.util.Arrays;

import junit.framework.Assert;

import org.capcaval.lafabrique.lang.StringMultiLine;

public class StringMultiLineTest {

	@org.junit.Test
	public void testStringMultiLine(){
		StringMultiLine str = new StringMultiLine("line1", "line2");
		str.addLine("line3", "line4");
		str.addLine("line5");
		
		// check out char by char
		System.out.println(Arrays.toString(str.toString().toCharArray()));
		System.out.println(Arrays.toString("line1\nline2\nline3\nline4\nline5".toCharArray()));
		
		Assert.assertEquals("line1\nline2\nline3\nline4\nline5", str.toString());
	}
	
	@org.junit.Test
	public void testStringSpecialCasesMultiLine(){
		StringMultiLine str = new StringMultiLine((String[])null);
		Assert.assertEquals("", str.toString());
		str.addLine((String[])null);
		Assert.assertEquals("", str.toString());
	}
	
	public static void main(String[] args) {
		StringMultiLine str = new StringMultiLine(
				"       @@@@       ",
				"    @@@@@@@@@@    ",
				"   @@@@@@@@@@@@   ",
				"  @@@  @@@@  @@@  ",
				"  @@@  @@@@  @@@  ",
				"  @@@  @@@@  @@@  ",
				"  @@@@@@@@@@@@@@  ",
				"  @@@@@@@@@@@@@@  ",
				"   @@@@@@@@@@@@   ",
				"    @@      @@    ",
				"     @@@  @@@     ",
				"       @@@@       ");
		System.out.println(str);
	}	
}
