package org.capcaval.c3.componentmanager._test.simplecomp;

import org.capcaval.c3.component.ComponentEvent;


public interface SimpleComponentEvents extends ComponentEvent{
	public void notifyValue(String newValue);
}
