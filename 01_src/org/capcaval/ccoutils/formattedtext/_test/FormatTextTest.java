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
		ft.setColumnWidthInChar(6);
		String str = ft.format(
				Chap.level1 +	"12345678901",
				Chap.level2 +	"12345678",
								"l3",
				Chap.level1 +	"end");
				
		System.out.println(str);
		String expected = "   123\n   456\n   789\n   01\n      123\n      456\n      78\n      l3\n   end\n";
		System.out.println(expected);
		
		Assert.assertEquals( expected, str);
	}

}
