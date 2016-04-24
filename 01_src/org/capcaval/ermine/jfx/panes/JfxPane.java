package org.capcaval.ermine.jfx.panes;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import org.capcaval.ermine.jfx.JfxTools;

public class JfxPane extends Pane{

	public void setBackgroundColor(Color color){
		this.setStyle("-fx-background-color: #" + JfxTools.toStyleString(color));
	}
	
	public ImageView newImageView(String path){
		Image image = new Image(this.getClass().getResource(path).toExternalForm());
		
		return new ImageView(image);
	}
	
	public void addNode(Node node, int x, int y){
        node.relocate(x, y);
		this.getChildren().add(node);
	}
}
