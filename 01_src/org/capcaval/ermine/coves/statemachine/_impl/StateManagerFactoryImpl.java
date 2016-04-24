package org.capcaval.ermine.coves.statemachine._impl;

import org.capcaval.ermine.coves.statemachine.StateManager;
import org.capcaval.ermine.coves.statemachine.StateManagerFactory;

public class StateManagerFactoryImpl implements StateManagerFactory {

	public StateManager newStateManager() {
		return new StateManagerImpl();
	}
}
