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
package org.capcaval.c3.sample.architecture.carpark.carparkcontroler._impl;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.capcaval.c3.component.ComponentState;
import org.capcaval.c3.component.ComponentStateAdaptor;
import org.capcaval.c3.component.annotation.ConsumedEvent;
import org.capcaval.c3.sample.architecture.carpark.carparkcomputer.CarParkComputer;
import org.capcaval.c3.sample.architecture.carpark.carparkcomputer.CarParkState;
import org.capcaval.c3.sample.architecture.carpark.carparkcomputer.CarparkComputerEvent;


public class CarParkControllerImpl extends ComponentStateAdaptor implements CarParkComputer {

	
	
	@Override
	public void componentStarted() {
		
		SwingUtilities.invokeLater(this.createHMI());
		
	}

	@ConsumedEvent
	CarparkComputerEvent createEvent(){
		CarparkComputerEvent event = new CarparkComputerEvent(){
			@Override
			public void notifyCarparkState(CarParkState state) {
			}

			@Override
			public void notifyCarGetIn(int newCarNumberInside) {
				System.out.println("car get in " + newCarNumberInside);
			}

			@Override
			public void notifyCarGetOut(int newCarNumberInside) {
				System.out.println("car get out " + newCarNumberInside);
			}};
		return event;
	}
	
	private Runnable createHMI() {
		Runnable hmiCreator = new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame("Car Park Controller");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setBounds(100, 100, 300, 400);
				frame.setVisible(true);
			}
		};
		
		return hmiCreator;
	}
}
