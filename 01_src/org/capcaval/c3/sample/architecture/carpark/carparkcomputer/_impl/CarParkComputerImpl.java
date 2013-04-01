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
package org.capcaval.c3.sample.architecture.carpark.carparkcomputer._impl;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.capcaval.c3.component.ComponentState;
import org.capcaval.c3.component.annotation.ProducedEvent;
import org.capcaval.c3.sample.architecture.carpark.carparkcomputer.CarParkComputer;
import org.capcaval.c3.sample.architecture.carpark.carparkcomputer.CarParkComputerServices;
import org.capcaval.c3.sample.architecture.carpark.carparkcomputer.CarparkComputerEvent;
import org.capcaval.ccoutils.thread.SchedulerFactory;


public class CarParkComputerImpl implements CarParkComputer, ComponentState, CarParkComputerServices{
	@ProducedEvent
	CarparkComputerEvent carParkEvent;

	protected int carMaxNumber = 30;
	protected int currentCarNumber = 0;
	
	@Override
	public void componentInitiated() {}

	@Override
	public void componentStarted() {
		// create a thread to simulate car entrance in and out
		ScheduledExecutorService ses = SchedulerFactory.factory.newSingleThreadScheduledExecutor("Car park computer");
		ses.scheduleAtFixedRate(this.createCarsGetInTask(), 0, 1, TimeUnit.SECONDS);
		ses.scheduleAtFixedRate(this.createCarsGetOutTask(), 0, 2, TimeUnit.SECONDS);
	}

	private Runnable createCarsGetInTask() {
		Runnable runnable = new Runnable(){
			@Override
			public void run() {
				currentCarNumber++;
				carParkEvent.notifyCarGetIn(currentCarNumber);
			}
		};
		return runnable;
	}
	
	private Runnable createCarsGetOutTask() {
		Runnable runnable = new Runnable(){
			@Override
			public void run() {
				currentCarNumber--;
				carParkEvent.notifyCarGetOut(currentCarNumber);
			}
		};
		return runnable;
	}

	@Override
	public void componentStopped() {}

	@Override
	public void openCarEntrance() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeCarEntrance() {
		// TODO Auto-generated method stub
		
	}
	
}
