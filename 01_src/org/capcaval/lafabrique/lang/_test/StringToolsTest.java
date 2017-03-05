/*
Copyright (C) 2012 by CapCaval.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package org.capcaval.lafabrique.lang._test;

import java.nio.file.Path;
import java.nio.file.Paths;

import junit.framework.Assert;

import org.capcaval.lafabrique.lang.StringMultiLine;
import org.capcaval.lafabrique.lang.StringTools;
import org.junit.Test;



public class StringToolsTest {
	
	@org.junit.Test
	public void testRemovingChar(){
		String result = StringTools.removeChar("Hello_World@", '_', '@');
		Assert.assertEquals("HelloWorld", result);
	}

	@org.junit.Test
	public void testDeleteChars(){
		String result = StringTools.deleteCharacters("1234567890eeee", 5, 10);
		Assert.assertEquals("12345eeee", result);
	}

	@org.junit.Test
	public void testInsertChars(){
		String result = StringTools.insertCharacters("123456789", "toto", 4);
		Assert.assertEquals("1234toto56789", result);
	}

	@org.junit.Test
	public void testMultiLine(){
		String result = StringTools.multiLineString(
				"ligne1",
				"ligne2",
				"ligne3",
				"ligne4",
				"ligne5");
		Assert.assertEquals("ligne1\nligne2\nligne3\nligne4\nligne5\n", result);
	}

	@org.junit.Test
	public void isExclusiveMadeOfTest(){
		// assert that all characters are made of the exclusive one
		Assert.assertTrue(StringTools.isExclusiveMadeOf("abcdabcdabcd", "abcd"));
		// assert that all characters are NOT made of the exclusive one
		Assert.assertFalse(StringTools.isExclusiveMadeOf("abcdabcdabcEd", "abcd"));
	}
	
	@org.junit.Test
	public void formatToCharWidthTest(){
		String formattedStr = StringTools.formatToCharWidth("1234567890123456", 5);
		Assert.assertEquals("12345\n67890\n12345\n6", formattedStr);
	}

	@org.junit.Test
	public void formatToCharWidthAndParamTest(){
		String formattedStr = StringTools.formatToCharWidth("1234567890123456", 5, "A\n");
		Assert.assertEquals("12345A\n67890A\n12345A\n6", formattedStr);
	}

	
	@org.junit.Test
	public void formatToMultLineCharWidthTest(){
		String[] ml = new String[]{"abcd", "1234567890123456"};
		String formattedStr = StringTools.formatMultiLineToCharWidth(ml, 5);
		Assert.assertEquals("abcd\n12345\n67890\n12345\n6\n", formattedStr);
	}

	@org.junit.Test
	public void repeatStringTest(){
		String repeatedStr = StringTools.repeatString("123", 3);
		Assert.assertEquals("123123123", repeatedStr);
	}

	@org.junit.Test
	public void removeStartTest(){
		// normal use
		String result = StringTools.removeStart("yes", "yesyesHello");
		Assert.assertEquals("yesHello", result);
		
		// faulty use
		result = StringTools.removeStart("Hell", "yesyesHello");
		Assert.assertEquals("yesyesHello", result);
	}

	
	@org.junit.Test
	public void removeEndTest(){
		// normal use
		String result = StringTools.removeEnd("Hello", "yesyesHello");
		Assert.assertEquals("yesyes", result);
		
		// faulty use
		result = StringTools.removeEnd("yes", "yesyesHello");
		Assert.assertEquals("yesyesHello", result);
	}
	
	
	@org.junit.Test
	public void removeLastLineTest(){
		// normal use
		String result = StringTools.removeLastLine("line1\nline2\nline3\nline4", "\n");
		Assert.assertEquals("line1\nline2\nline3\n", result);

		result = StringTools.removeLastLine("line1\nline2\nline3\nline4");
		Assert.assertEquals("line1\nline2\nline3\n", result);
	}
	
	
	@org.junit.Test
	public void getFirstAndLastTest(){
		String name = "one.two.three.four";
		
		// test first and last
		Assert.assertEquals("one", StringTools.getFirstElement(".", name));
		Assert.assertEquals("four", StringTools.getLastElement(".", name));
		
		
		String other = "oneWtwoWthreeWfour";
		// test first and last
		Assert.assertEquals("one", StringTools.getFirstElement("W", other));
		Assert.assertEquals("four", StringTools.getLastElement("W", other));
	}
	
	@org.junit.Test
	public void transformCharToMajusculeAndMinusculeTest(){
		String name = "coolName";
		
		// test to upper case
		Assert.assertEquals("CoolName", StringTools.transformCharToUpperCase(name, 0));

		// test to lower case
		Assert.assertEquals("coolname", StringTools.transformCharToLowerCase(name, 4));
	}
	
	@org.junit.Test
	public void getLineArrayTest(){
		String test = "line1\nline2\nline3\nline4";
		String[] array = StringTools.getLineArray(test);
		
		Assert.assertEquals("line1", array[0]);
		Assert.assertEquals("line2", array[1]);
		Assert.assertEquals("line3", array[2]);
		Assert.assertEquals("line4", array[3]);
	}
	
	@org.junit.Test
	public void getLineNumberTest(){
		String test = "line1\nline2\nline3\nline4";
		String line3 = StringTools.getLineNumber(3, test);
		
		Assert.assertEquals("line3", line3);
		
		// test error
		try{
			StringTools.getLineNumber(6, test);
			Assert.fail("Shall go here, exception shall be catched.");
		}catch(Exception e){
			Assert.assertTrue(true);
		}
		
		try{
			StringTools.getLineNumber(0, test);
			Assert.fail("Shall go here, exception shall be catched.");
		}catch(Exception e){
			Assert.assertTrue(true);
		}
	}

	@org.junit.Test
	public void getLastLineTest(){
		String test = "line1\nline2\nline3\nline4";
		String lastLine = StringTools.getFirstLine(test);
		
		Assert.assertEquals("line1", lastLine);
	}

	@org.junit.Test
	public void getFirstLineTest(){
		String test = "line1\nline2\nline3\nline4";
		String lastLine = StringTools.getLastLine(test);
		
		Assert.assertEquals("line4", lastLine);
	}

	
	@org.junit.Test
	public void replaceStringTest(){
		String test = "Jo is a biker. Jo is brown.";
		String result = StringTools.replaceString(test, "Jo", "Bob");
		System.out.println(test);
		System.out.println(result);
		
		Assert.assertEquals("Bob is a biker. Bob is brown.", result);
	}
	
	@Test
	public void removeFirstCharactersPerLineTest(){
		String test = "   Jo is a biker.\n\t  Jo is brown.\n \t\t Jo is tall";
		String result = StringTools.removeFirstCharactersPerLine(test, ' ', '\t');
		System.out.println(test);
		System.out.println(result);
		
		Assert.assertEquals("Jo is a biker.Jo is brown.Jo is tall", result);
	}

	@Test
	public void compareStringForDebug(){
		StringMultiLine src1 = new StringMultiLine(
				"Hello, what a nice day",
				"this is great !",
				"bye bye"
				);
		StringMultiLine src2 = new StringMultiLine(
				"Hello, what a nice day",
				"this is great!",
				"bye bye"
				);
		
		String diff = StringTools.compareStringForDebug(src1.toString(), src2.toString());
		System.out.println(diff);
		
		Assert.assertTrue(diff.contains("position 14"));
		Assert.assertTrue(diff.contains("line 2"));
		
	}

	@Test
	public void shiftWithTabTest(){
		String text = new StringMultiLine(
				"Hello, what a nice day",
				"this is great !",
				"bye bye"
				).toString();
		
		String expectedResult = new StringMultiLine(
				"\tHello, what a nice day",
				"\tthis is great !",
				"\tbye bye"
				).toString();
		
		
		String result = StringTools.shiftTextWithTab(text);
		
		Assert.assertEquals(expectedResult, result);
	}
	
	@Test
	public void removeCharSequenceTest(){
		String str = "__Hello_how__do_you_do_\t___?_";
		
		str = StringTools.removeCharSequence('_', str);
		System.out.println(str);
		
		
		Assert.assertEquals("_Hello_how_do_you_do_\t_?_", str);
		
	}
	
	public static void main(String[] args) {
		Path p = Paths.get("~");
		System.out.println(p.toAbsolutePath().toString());
		System.out.println(p.getClass());
		System.out.println(Path.class.isAssignableFrom(p.getClass()));
	}
	
	
}
