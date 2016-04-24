package org.capcaval.ermine.coves.statemachine._impl;

import javafx.scene.Node;

import org.capcaval.ermine.coves.statemachine.GState;


public class GadgetWithStateJfxImpl implements GadgetWithState
{
    protected Node node;

    /**
     * Get accessor for componentInstance
     */
    public Node getComponentInstance () {
        return this.node;
    }

    protected GState state;


    public GadgetWithStateJfxImpl(
        final Node node,
        final GState state)
    {
		this.node = node;
		this.state = state;
    }

    @Override
	public void updateGadgetFromItsState()
    {
		if(this.state != GState.nochange){
			// set visible false for invisible state otherwise set it 
			this.node.setVisible(this.state==GState.invisible?false:true);
		
			// set enable true for enable state 
			if((this.state == GState.enable)||(this.state == GState.disable)){
				this.node.setDisable(this.state==GState.enable?false:true);}
		}
    }
}
