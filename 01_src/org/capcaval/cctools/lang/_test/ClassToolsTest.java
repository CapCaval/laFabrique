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
package org.capcaval.cctools.lang._test;

import org.capcaval.cctools.lang.ClassTools;
import org.capcaval.cctools.lang.StringTools;

import junit.framework.Assert;



public class ClassToolsTest {
	
	public class DefaultClass{}
	public class NoParameterCtorClass{
		public NoParameterCtorClass(){}
	}
	public class OnlyParameterCtorClass{
		public OnlyParameterCtorClass(int param1, int param2){}
	}
	public class MixParameterCtorClass{
		public MixParameterCtorClass(){}
		public MixParameterCtorClass(int param1, int param2){}
	}
	
	
	@org.junit.Test
	public void testDifferentTypeOfClass(){
		// default constructor when undefined
		boolean isContainsNoParameterConstructor = ClassTools.containNoArgumentConstructor(DefaultClass.class);
		Assert.assertEquals(true, isContainsNoParameterConstructor);
	
		// constructor with no parameter 
		isContainsNoParameterConstructor = ClassTools.containNoArgumentConstructor(NoParameterCtorClass.class);
		Assert.assertEquals(true, isContainsNoParameterConstructor);
	
		// constructor with with parameters 
		isContainsNoParameterConstructor = ClassTools.containNoArgumentConstructor(OnlyParameterCtorClass.class);
		Assert.assertEquals(false, isContainsNoParameterConstructor);
		
		// constructor with mix parameters 
		isContainsNoParameterConstructor = ClassTools.containNoArgumentConstructor(MixParameterCtorClass.class);
		Assert.assertEquals(true, isContainsNoParameterConstructor);

	}

}
