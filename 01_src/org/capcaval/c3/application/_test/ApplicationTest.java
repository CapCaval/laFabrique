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
package org.capcaval.c3.application._test;

import java.nio.file.FileSystems;

import junit.framework.Assert;

import org.capcaval.c3.application._test.CoolApplication.MyEnum;
import org.junit.Test;

public class ApplicationTest {

	@Test
	public void testSetPropery() {
		CoolApplication app = new CoolApplication();
		
	
		System.setProperty("name", "ROb Halford is the best");
		System.setProperty("value", "value3");
		System.setProperty("intValue1", "3");
		System.setProperty("doubleValue1", "6.789");
		System.setProperty("doubleValue2", "2.789");
		System.setProperty("floatValue1", "-2.789");
		//System.setProperty("path", "thisPathDoesNotExist");
		app.launchApplication(null);
		
		Assert.assertEquals("ROb Halford is the best", app.name);
		Assert.assertEquals(MyEnum.value3, app.value);
		Assert.assertEquals(3, app.intValue1);
		Assert.assertEquals(6.789, app.doubleValue1);
		Assert.assertEquals(2.789, app.doubleValue2);
		Assert.assertEquals(-2.789f, app.floatValue1);
		Assert.assertEquals(FileSystems.getDefault().getPath(
				"src/org/capcaval/app/cclicenseinjector/templates").equals( app.path), true);
	}
	
	@Test
	public void testSetProperyWithError() {
		CoolApplication app = new CoolApplication();
		
		System.setProperty("value", "value981");
		System.setProperty("intValue1", "AAA");
		System.setProperty("doubleValue1", "6.789");
		System.setProperty("doubleValue2", "2.789");
		System.setProperty("floatValue1", "-2.789");
		app.launchApplication(null);
		
		Assert.assertEquals(MyEnum.value1, app.value);
		Assert.assertEquals(6, app.intValue1);
		Assert.assertEquals(6.789, app.doubleValue1);
//		Assert.assertEquals(2.789, app.doubleValue2);
//		Assert.assertEquals(-2.789f, app.floatValue1);
//		Assert.assertEquals(FileSystems.getDefault().getPath(
//				"src/org/capcaval/app/cclicenseinjector/templates").equals( app.path), true);
	}

}
