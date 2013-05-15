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
package org.capcaval.ccoutils.lang._test;

import org.capcaval.ccoutils.lang.StringTools;

import junit.framework.Assert;



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

	
}
