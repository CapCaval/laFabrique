package org.capcaval.ermine.coves.statemachine._impl;

import java.awt.Component;

import org.capcaval.ermine.coves.statemachine.GState;


public class GadgetWithStateAwtImpl implements GadgetWithState
{
    protected Component componentInstance;

    /**
     * Get accessor for componentInstance
     */
    public Component getComponentInstance () {
        return this.componentInstance;
    }

    /**
     * Card accessor for componentInstance
     */
    public int cardComponentInstance () {
        if ( this.componentInstance == null ) return 0;
        else return 1;
    }

    protected GState state;


    public GadgetWithStateAwtImpl(
        final Component componentInstance,
        final GState state)
    {
		this.componentInstance = componentInstance;
		this.state = state;
    }

    @Override
	public void updateGadgetFromItsState()
    {
		if(this.state != GState.nochange){
			// set visible false for invisible state otherwise set it 
			this.componentInstance.setVisible(this.state==GState.invisible?false:true);
		
			// set enable true for enable state 
			if((this.state == GState.enable)||(this.state == GState.disable)){
				this.componentInstance.setEnabled(this.state==GState.enable?true:false);}
		}
    }
}
