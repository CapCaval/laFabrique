package org.capcaval.ccoutils.lang._test;

import java.util.Arrays;

import junit.framework.Assert;

import org.capcaval.ccoutils.lang.StringMultiLine;

public class StringMultiLineTest {

	@org.junit.Test
	public void testStringMultiLine(){
		StringMultiLine str = new StringMultiLine("line1", "line2");
		str.addLine("line3", "line4");
		str.addLine("line5");
		
		// check out char by char
		System.out.println(Arrays.toString(str.toString().toCharArray()));
		System.out.println(Arrays.toString("line1\nline2\nline3\nline4\nline5\n".toCharArray()));
		
		Assert.assertEquals("line1\nline2\nline3\nline4\nline5\n", str.toString());
	}
}
