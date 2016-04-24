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
package org.capcaval.c3.sample.tutorial04.numberhmi._impl;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.capcaval.c3.component.ComponentEventSubscribe;
import org.capcaval.c3.component.ComponentState;
import org.capcaval.c3.component.annotation.EventSubcribe;
import org.capcaval.c3.sample.tutorial04.numberhmi.NumberHMI;
import org.capcaval.c3.sample.tutorial04.numberproducer.NumberProducerEvent;

public class NumberIhmImpl implements NumberHMI, ComponentState {
	
	@EventSubcribe
	ComponentEventSubscribe<NumberProducerEvent> numberProducerEventSubscribe;

	@Override
	public void componentInitiated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentStarted() {
		JFrame frame = new JFrame("Produce Number");
		frame.setBounds(10, 20, 200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JLabel valueLabel = new JLabel("-");
		frame.getContentPane().add(valueLabel);
		
		numberProducerEventSubscribe.subscribe(new NumberProducerEvent(){

			@Override
			public void notifyValueUpdated(int newValue) {
				valueLabel.setText(Integer.toString(newValue));
			}});
		
		frame.setVisible(true);
	}

	@Override
	public void componentStopped() {
		// TODO Auto-generated method stub

	}

}
