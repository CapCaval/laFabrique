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
package test.test2components.numberproducer.impl;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.capcaval.c3.component.ComponentStateAdaptor;
import org.capcaval.c3.component.annotation.ProducedEvent;

import test.test2components.numberproducer.NumberProducer;
import test.test2components.numberproducer.NumberProducerEvent;
import test.test2components.numberproducer.NumberProducerService;

public class NumberProducerImpl extends ComponentStateAdaptor implements NumberProducer, NumberProducerService{

	
	@ProducedEvent
	protected NumberProducerEvent producerEvent;
	
	protected AtomicBoolean keepProduceNumber = new AtomicBoolean(false);
	protected Thread producerThread;
	
	@Override
	public void componentStarted() {
		System.out.println("NumberFeeder component Started");
		
		this.producerThread = new Thread(new Runnable(){
			
			AtomicInteger value = new AtomicInteger(0);

			@Override
			public void run() {

				while(keepProduceNumber.get() == true){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					int val = value.incrementAndGet();
					
					// notify subscriber
					producerEvent.notifyNewValueCreated(val);
					
					System.out.println("ProduceNumber : " + val);
				}
				
			}
			
		});
	}


	@Override
	public void start() {
		System.out.println("Start Produce number .. ");
		this.keepProduceNumber.set(true);
		producerThread.start();
		
	}


	@Override
	public void stop() {
		keepProduceNumber.set(false);
		
	}
}
