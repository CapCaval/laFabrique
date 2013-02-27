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
package org.capcaval.c3.sample.tutorial4.numberproducer._impl;

import org.capcaval.c3.component.ComponentState;
import org.capcaval.c3.component.annotation.EventValue;
import org.capcaval.c3.component.annotation.ProducedEvent;
import org.capcaval.c3.sample.tutorial4.numberproducer.NumberProducer;
import org.capcaval.c3.sample.tutorial4.numberproducer.NumberProducerEvent;

public class NumberProducerImpl implements NumberProducer, ComponentState{

	@ProducedEvent
	NumberProducerEvent notifyNumberProducerEvent;
	
	@EventValue(eventType=NumberProducerEvent.class)
	int value;

	@Override
	public void componentInitiated() {		
	}

	@Override
	public void componentStarted() {
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					notifyNumberProducerEvent.notifyValueUpdated(value++);
					try {
						Thread.sleep(3000); // wait 3s
						System.out.println(value);
					} catch (InterruptedException e) { 
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	@Override
	public void componentStopped() {		
	}
	
	
}
