package org.capcaval.ermine.coves.statemachine._impl;

import java.util.ArrayList;
import java.util.List;

import org.capcaval.ermine.coves.statemachine.GadgetStateHandler;
import org.capcaval.ermine.coves.statemachine.StateManager;


public class StateManagerImpl implements StateManager
{
    protected List<GadgetStateHandler<?>> gshList = new ArrayList<GadgetStateHandler<?>>();

    
    public void add( final GadgetStateHandler<?> gsh)
    {
		// add it at the end of the list
		//this.gshList.add(this.gshList.size(), gsh);
		this.gshList.add(0, gsh);
		// set the defaultValue
		gsh.updateWithLastValue();
    }

    public <T extends Enum<T>>void update(
        final T state)
    {
		for(GadgetStateHandler<?> gsh : this.gshList){
			Class<?> stateType = gsh.getState().getClass();
			
			if(stateType == state.getClass()){
				gsh.updateStateFromValues(state);
			}else{
				gsh.updateWithLastValue();
			}
		}
    }
}
