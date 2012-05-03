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
package org.capcaval.c3.componentmanager._test.TestConsummer.imp;

import org.capcaval.c3.component.ComponentState;
import org.capcaval.c3.component.annotation.UsedService;
import org.capcaval.c3.componentmanager._test.TestConsummer.TestConsummer;
import org.capcaval.c3.componentmanager._test.TestConsummer.TestConsummerService;
import org.capcaval.c3.componentmanager._test.TestProducer.TestProducerEvent;
import org.capcaval.c3.componentmanager._test.TestProducer.TestProducerService;

public class TestConsummerImpl implements TestConsummer, TestProducerEvent, TestConsummerService, ComponentState {
	
	@UsedService
	TestProducerService tpService;

	@Override
	public void componentInitiated() {
		this.tpService.setMyTestingValue(-1);
	}

	@Override
	public void componentStarted() {
		System.out.println("Value from consummer : " + this.tpService.getMyTestingValue());
		
	}

	@Override
	public void componentStopped() {
	}

	@Override
	public int getValue() {
		return tpService.getMyTestingValue();
	}

	@Override
	public void setValue(int value) {
		this.tpService.setMyTestingValue(value);
		
	}

	@Override
	public void notifyValueChanged(int value) {
		System.out.println("######################## event : " + value);
	}

}
