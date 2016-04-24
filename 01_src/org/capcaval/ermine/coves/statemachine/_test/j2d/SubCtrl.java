package org.capcaval.ermine.coves.statemachine._test.j2d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.capcaval.ermine.coves.statemachine.GState;
import org.capcaval.ermine.coves.statemachine.GadgetStateHandler;
import org.capcaval.ermine.coves.statemachine.StateManager;

public class SubCtrl 
{
    protected SubView view;

    /**
     * Get accessor for view
     */
    public SubView getView () {
        return this.view;
    }

    /**
     * Card accessor for view
     */
    public int cardView () {
        if ( this.view == null ) return 0;
        else return 1;
    }


    public SubCtrl(
        final StateManager stateManager)
    {
    	this.view  = new SubView();
    	
    	JButton minb = this.view.getMinusBtn();
    	JButton plusb = this.view.getPlusBtn();
    	
    	GadgetStateHandler<SubState> gsh = new GadgetStateHandler<SubState>(SubState.max);
    	gsh.addStateValue( 			SubState.max, SubState.min);
    	gsh.addGadgetState(minb,	GState.disable, GState.nochange);
    	gsh.addGadgetState(plusb,	GState.nochange, GState.disable);
    	
    	stateManager.add(gsh);
    	
    	
    	// handle the event on each button
    	this.view.getMinusBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				stateManager.update(SubState.max);
			}
		});

    	this.view.getPlusBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				stateManager.update(SubState.min);
			}
		});

    	
    }
}
