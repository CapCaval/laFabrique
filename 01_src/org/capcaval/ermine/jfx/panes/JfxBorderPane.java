package org.capcaval.ermine.jfx.panes;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import org.capcaval.ermine.jfx.JfxTools;

public class JfxBorderPane extends BorderPane{

	public void setBackgroundColor(Color color){
		this.setStyle("-fx-background-color: #" + JfxTools.toStyleString(color));
	}
	
	public void addNode(Node node){
		this.setCenter(node);
	}
}
