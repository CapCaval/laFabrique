package org.capcaval.ccoutils.application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GHelpApplication extends javafx.application.Application{

	public GHelpApplication(ApplicationDescription appDesc) {
	}

	@Override
	public void start(Stage stage) throws Exception {
	       stage.setTitle("GHelp");
	        final Scene scene = new Scene(new Group(), 875, 700);
	        scene.setFill(Color.LIGHTGRAY);
	        Group root = (Group)scene.getRoot();
	        
	        TabPane tabPane = new TabPane();
	        root.getChildren().add(tabPane);

	        stage.setScene(scene);
	        stage.show();
	}

}
