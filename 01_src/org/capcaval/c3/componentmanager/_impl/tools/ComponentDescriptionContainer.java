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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.capcaval.c3.component.Component;
import org.capcaval.c3.component.ComponentEvent;
import org.capcaval.c3.component.ComponentEventSubscribe;
import org.capcaval.c3.component.ComponentService;
import org.capcaval.c3.componentmanager.tools.ComponentDescription;

public class ComponentDescriptionContainer {
	// contains all the root instances for each component 
	Map<Class<?>,Component> instanceMap = new IdentityHashMap<Class<?>,Component>();
	// contains all the instances of implementation of service
	Map<Class<? extends ComponentService>,ComponentService> servicesMap = new IdentityHashMap<Class<? extends ComponentService>,ComponentService>();
	// contains all the instances of implementation of eventSubscribe
	Map<Class<? extends ComponentEvent>,ComponentEventSubscribe<?>> eventSubscribeMap = new IdentityHashMap<Class<? extends ComponentEvent>,ComponentEventSubscribe<?>>();
	// contains all the instances of implementation of event
	Map<Method,ComponentEvent> eventInstanceMap = new IdentityHashMap<Method,ComponentEvent>();
	// contains all the descriptions of all components
	List<ComponentDescription> componentDescriptionList = new ArrayList<ComponentDescription>();
	// contains all the instances of implementations of items
	Map<Class<?>, Object> itemInstanceMap = new HashMap<Class<?>, Object>();

	public Map<Class<? extends ComponentService>, ComponentService> getServicesMap() {
		return servicesMap;
	}

	public Map<Method, ComponentEvent> getEventInstanceMap() {
		return eventInstanceMap;
	}
	public Map<Class<? extends ComponentEvent>, ComponentEventSubscribe<?>> getEventSubscribeMap() {
		return eventSubscribeMap;
	}

	
	public ComponentDescriptionContainer(ComponentDescription[] cdList) {
		for(ComponentDescription desc : cdList){
			this.componentDescriptionList.add(desc);
		}
	}

	public void registerService(
			Class<? extends ComponentService> serviceType,
			ComponentService instance){
		this.servicesMap.put(serviceType, instance);
	}
	
	public void registerEventSubscribe(
			Class<? extends ComponentEvent> eventType,
			ComponentEventSubscribe<?> instance){
		this.eventSubscribeMap.put(eventType, instance);
	}
	
	public ComponentService getServiceInstance(Class<? extends ComponentService> serviceType){
		return this.servicesMap.get(serviceType);
	}
	
	public ComponentEventSubscribe<?> getEventSubscribeInstance(Class<? extends ComponentEvent> eventType){
		return this.eventSubscribeMap.get(eventType);
	}

	public ComponentDescription[] getComponentDescriptionList() {
		return this.componentDescriptionList.toArray(new ComponentDescription[0]);
	}

	public void registerImplementation(Class<?> componentType,
			Component instance) {
		this.instanceMap.put(componentType, instance);
		
	}
	public void registerItemImplementation(Class<?> componentType,
			Object instance) {
		this.itemInstanceMap.put(componentType, instance);
		
	}

	public Component getComponentInstance(Class<?> type) {
		return this.instanceMap.get(type);
	}

	public ComponentEvent getEventInstance(
			Method method) {
		return this.eventInstanceMap.get(method);
	}

	public void registerEventInstance(Method createEventMethod,
			ComponentEvent event) {
		this.eventInstanceMap.put(createEventMethod, event);
		
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer();
		
		for(ComponentDescription desc : this.componentDescriptionList){
			str.append(desc.toString());
		}
		
		return str.toString();
	}

	public Object getItemInstance(Class<?> itemType) {
		return this.itemInstanceMap.get(itemType);
	}
	
}
