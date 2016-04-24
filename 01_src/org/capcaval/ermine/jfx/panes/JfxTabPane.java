package org.capcaval.ermine.jfx.panes;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class JfxTabPane extends TabPane{

	public void add(String name, Pane pane){
		 Tab tab = new Tab();
		 tab.setText(name);
		 tab.setContent(pane);
		 tab.closableProperty().set(false);
		 
		 // add the tab
		 this.getTabs().add(tab);
	}
}
