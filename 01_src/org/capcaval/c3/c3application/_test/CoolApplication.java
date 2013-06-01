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
package org.capcaval.c3.c3application._test;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.capcaval.c3.c3application.C3Application;
import org.capcaval.c3.c3application.C3ApplicationFullState;
import org.capcaval.c3.c3application.annotations.AppProperty;

public class CoolApplication extends C3Application {

	enum MyEnum {
		value1, value2, value3
	};

	@AppProperty(comment = "name of the writter")
	String name = "Rob Alford";

	@AppProperty(comment = "existing Path - no error")
	Path existingPath = FileSystems.getDefault().getPath("src");

	@AppProperty(comment = "existing Path - no error")
	MyEnum value = MyEnum.value1;

	@AppProperty(comment = "primitive value", min = 3, max = 7)
	int intValue1 = 5;

	@AppProperty(comment = "object Value")
	Integer integerValue2 = 6;

	@AppProperty(comment = "object Value")
	double doubleValue1 = 0.789;

	@AppProperty(comment = "object Value")
	Double doubleValue2 = 0.789;

	@AppProperty(comment = "object Value")
	float floatValue1 = -0.789f;

	@AppProperty
	Path path = FileSystems.getDefault().getPath("src/org/capcaval/app/cclicenseinjector/templates");

	@Override
	public void notifyApplicationToBeRun(String propertiesDescrition,
			String componentsDescription) {
		
	}

	@Override
	public void notifyApplicationToBeClosed() {
		// TODO Auto-generated method stub
		
	}






}
