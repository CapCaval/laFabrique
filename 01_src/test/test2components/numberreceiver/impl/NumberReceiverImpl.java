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
package test.test2components.numberreceiver.impl;



import org.capcaval.c3.component.ComponentEventSubscribe;
import org.capcaval.c3.component.ComponentState;
import org.capcaval.c3.component.annotation.ConsumedEvent;
import org.capcaval.c3.component.annotation.EventSubcribe;
import org.capcaval.c3.component.annotation.UsedService;


import test.test2components.numberproducer.NumberProducerEvent;
import test.test2components.numberproducer.NumberProducerService;
import test.test2components.numberreceiver.NumberReceiver;


public class NumberReceiverImpl implements NumberReceiver, ComponentState{

	
	@EventSubcribe
	ComponentEventSubscribe<NumberProducerEvent> numberEventSubscribe;
	
	@UsedService
	NumberProducerService nps;
	
	@ConsumedEvent
	NumberProducerEvent newProducerConsummerEvent(){
		NumberProducerEvent npe = new NumberProducerEvent() {
			
			@Override
			public void notifyNewValueCreated(int val) {
				System.out.println("NumberReceiver received new value : " + val);
				
				if(val==5){
					nps.stop();
				}
			}
		};
		
		return npe;
	}

	@Override
	public void componentInitiated() {
		
	}

	@Override
	public void componentStarted() {
		this.nps.start();
	}

	@Override
	public void componentStopped() {
		// TODO Auto-generated method stub
		
	}

}
