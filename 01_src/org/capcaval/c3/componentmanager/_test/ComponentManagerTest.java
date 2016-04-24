package org.capcaval.c3.componentmanager._test;

import junit.framework.Assert;

import org.capcaval.c3.component.ComponentEventSubscribe;
import org.capcaval.c3.component.ComponentService;
import org.capcaval.c3.component.annotation.ConsumedEvent;
import org.capcaval.c3.component.annotation.EventSubcribe;
import org.capcaval.c3.component.annotation.UsedService;
import org.capcaval.c3.componentmanager.ComponentManager;
import org.capcaval.c3.componentmanager._test.simplecomp.SimpleCompService;
import org.capcaval.c3.componentmanager._test.simplecomp.SimpleComponentEvents;
import org.capcaval.c3.componentmanager._test.simplecomp._impl.SimpleCompImpl;
import org.capcaval.lafabrique.unittest.AsyncTester;

public class ComponentManagerTest {

	
	class InjectedClass{
		@UsedService
		public SimpleCompService service;

		@EventSubcribe
		public ComponentEventSubscribe<SimpleComponentEvents> subscriber;

		public String resultEventValue = "no value";
		
		@ConsumedEvent
		SimpleComponentEvents newEvent(){
			SimpleComponentEvents event = new SimpleComponentEvents(){
				@Override
				public void notifyValue(String newValue) {
					resultEventValue=newValue;
				}
			};
			return event;
		}
	}
	
	@org.junit.Test
	public void getServicesTest(){
		
		ComponentManager cm = ComponentManager.componentManager;
		
		cm.startApplication(SimpleCompImpl.class);
		
		SimpleCompService service = cm.getComponentService(SimpleCompService.class);

		// Test default value
		Assert.assertEquals("Toto", service.getValue());
		
		// Test changed value 
		service.setValue("This is a test");
		Assert.assertEquals("This is a test", service.getValue());
		
	}

	
	@org.junit.Test
	public void getEventSubscribeTest(){
		
		ComponentManager cm = ComponentManager.componentManager;
		
		cm.startApplication(SimpleCompImpl.class);
		
		ComponentEventSubscribe<SimpleComponentEvents> subscriber = cm.getComponentEventSubscribe(SimpleComponentEvents.class);
		subscriber.subscribe(new SimpleComponentEvents() {
			
			@Override
			public void notifyValue(String newValue) {
				Assert.assertEquals("This is a test!", newValue);
			}
		});
		
		SimpleCompService service = cm.getComponentService(SimpleCompService.class);
		service.setValue("This is a test!");
	}
	
	@org.junit.Test
	public void injectServiceAndEventSubscribeTest(){
		ComponentManager cm = ComponentManager.componentManager;
		
		cm.startApplication(SimpleCompImpl.class);
		
		// allocate a class containing C3 annotations
		InjectedClass obj = new InjectedClass();
		
		cm.injectServicesAndEvents(obj);
		Assert.assertNotNull(obj.service);
		Assert.assertNotNull(obj.subscriber);
		
		// subscribe
		obj.subscriber.subscribe(obj.newEvent());
		
		obj.service.setValue("Test inject");
		// use service and event
		Assert.assertEquals("Test inject", obj.resultEventValue);
		
	}
}
