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
package org.capcaval.c3.componentmanager._test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.capcaval.c3.application.Application;
import org.capcaval.c3.component.annotation.UsedService;
import org.capcaval.c3.componentmanager._test.TestConsummer.TestConsummerService;
import org.capcaval.c3.componentmanager._test.TestConsummer.imp.TestConsummerImpl;
import org.capcaval.c3.componentmanager._test.TestProducer.impl.TestProducerImpl;
import org.junit.Test;

public class ComponentServiceTest {

	class ApplicationTest extends Application{
		
		@UsedService
		public TestConsummerService tcs;
		
		@Override
		public void notifyApplicationToBeRun(String applicationDescrition,
				String componentsDescription) {
			System.out.println(applicationDescrition);
			System.out.println(componentsDescription);
		}

		@Override
		public void notifyApplicationToBeClosed() {
			System.out.println("Bye bye");
		}};
	
	@Test
	public void ServiceTest() {

		ApplicationTest app = new ApplicationTest();
			
		try{
			app.launchApplication(new String[0], 
					TestConsummerImpl.class, 
					TestProducerImpl.class);
			int value = 1000;
			app.tcs.setValue(value);
			
			Assert.assertEquals(value, app.tcs.getValue());
		}catch( Exception e){
			Assert.assertFalse(true);
		}
		
	}
	@Test
	public void ServiceEvent() {

		ApplicationTest app = new ApplicationTest();
			
		try{
			app.launchApplication(new String[0], 
					TestConsummerImpl.class, 
					TestProducerImpl.class);
			int value = 1000;
			app.tcs.setValue(value);
			
			Assert.assertEquals(value, app.tcs.getValue());
		}catch( Exception e){
			Assert.assertFalse(true);
		}
		
	}

}
