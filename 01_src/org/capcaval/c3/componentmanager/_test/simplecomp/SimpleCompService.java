package org.capcaval.c3.componentmanager._test.simplecomp;

import org.capcaval.c3.component.ComponentService;

public interface SimpleCompService extends ComponentService{
	String getValue();
	void setValue(String newValue);
	
}
