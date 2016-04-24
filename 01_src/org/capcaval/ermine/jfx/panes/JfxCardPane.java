package org.capcaval.ermine.jfx.panes;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import org.capcaval.ermine.jfx.JfxTools;
import org.capcaval.ermine.jfx.layout.JfxRefPointLayout;

public class JfxCardPane extends Pane{

	JfxRefPointLayout layout = null;
	Pane currentPane;
	
	public JfxCardPane(){
		this.layout = new JfxRefPointLayout(this);
	}
	
	public void setBackgroundColor(Color color){
		this.setStyle("-fx-background-color: #" + JfxTools.toStyleString(color));
	}
	
	public void display(Pane pane){
		// remove currentPane 
		//this.getChildren().remove(this.currentPane);
		if(this.currentPane != null){
			this.layout.removeNode(this.currentPane);}
		// keep the pane
		this.currentPane = pane;
		
		// add the new one
		this.layout.addResizableNode(pane, 1, 1, -1, -1);
	}
}
