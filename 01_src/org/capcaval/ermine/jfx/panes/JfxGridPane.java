package org.capcaval.ermine.jfx.panes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public abstract class JfxGridPane extends GridPane {

	public ImageView newImageView(String fileName){
		Class<?> currentType = this.getClass();
		Image image = new Image( currentType.getResourceAsStream(fileName));
		
		return new ImageView(image);
	}
	
}
