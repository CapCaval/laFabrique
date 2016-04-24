package org.capcaval.ermine.coves.statemachine._test.sample;

import javax.swing.JFrame;

import org.capcaval.ermine.coves.statemachine.GState;
import org.capcaval.ermine.coves.statemachine.GadgetStateHandler;
import org.capcaval.ermine.coves.statemachine.StateManager;
import org.capcaval.ermine.coves.statemachine._test.j2d.MainCtrl;
import org.capcaval.ermine.coves.statemachine._test.j2d.MainView;
import org.capcaval.ermine.coves.statemachine._test.j2d.SubView;

public class TestMainJ2D 
{

    public static void main(
        final String[] args)
    {
		StateManager stateManager = StateManager.factory.newStateManager();
		
		JFrame frame = new JFrame("State Test");
		frame.setBounds(10,10,500,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainCtrl ctrl = new MainCtrl(stateManager);
		frame.getContentPane().add(ctrl.getView());
		
		
		MainView v = ctrl.getView(); 
		SubView sv = v.getSubCtrl().getView();
		
		GadgetStateHandler<State> gsh = new GadgetStateHandler<State>(State.allEnable);
		gsh.addStateValue(						State.allEnable,	State.mixDisable, 	State.allDisable, 	State.mixVisible,	State.allUnvisible);
		gsh.addGadgetState( v.getButton1(), 	GState.enable, 		GState.enable,		GState.disable, 	GState.invisible, 	GState.invisible); 
		gsh.addGadgetState( v.getButton2(), 	GState.enable, 		GState.disable, 	GState.disable, 	GState.disable, 	GState.invisible);
		gsh.addGadgetState( sv.getPlusBtn(), 	GState.enable, 		GState.disable, 	GState.disable, 	GState.invisible, 	GState.invisible);
		gsh.addGadgetState( sv.getMinusBtn(),	GState.enable, 		GState.enable, 		GState.disable, 	GState.disable, 	GState.invisible);
				
		//gsh.updateStateFromValue(State.readyForAcquisition);
		stateManager.add(gsh);
		
		frame.setVisible(true);
    	
    }
}
