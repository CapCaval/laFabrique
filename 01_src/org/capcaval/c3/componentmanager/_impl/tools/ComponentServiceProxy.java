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
package org.capcaval.c3.componentmanager._impl.tools;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ScheduledExecutorService;

import org.capcaval.c3.component.ComponentService;

public class ComponentServiceProxy <T extends ComponentService> implements InvocationHandler{

	ComponentService componentService;
	ScheduledExecutorService executor;
	
	public ComponentServiceProxy(ComponentService componentService, ScheduledExecutorService executor){
		this.componentService = componentService;
		this.executor = executor;
	}
	
	@Override
	public Object invoke(final Object proxy,final Method method,final Object[] args)
			throws Throwable {
		
		Runner runner = new Runner(proxy, method, args);
		this.executor.execute(runner);
		
		
		return null;
	}

	public class Runner implements Runnable{
		
		private Object proxy;
		private Method method;
		private Object[] args;

		public Runner(final Object proxy,final Method method,final Object[] args){
			this.proxy = proxy;
			this.method = method;
			this.args = args;
		}
		
		@Override
		public void run() {
			try {
				this.method.invoke(this.proxy, this.args);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		

	}
}
