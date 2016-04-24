package org.capcaval.ermine.coves.statemachine._test.sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.capcaval.ermine.coves.statemachine.GState;
import org.capcaval.ermine.coves.statemachine.GadgetStateHandler;
import org.capcaval.ermine.coves.statemachine.StateManager;
import org.capcaval.ermine.coves.statemachine._test.jfx.MainView;

public class TestMainJFX extends Application {

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		MainView mv = new MainView();
		
        Scene scene = new Scene(mv, 100, 120);
        stage.setScene(scene);
        stage.show();
		
		final StateManager stateManager = StateManager.factory.newStateManager();

		GadgetStateHandler<State> gsh = new GadgetStateHandler<State>( State.allEnable);

		
		gsh.addStateValue(					State.allEnable,	State.mixDisable,	State.allDisable,	State.mixVisible,	State.allUnvisible);
		gsh.addGadgetState( mv.button1,		GState.enable,		GState.enable,		GState.disable,		GState.invisible,	GState.invisible);
		gsh.addGadgetState( mv.button2,		GState.enable,		GState.disable,		GState.disable,		GState.disable,		GState.invisible);
		
		// gsh.updateStateFromValue(State.readyForAcquisition);
		stateManager.add(gsh);
		

		mv.combo.valueProperty().addListener(new ChangeListener<Object>() {
            @Override 
            public void changed(ObservableValue<?> ov, Object oldObject, Object newObject) {
            	System.out.println("old object : " + oldObject + "  new object" + newObject);
            	stateManager.update((State)newObject);
            }    
        });
	}
}
