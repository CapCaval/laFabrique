package org.capcaval.ermine.coves.statemachine;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Node;

import org.capcaval.ermine.coves.statemachine._impl.GadgetWithState;
import org.capcaval.ermine.coves.statemachine._impl.GadgetWithStateAwtImpl;
import org.capcaval.ermine.coves.statemachine._impl.GadgetWithStateJfxImpl;


public class GadgetStateHandler <T extends Enum<T>>
{
    protected T state;

    /**
     * Get accessor for state
     */
    public T getState () {
        return this.state;
    }

    /**
     * Set accessor for state
     */
    public void setState (T value) {
        this.state = value; 
    }

    protected Map<T, List<GadgetWithState>> gadgetStateFromApplicationState = new HashMap<T, List<GadgetWithState>>();

    protected T[] stateValueList = null;

    /**
     * Get accessor for stateValueList
     */
    public T[] getStateValueList () {
        return this.stateValueList;
    }


    public GadgetStateHandler(
        final T defaultValue)
    {
    	this.state  = defaultValue;
    }

    public void addStateValue(
        final T... stateValueList)
    {
		// keep the ref on the list 
		this.stateValueList = stateValueList; 
		// create all the empty list associated with the 
		for(T state : stateValueList){
			this.gadgetStateFromApplicationState.put(state, new ArrayList<GadgetWithState>());
		}
    }

    public void addGadgetState(
        final Object component,
        final GState... gstateList)
    {
		int index = 0;
		
		for(GState gstate: gstateList){
			// get the application state to be configured
			T appState = this.stateValueList[index];
			// go to the next application status next time
			index ++;
			
			// retrieve the list to be updated
			List<GadgetWithState> gadgetStateList = this.gadgetStateFromApplicationState.get(appState);

			Class<?> type = component.getClass();
			GadgetWithState gadgetWithState = null;
			if(Node.class.isAssignableFrom(type) == true){
				gadgetWithState = new GadgetWithStateJfxImpl((Node)component, gstate);
			}else if(Component.class.isAssignableFrom(type) == true){
				gadgetWithState = new GadgetWithStateAwtImpl((Component)component, gstate);
			}else{
				throw new RuntimeException("[Ermine] Error : type " + type + " is incompatible. It should be either a sub class of java.awt.Component or of javafx.scene.Node.");
			}
			
			// add the new value to the list
			if(gadgetWithState != null){
				gadgetStateList.add(gadgetWithState);
			}
		}
    }

    public void updateStateFromValues(
        final Enum<?> newState)
    {
		// get all the gadget states for a given application state
		List<GadgetWithState> gadgetStateList = gadgetStateFromApplicationState.get(newState);
		
		// keep the last value
		this.state = (T)newState;
		
		for(GadgetWithState gadgetWithState : gadgetStateList){
			gadgetWithState.updateGadgetFromItsState();
		}
    }

    public void updateWithLastValue()
    {
    	this.updateStateFromValues(this.state);
    }
}
