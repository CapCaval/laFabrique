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
package test.simpleserviceandevent;

import org.capcaval.c3.component.ComponentEventSubscribe;
import org.capcaval.c3.componentmanager.ComponentManager;

import test.simpleserviceandevent.hellomachine.HelloMachineEvent;
import test.simpleserviceandevent.hellomachine.HelloMachineServices;
import test.simpleserviceandevent.hellomachine.impl.HelloMachineImpl;


public class HelloMachineMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ComponentManager cm = ComponentManager.componentManager;
		
		cm.registerComponent(HelloMachineImpl.class);
		
		cm.startApplication();
		
		ComponentEventSubscribe<HelloMachineEvent> tces = cm.getComponentEventSubscribe(HelloMachineEvent.class);
		
		tces.subscribe(new HelloMachineEvent() {
			
			@Override
			public void notifyHelloMessageChanged(String updatedmessage) {
				System.out.println("Message updated : " + updatedmessage);
			}
		});
	
		
		HelloMachineServices hms = cm.getComponentService(HelloMachineServices.class);
		
		String name = hms.getNameToSalute();
		
		System.out.println("Default name : " + name);
		
		hms.setNameToSalute("world");

		cm.stopApplication();
	}

}
