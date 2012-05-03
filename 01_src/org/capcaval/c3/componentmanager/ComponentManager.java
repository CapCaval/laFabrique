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
package org.capcaval.c3.componentmanager;

import org.capcaval.c3.component.Component;
import org.capcaval.c3.component.ComponentEvent;
import org.capcaval.c3.component.ComponentEventSubscribe;
import org.capcaval.c3.component.ComponentService;
import org.capcaval.c3.componentmanager._impl.ComponentManagerImpl;
import org.capcaval.c3.sample.tutorial1.hellomachine._impl.HelloMachineImpl;


/**
 * @author mik
 * This interface provide methods in order to create C3 application and to start it. 
 * 
 */
public interface ComponentManager {

	static public ComponentManager componentManager = new ComponentManagerImpl();

	/**
	 * @param componentType
	 */
	@SuppressWarnings("unchecked")
	public void registerComponent(Class<? extends Component>... componentType);
	
	/**
	 * @param <T>
	 * @param componentType
	 * @return
	 */
	public <T extends ComponentService> T getComponentService(Class<T> componentType);
	public <T extends ComponentEvent> ComponentEventSubscribe<T> getComponentEventSubscribe(Class<T> eventType);
	
	/**
	 * @return
	 */
	public String startApplication();
	/**
	 * @param componentTypeList
	 * @return
	 */
	public String startApplication(Class<? extends Component>... componentTypeList);

	/**
	 * 
	 */
	public void stopApplication();
	
	public ComponentManagerController getController();

}
