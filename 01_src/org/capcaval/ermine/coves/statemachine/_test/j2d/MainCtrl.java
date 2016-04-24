package org.capcaval.ermine.coves.statemachine._test.j2d;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;

import org.capcaval.ermine.coves.statemachine.StateManager;
import org.capcaval.ermine.coves.statemachine._test.sample.State;

public class MainCtrl 
{
    protected MainView view;

    public MainView getView () {
        return this.view;
    }

    public MainCtrl(
        final StateManager stateManager)
    {
    	this.view = new MainView(stateManager);
    	
    	this.view.getStateCombo().setModel(new DefaultComboBoxModel(State.values()));
    	
    	// change the state from new combo selection
    	this.view.getStateCombo().addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				
				State item = (State)event.getItem();
				
				if (event.getStateChange() == ItemEvent.SELECTED) {
					stateManager.update(item);
				}
			}
		});
    }
}
