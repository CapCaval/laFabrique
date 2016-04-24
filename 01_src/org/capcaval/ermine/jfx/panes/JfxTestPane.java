package org.capcaval.ermine.jfx.panes;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import org.capcaval.ermine.jfx.JfxTools;
import org.capcaval.ermine.jfx.layout.JfxRefPointLayout;
import org.capcaval.ermine.layout.RefPointEnum;

public class JfxTestPane extends JfxPane{

	public JfxTestPane(String name){
		JfxRefPointLayout layout = new JfxRefPointLayout(this);
		layout.addCenteredNode(new Label(name), RefPointEnum.middleCenterPoint);
	}
	
	public void setBackgroundColor(Color color){
		this.setStyle("-fx-background-color: #" + JfxTools.toStyleString(color));
	}
}
