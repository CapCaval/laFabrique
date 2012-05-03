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
package org.capcaval.c3.componentmanager.tools;

import java.lang.reflect.Method;

import org.capcaval.c3.component.Component;
import org.capcaval.c3.component.ComponentEvent;
import org.capcaval.c3.component.ComponentService;
import org.capcaval.c3.component.ComponentState;
import org.capcaval.c3.componentmanager._impl.tools.ComponentDescriptionFactoryImpl;
import org.capcaval.c3.componentmanager._impl.tools.ComponentItemDescription;
import org.capcaval.c3.componentmanager._impl.tools.SubComponentDescription;
import org.capcaval.c3.componentmanager._impl.tools.UsedEventSubscribeDescription;
import org.capcaval.c3.componentmanager._impl.tools.UsedServicesDescription;
import org.capcaval.tools.pair.Pair;


public interface ComponentDescription {
	public Class<? extends ComponentService>[] getProvidedServices();
	
	public void addProvidedServices(Pair<Class<? extends ComponentService>, Class<?>> componentDesc);
	public void addProvidedServiceList(Pair<Class<? extends ComponentService>, Class<?>>[] serviceDescList);		
	public void addProvidedEventList(Class<? extends ComponentEvent>[] eventList);
	
	public void setComponentImplementationType(Class<?> type);
	public Class<?> getComponentImplementationType();
	
	
	public interface ComponentDescriptionFactory {
		static ComponentDescriptionFactory factory = new ComponentDescriptionFactoryImpl();
		
		public ComponentDescription createComponentDescription();
	}



	public void setComponentType(Class<?> cmpnClass);
	public void setComponentName(String name);
//	public void setProvidedServiceList(Class<? extends ComponentService>[] serviceList);
	
	public Pair<?,?>[] getProvidedServiceList(); 
	public UsedServicesDescription[] getUsedServiceList();
	public UsedEventSubscribeDescription[] getUsedEventSubscribeList();

	public Class<? extends ComponentEvent>[] getProvidedEventList();
	public SubComponentDescription[] getSubComponetList();

	public Component getComponentInstance();
	public void setComponentInstance(Component instance);

	public ComponentState getComponentStateInstance();
	public void setComponentStateInstance(ComponentState instance);


	public void addUsedComponentEventSubscribeFieldList(UsedEventSubscribeDescription[] cmpEventSubscribeList);
	public void addUsedComponentServiceFieldList(UsedServicesDescription[] cmpServiceList);

	public void addConsumedEventMethodList(Method[] consumeEventMethodList);
	public void addConsumedEventList(Class<?>[] consumedEventlist);
	public Method[] getConsumedEventMethodList();
	public Class<?>[] getConsumedEventList();
	public void addUsedServicesField(UsedServicesDescription[] usedServiceList);

	public void addSubComponent(SubComponentDescription desc);

	public void setComponentLevel(int componentLevel);

	public void addItem(ComponentItemDescription itemDesc);

	public ComponentItemDescription[] getComponentItemList();

	public void addProvidedServices(
			Class<? extends ComponentService> serviceType, Class<?> cmpnClass);



}
