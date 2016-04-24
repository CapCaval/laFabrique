package org.capcaval.ermine.jfx;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class JfxCustomFrame extends JfxFrame{
	public JfxCustomFrame(){}
	
	public JfxCustomFrame(int x, int y, Node customBackground){
		this.init( x, y, customBackground);
	}

	protected void init(int x, int y, Node customBackground) {
		
		this.init( "", x, y, 0, 0, null);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(Color.rgb(255	, 255, 255, 0.0));
		
		this.setView(customBackground);
		
		int width = (int)customBackground.getBoundsInLocal().getWidth();
		int height = (int)customBackground.getBoundsInLocal().getHeight();
		
		this.stage.setWidth(width);		
		this.stage.setHeight(height);
	}
}
