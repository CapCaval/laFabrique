package org.capcaval.ermine.coves.statemachine._test.jfx;


import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import org.capcaval.ermine.coves.statemachine._test.sample.State;
import org.capcaval.ermine.jfx.node.JfxEnumComboBox;

public class MainView extends VBox{
	
	public Button button1 = new Button("btn1");
	public Button button2= new Button("btn1");
	public JfxEnumComboBox<State> combo = JfxEnumComboBox.factory.newInstance(State.class);
	
	public MainView(){
		
		this.getChildren().add(this.button1);
		this.getChildren().add(this.button2);
		this.getChildren().add(this.combo);
	}
}
