package org.capcaval.ccoutils.formattedtext._test;

import junit.framework.Assert;

import org.capcaval.ccoutils.formattedtext.Chap;
import org.capcaval.ccoutils.formattedtext.FormatText;
import org.junit.Test;

public class FormatTextTest {

	@Test
	public void formatTextTest() {
		FormatText ft = new FormatText();
		String str = ft.format(
				Chap.level1 +	"This is the title",
				Chap.level2 +	"line1",
								"line2",
								"line3",
				Chap.level1 +	"This is the end");
				
		System.out.println(str);
		String expected = "   This is the title\n      line1\n      line2\n      line3\n   This is the end\n";
		
		Assert.assertEquals( expected, str);
	}

	@Test
	public void formatTextLongTest() {
		FormatText ft = new FormatText();
		ft.setColumnWidthInChar(8);

		String str = ft.format(
				Chap.level1 +	"12345678901",
				Chap.level2 +	"123",
								"l",
				Chap.level1 +	"end");
				
		System.out.println(str);
		String expected = "   1234\n   5678\n   901\n      1\n      2\n      3\n      l\n   end\n";
		System.out.println(expected);
		
		Assert.assertEquals( expected, str);
	}

}
