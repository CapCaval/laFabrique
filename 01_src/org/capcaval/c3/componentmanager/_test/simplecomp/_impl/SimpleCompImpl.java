package org.capcaval.c3.componentmanager._test.simplecomp._impl;

import org.capcaval.c3.component.annotation.ProducedEvent;
import org.capcaval.c3.componentmanager._test.simplecomp.SimpleComp;
import org.capcaval.c3.componentmanager._test.simplecomp.SimpleCompService;
import org.capcaval.c3.componentmanager._test.simplecomp.SimpleComponentEvents;

public class SimpleCompImpl implements SimpleComp, SimpleCompService {

	@ProducedEvent
	SimpleComponentEvents simpleComponentEventsProducer;
	
	String value = "Toto";
	
	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public void setValue(String newValue) {
		this.value = newValue;
		// raise event for all observer
		this.simpleComponentEventsProducer.notifyValue(this.value);
	}

}
